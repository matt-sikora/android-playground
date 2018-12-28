package io.msikora.starter

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import io.msikora.starter.core.di.AppContextModule
import io.msikora.starter.core.di.MainActivityBindingModule
import io.msikora.starter.core.di.UIBindigsModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        UIBindigsModule::class,
        MainActivityBindingModule::class,
        AppContextModule::class
    ]
)
interface AppComponent {

    fun inject(application: App)
}