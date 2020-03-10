package org.company.annamedvedieva.wishlist.di;

import android.app.Application;

import org.company.annamedvedieva.wishlist.MyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        DbModule.class,
        AppModule.class,
        ActivityBuilder.class })

public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder appModule(AppModule appModule);

        @BindsInstance
        Builder dbModule(DbModule dbModule);

        AppComponent build();

    }

    void inject(MyApplication app);

}
