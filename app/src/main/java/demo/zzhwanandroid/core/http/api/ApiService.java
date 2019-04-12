/*
 *     (C) Copyright 2019, ForgetSky.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package demo.zzhwanandroid.core.http.api;

import java.util.List;

import demo.zzhwanandroid.core.http.BaseResponse;
import demo.zzhwanandroid.moudles.homepage.banner.BannerData;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleItemData;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleListData;
import demo.zzhwanandroid.moudles.login.bean.LoginData;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    String BASE_URL = "https://www.wanandroid.com/";

    /**
     * 登录
     * https://www.wanandroid.com/user/login
     *
     * @param username user name
     * @param password password
     * @return 登录数据
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * https://www.wanandroid.com/user/register
     *
     * @param username user name
     * @param password password
     * @param repassword re password
     * @return 注册数据
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<LoginData>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);
    /**
     * 获取文章列表
     * https://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<ArticleListData>> getArticleList(@Path("pageNum") int pageNum);

    /**
     * 广告栏
     * https://www.wanandroid.com/banner/json
     *
     * @return 广告栏数据
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerData();

    /**
     * 获取首页置顶文章列表
     * https://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    Observable<BaseResponse<List<ArticleItemData>>> getTopArticles();

}
