package io.msikora.starter.core.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class BindingsModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}