package demo.zzhwanandroid.moudles.main.presenter;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import demo.zzhwanandroid.moudles.main.contract.ArticleDetailContract;

public class ArticleDetailPresenter extends CollectEventPresenter<ArticleDetailContract.View>
        implements ArticleDetailContract.Presenter {

    @Inject
    ArticleDetailPresenter() {

    }

    @Override
    public void shareEventWithPermissionVerify(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        mView.shareArticle();
                    } else {
                        mView.shareError();
                    }
                }));
    }
}
