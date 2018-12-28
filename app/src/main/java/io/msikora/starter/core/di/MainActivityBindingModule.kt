package io.msikora.starter.core.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.msikora.starter.sample.MainActivity

@Module(subcomponents = [FragmentSubcomponent::class])
abstract class MainActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity
}
