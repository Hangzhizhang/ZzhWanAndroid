
package demo.zzhwanandroid.core.http;


import java.util.List;

import demo.zzhwanandroid.moudles.homepage.banner.BannerData;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleItemData;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleListData;
import demo.zzhwanandroid.moudles.login.bean.LoginData;
import io.reactivex.Observable;

public interface HttpHelper {


    // 注册
    Observable<BaseResponse<LoginData>> register(String username, String password, String repassword);
    // 登录
    Observable<BaseResponse<LoginData>> login(String username, String password);
    // 获取首页列表数据
    Observable<BaseResponse<ArticleListData>> getArticleList(int pageNum);
    // 获取首页banner数据
    Observable<BaseResponse<List<BannerData>>> getBannerData();

    Observable<BaseResponse<List<ArticleItemData>>> getTopArticles();
    // 收藏文章
    Observable<BaseResponse<ArticleListData>> addCollectArticle(int id);
    // 取消收藏
    Observable<BaseResponse<ArticleListData>> cancelCollectArticle(int id);
}
