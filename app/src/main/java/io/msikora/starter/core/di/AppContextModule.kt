package io.msikora.starter.core.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppContextModule(private val application: Application) {

    @Provides
    @Singleton
    fun context(): Context = application
}
