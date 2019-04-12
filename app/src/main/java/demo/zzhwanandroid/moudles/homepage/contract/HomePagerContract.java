package demo.zzhwanandroid.moudles.homepage.contract;


import java.util.List;

import demo.zzhwanandroid.moudles.homepage.banner.BannerData;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleListData;
import demo.zzhwanandroid.moudles.main.contract.CollectEventContract;

public interface HomePagerContract {
    interface View extends CollectEventContract.View{
        void showArticleList(ArticleListData articleListData, boolean isRefresh);
        void showBannerData(List<BannerData> bannerDataList);
    }
    interface Presenter extends CollectEventContract.Presenter<View>{
        void getArticleList(boolean isShowStatusView);

        void getBannerData(boolean isShowStatusView);

        void getHomePagerData(boolean isShowStatusView);

        void refreshLayout(boolean isShowStatusView);

        void loadMore();
    }

}
