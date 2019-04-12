package demo.zzhwanandroid.moudles.homepage.presenter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import demo.zzhwanandroid.R;
import demo.zzhwanandroid.app.WanAndroidApp;
import demo.zzhwanandroid.core.constant.Constants;
import demo.zzhwanandroid.core.event.CollectEvent;
import demo.zzhwanandroid.core.event.LoginEvent;
import demo.zzhwanandroid.core.event.LogoutEvent;
import demo.zzhwanandroid.core.event.RefreshHomeEvent;
import demo.zzhwanandroid.core.rx.BaseObserver;
import demo.zzhwanandroid.moudles.homepage.banner.BannerData;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleListData;
import demo.zzhwanandroid.moudles.homepage.contract.HomePagerContract;
import demo.zzhwanandroid.moudles.main.presenter.CollectEventPresenter;
import demo.zzhwanandroid.utils.RxUtils;
import io.reactivex.Observable;

public class HomePagerPresenter extends CollectEventPresenter<HomePagerContract.View>
        implements HomePagerContract.Presenter{

    private int currentPage;
    private boolean isRefresh = true;
    @Inject
    HomePagerPresenter() {}


    @Override
    public void refreshLayout(boolean isShowStatusView) {
        isRefresh = true;
        currentPage = 0;
        getHomePagerData(isShowStatusView);
    }
    @Override
    public void reload() {
        refreshLayout(true);
    }
    // 获取列表数据
    @Override
    public void getArticleList(boolean isShowStatusView) {
        addSubscribe(mDataManager.getArticleList(currentPage)
                    .compose(RxUtils.SchedulerTransformer())
                    .filter(articleListData -> mView != null)
                    .subscribeWith(new BaseObserver<ArticleListData>(mView,
                            WanAndroidApp.getContext().getString(R.string.failed_to_obtain_article_list),
                            isShowStatusView) {
                        @Override
                        public void onSuccess(ArticleListData articleListData) {
                            mView.showArticleList(articleListData, isRefresh);
                        }
                    }));
    }
    @Override
    public void getBannerData(boolean isShowStatusView) {
        addSubscribe(mDataManager.getBannerData()
                .compose(RxUtils.SchedulerTransformer())
                .filter(articleListData -> mView != null)
                .subscribeWith(new BaseObserver<List<BannerData>>(mView,
                        WanAndroidApp.getContext().getString(R.string.failed_to_obtain_banner_data),
                        isShowStatusView) {
                    @Override
                    public void onSuccess(List<BannerData> bannerData) {
                        mView.showBannerData(bannerData);
                    }
                }));
    }
    @Override
    public void getHomePagerData(boolean isShowStatusView) {
        getBannerData(isShowStatusView);
        addSubscribe(Observable.zip(mDataManager.getTopArticles(), mDataManager.getArticleList(0),
                (topArticlesBaseResponse, articleListDataBaseResponse) -> {
                    articleListDataBaseResponse.getData().getDatas().
                            addAll(0, topArticlesBaseResponse.getData());
                    return articleListDataBaseResponse;
                })
                .compose(RxUtils.SchedulerTransformer())
                .filter(articleListData -> mView != null)
                .subscribeWith(new BaseObserver<ArticleListData>(mView,
                        WanAndroidApp.getContext().getString(R.string.failed_to_obtain_article_list),
                        isShowStatusView) {
                    @Override
                    public void onSuccess(ArticleListData articleListData) {
                        mView.showArticleList(articleListData, isRefresh);
                    }
                }));
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getArticleList(false);
    }
    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscriber()
    public void loginSuccessEvent(LoginEvent loginEvent) {
        getHomePagerData(false);
    }

    @Subscriber()
    public void logoutSuccessEvent(LogoutEvent logoutEvent) {
        getHomePagerData(false);
    }

    @Subscriber()
    public void refreshHomeEvent(RefreshHomeEvent refreshHomeEvent) {
        getHomePagerData(false);
    }

    @Subscriber(tag = Constants.MAIN_PAGER)
    public void collectEvent(CollectEvent collectEvent) {
        if (mView == null) return;
        if (collectEvent.isCancel()) {
            mView.showCancelCollectSuccess(collectEvent.getArticlePostion());
        } else {
            mView.showCollectSuccess(collectEvent.getArticlePostion());
        }
    }
}
