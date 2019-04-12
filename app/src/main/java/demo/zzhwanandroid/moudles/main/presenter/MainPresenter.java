package demo.zzhwanandroid.moudles.main.presenter;

import javax.inject.Inject;

import demo.zzhwanandroid.base.presenter.BasePresenter;
import demo.zzhwanandroid.moudles.main.contract.MainContract;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter{
    @Inject
    MainPresenter(){}

    @Override
    public void logout() {

    }

    @Override
    public void setNightMode(boolean isNightMode) {
        mDataManager.setNightMode(isNightMode);
    }

    @Override
    public boolean isNightMode() {
        return mDataManager.isNightMode();
    }
}
