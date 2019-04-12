package demo.zzhwanandroid.moudles.main.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import demo.zzhwanandroid.R;
import demo.zzhwanandroid.base.activity.BaseActivity;
import demo.zzhwanandroid.core.constant.Constants;
import demo.zzhwanandroid.moudles.homepage.ui.HomePagerFragment;
import demo.zzhwanandroid.moudles.main.contract.MainContract;
import demo.zzhwanandroid.moudles.main.presenter.MainPresenter;
import demo.zzhwanandroid.utils.CommonUtils;
import demo.zzhwanandroid.utils.ToastUtils;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.main_floating_action_btn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.fragment_group)
    FrameLayout mFrameGroup;
    TextView mUsTv;
    private AlertDialog mDialog;
    // Fragments
    private HomePagerFragment mHomeFragment;

    private int mLastFgIndex = -1;
    private int mCurrentFgIndex = 0; // 显示当前Fragment角标
    private long clickTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentFgIndex = savedInstanceState.getInt(Constants.CURRENT_FRAGMENT_KEY);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.CURRENT_FRAGMENT_KEY, mCurrentFgIndex);
    }

    @Override
    protected void initView() {
        initDrawerLayout();
        showFragment(mCurrentFgIndex);
        initNavigationView();
        initBottomNavigationView();
    }

    private void showFragment(int index) {
        mCurrentFgIndex = index;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        mLastFgIndex = index;
        switch (index){
            case Constants.TYPE_HOME_PAGER:
                mTitle.setText(R.string.home_pager);
                if(mHomeFragment == null){
                    mHomeFragment = HomePagerFragment.newInstance();
                    transaction.add(R.id.fragment_group, mHomeFragment);
                }
                transaction.show(mHomeFragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        switch (mLastFgIndex){
            case Constants.TYPE_HOME_PAGER:
                if(mHomeFragment != null){
                    transaction.hide(mHomeFragment);
                }
                break;
            default:
                break;
        }
    }

    private void initBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.tab_main_pager:
                    showFragment(Constants.TYPE_HOME_PAGER);
                    break;
                default:
                    break;
            }
            return true;
        });

    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //TODO navigation item
                    case R.id.nav_item_my_collect:
                        if (mPresenter.getLoginStatus()) {
//                            CommonUtils.startFragmentInCommonActivity(MainActivity.this, Constants.TYPE_COLLECT);
                        } else {
                            CommonUtils.startLoginActivity(MainActivity.this);
                            ToastUtils.showToast(MainActivity.this, getString(R.string.login_first));
                        }
                        break;
                    case R.id.nav_item_todo:
                        ToastUtils.showToast(MainActivity.this, getString(R.string.in_the_process));
                        break;
                    case R.id.nav_item_night_mode:
                        if (mPresenter.isNightMode()) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            mPresenter.setNightMode(false);
                            menuItem.setTitle(R.string.nav_day_mode);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            mPresenter.setNightMode(true);
                            menuItem.setTitle(R.string.nav_night_mode);
                        }
                        recreate();
                        break;
                    case R.id.nav_item_setting:
//                        CommonUtils.startFragmentInCommonActivity(MainActivity.this, Constants.TYPE_SETTING);
                        break;
                    case R.id.nav_item_about_us:
//                        CommonUtils.startFragmentInCommonActivity(MainActivity.this, Constants.TYPE_ABOUT_US);
                        break;
                    case R.id.nav_item_logout:
                        mPresenter.logout();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        mUsTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_login);
        mUsTv.setText(mPresenter.getLoginStatus() ? mPresenter.getLoginAccount() : getString(R.string.login));
        mUsTv.setOnClickListener(v -> CommonUtils.startLoginActivity(MainActivity.this));
        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(mPresenter.getLoginStatus());
        MenuItem nightModeItem = mNavigationView.getMenu().findItem(R.id.nav_item_night_mode);
        if (mPresenter.isNightMode()) {
            nightModeItem.setIcon(R.drawable.ic_day);
            nightModeItem.setTitle(R.string.nav_day_mode);
        } else {
            nightModeItem.setIcon(R.drawable.ic_night);
            nightModeItem.setTitle(R.string.nav_night_mode);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            mTitle.setText(R.string.home_pager);
        }
    }
    private void initDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick({R.id.main_floating_action_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_floating_action_btn:
                jumpToTheTop();
                break;
            default:
                break;
        }
    }
    private void jumpToTheTop() {
        switch (mCurrentFgIndex) {
            case Constants.TYPE_HOME_PAGER:
                if (mHomeFragment != null) {
                    mHomeFragment.jumpToTheTop();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 处理回退事件
     */
    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - clickTime) > Constants.DOUBLE_INTERVAL_TIME) {
                ToastUtils.showToast(MainActivity.this, getString(R.string.double_click_exit_toast));
                clickTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    @Override
    protected void initEventAndData() {

    }
    @Override
    public void showLoading() {
        if (mDialog == null) {
            mDialog = CommonUtils.getLoadingDialog(this, getString(R.string.logging_out));
        }
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
