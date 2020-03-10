package org.company.annamedvedieva.wishlist;

import android.app.Activity;
import android.app.Application;

import org.company.annamedvedieva.wishlist.di.AppModule;
import org.company.annamedvedieva.wishlist.di.DbModule;
import org.company.annamedvedieva.wishlist.di.DaggerAppComponent;

import javax.inject.Inject;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;


    @Override
    public void onCreate(){
        super.onCreate();
        DaggerAppComponent
                .builder()
                .application(this)
                .appModule(new AppModule())
                .dbModule(new DbModule())
                .build()
                .inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}

