

package demo.zzhwanandroid.di.moulde;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import demo.zzhwanandroid.di.component.BaseFragmentComponent;
import demo.zzhwanandroid.moudles.homepage.ui.HomePagerFragment;
import demo.zzhwanandroid.moudles.login.ui.LoginFragment;
import demo.zzhwanandroid.moudles.login.ui.RegisterFragment;

@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AbstractAllFragmentModule {
    @ContributesAndroidInjector(modules = LoginFragmentModule.class)
    abstract LoginFragment contributesLoginFragmentInject();

    @ContributesAndroidInjector(modules = RegisterFragmentModule.class)
    abstract RegisterFragment contributesRegisterFragmentInject();

    @ContributesAndroidInjector(modules = HomePagerFragmentModule.class)
    abstract HomePagerFragment contributesHomePagerFragmentInject();

}
