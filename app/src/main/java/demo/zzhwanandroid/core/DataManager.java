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

package demo.zzhwanandroid.core;


import java.util.List;

import demo.zzhwanandroid.core.db.DbHelper;
import demo.zzhwanandroid.core.greendao.HistoryData;
import demo.zzhwanandroid.core.http.BaseResponse;
import demo.zzhwanandroid.core.http.HttpHelper;
import demo.zzhwanandroid.core.preference.PreferenceHelper;
import demo.zzhwanandroid.moudles.homepage.banner.BannerData;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleItemData;
import demo.zzhwanandroid.moudles.homepage.bean.ArticleListData;
import demo.zzhwanandroid.moudles.login.bean.LoginData;
import io.reactivex.Observable;

public class DataManager implements HttpHelper, DbHelper, PreferenceHelper {
    private HttpHelper mHttpHelper;
    private DbHelper mDbHelper;
    private PreferenceHelper mPreferenceHelper;

    public DataManager(HttpHelper httpHelper, DbHelper dbHelper, PreferenceHelper preferenceHelper) {
        mHttpHelper = httpHelper;
        mDbHelper = dbHelper;
        mPreferenceHelper = preferenceHelper;
    }


    @Override
    public List<HistoryData> addHistoryData(String data) {
        return mDbHelper.addHistoryData(data);
    }

    @Override
    public void clearAllHistoryData() {
        mDbHelper.clearAllHistoryData();
    }

    @Override
    public void deleteHistoryDataById(Long id) {
        mDbHelper.deleteHistoryDataById(id);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return mDbHelper.loadAllHistoryData();
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        mPreferenceHelper.setLoginStatus(isLogin);
    }

    @Override
    public boolean getLoginStatus() {
        return mPreferenceHelper.getLoginStatus();
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferenceHelper.setLoginAccount(account);
    }

    @Override
    public String getLoginAccount() {
        return mPreferenceHelper.getLoginAccount();
    }

    @Override
    public void setNightMode(boolean isNightMode) {
        mPreferenceHelper.setNightMode(isNightMode);
    }

    @Override
    public boolean isNightMode() {
        return mPreferenceHelper.isNightMode();
    }
    // 注册
    @Override
    public Observable<BaseResponse<LoginData>> register(String username, String password, String repassword) {
        return mHttpHelper.register(username, password, repassword);
    }
    // 登录
    @Override
    public Observable<BaseResponse<LoginData>> login(String username, String password){
        return mHttpHelper.login(username, password);
    }
    // 获取首页列表数据
    @Override
    public Observable<BaseResponse<ArticleListData>> getArticleList(int pageNum){
        return mHttpHelper.getArticleList(pageNum);
    }
    // 获取banner数据
    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData(){
        return mHttpHelper.getBannerData();
    }
    @Override
    public Observable<BaseResponse<List<ArticleItemData>>> getTopArticles() {
        return mHttpHelper.getTopArticles();
    }
    // 收藏
    @Override
    public Observable<BaseResponse<ArticleListData>> addCollectArticle(int id) {
        return mHttpHelper.addCollectArticle(id);
    }
    // 取消收藏
    @Override
    public Observable<BaseResponse<ArticleListData>> cancelCollectArticle(int id) {
        return mHttpHelper.cancelCollectArticle(id);
    }
}
