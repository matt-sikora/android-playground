package io.msikora.starter

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.msikora.starter.core.di.AppContextModule
import javax.inject.Inject

class App : HasSupportFragmentInjector, HasActivityInjector, Application() {

    @Inject lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().appContextModule(AppContextModule(this)).build().inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingFragmentInjector

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

}