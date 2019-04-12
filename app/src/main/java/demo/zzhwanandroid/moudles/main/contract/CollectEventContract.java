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

package demo.zzhwanandroid.moudles.main.contract;


import demo.zzhwanandroid.base.presenter.IPresenter;
import demo.zzhwanandroid.base.view.IView;

public interface CollectEventContract {

    interface View extends IView {
        void showCollectSuccess(int position);
        void showCancelCollectSuccess(int position);
    }

    interface Presenter<V extends View> extends IPresenter<V> {
        void addCollectArticle(int postion, int id);
        void cancelCollectArticle(int postion, int id);
    }
}