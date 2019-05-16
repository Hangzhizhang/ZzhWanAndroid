package demo.zzhwanandroid.moudles.homepage.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import demo.zzhwanandroid.R;
import demo.zzhwanandroid.base.fragment.BaseFragment;
import demo.zzhwanandroid.core.constant.Constants;
import demo.zzhwanandroid.moudles.homepage.banner.BannerData;
import demo.zzhwanandroid.moudles.homepage.banner.BannerGlideImageLoader;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleItemData;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleListData;
import demo.zzhwanandroid.moudles.homepage.contract.HomePagerContract;
import demo.zzhwanandroid.moudles.homepage.presenter.HomePagerPresenter;
import demo.zzhwanandroid.utils.CommonUtils;
import demo.zzhwanandroid.utils.ToastUtils;

public class HomePagerFragment extends BaseFragment<HomePagerPresenter> implements HomePagerContract.View{

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.home_pager_recycler_view)
    RecyclerView mRecyclerView;

    private List<ArticleItemData> mArticleList;  // 列表数据
    private ArticleListAdapter mAdapter;
    private List<String> mBannerTitleList;
    private List<String> mBannerUrlList;
    private List<Integer> bannerIdList;
    Banner mBanner;

    public static HomePagerFragment newInstance() {
        HomePagerFragment fragment = new HomePagerFragment();
        //在此处传递参数，可在fragment恢复时使用；避免在构造函数中传参，fragment恢复时不调用非默认构造函数
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_pager;
    }
    @Override
    protected void initView() {
        initRecyclerView();
    }
    @Override
    protected void initEventAndData() {
        initRefreshLayout();
        mPresenter.refreshLayout(true);
    }
    private void initRecyclerView(){
        mArticleList = new ArrayList<>();
        mAdapter = new ArticleListAdapter(R.layout.item_article_list, mArticleList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startArticleDetailPager(view,position));
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> clickChildEvent(view, position));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setHasFixedSize(true);
        LinearLayout mHeaderGroup =(LinearLayout)getLayoutInflater().inflate(R.layout.head_banner, null);
        mBanner  = mHeaderGroup.findViewById(R.id.head_banner);

        mHeaderGroup.removeView(mBanner);
        mAdapter.setHeaderView(mBanner);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void initRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refreshLayout(false);
            refreshLayout.finishRefresh();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore();
        });
    }
    /**
     * 点击按钮
     * TODO
     * @param view
     * @param position
     */
    private void clickChildEvent(View view, int position) {
    }
    /**
     * 跳转到详情页面
     * TODO
     */
    private void startArticleDetailPager(View view, int position) {
        if (mAdapter.getData().size() <= 0 || mAdapter.getData().size() < position) {
            return;
        }
        CommonUtils.startArticleDetailActivity(_mActivity,
                mAdapter.getData().get(position).getId(),
                mAdapter.getData().get(position).getTitle(),
                mAdapter.getData().get(position).getLink(),
                mAdapter.getData().get(position).isCollect(),
                true, position, Constants.MAIN_PAGER);
    }

    @Override
    public void showArticleList(ArticleListData articleListData, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mArticleList = articleListData.getDatas();
            mAdapter.replaceData(articleListData.getDatas());
        } else {
            mArticleList.addAll(articleListData.getDatas());
            mAdapter.addData(articleListData.getDatas());
        }
    }

    @Override
    public void showBannerData(List<BannerData> bannerDataList) {
        mBannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        bannerIdList = new ArrayList<>();
        mBannerUrlList = new ArrayList<>();
        for (BannerData bannerData : bannerDataList) {
            mBannerTitleList.add(bannerData.getTitle());
            bannerImageList.add(bannerData.getImagePath());
            mBannerUrlList.add(bannerData.getUrl());
            bannerIdList.add(bannerData.getId());
        }
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new BannerGlideImageLoader());
        //设置图片集合
        mBanner.setImages(bannerImageList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Accordion);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerListener(i ->
                        ToastUtils.showToast(_mActivity,mBannerTitleList.get(i))
//                CommonUtils.startArticleDetailActivity(_mActivity, bannerIdList.get(i),
//                        mBannerTitleList.get(i), mBannerUrlList.get(i),
//                        false, false,
//                        -1, Constants.TAG_DEFAULT)
        );
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }
    public void jumpToTheTop() {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
    @Override
    public void showCollectSuccess(int position) {
        mAdapter.getData().get(position).setCollect(true);
        mAdapter.setData(position, mAdapter.getData().get(position));
        ToastUtils.showToast(_mActivity, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectSuccess(int position) {
        mAdapter.getData().get(position).setCollect(false);
        mAdapter.setData(position, mAdapter.getData().get(position));
        ToastUtils.showToast(_mActivity, getString(R.string.cancel_collect));
    }
}
