package demo.zzhwanandroid.moudles.main.contract;

import demo.zzhwanandroid.base.presenter.IPresenter;
import demo.zzhwanandroid.base.view.IView;

public interface MainContract {
    interface View extends IView{

    }
    interface Presenter extends IPresenter<View>{
        void logout();
        void setNightMode(boolean isNightMode);
        boolean isNightMode();
    }
}
