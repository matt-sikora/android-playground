package io.msikora.starter.core.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module(subcomponents = [FragmentSubcomponent::class])
abstract class UIBindigsModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}