package io.msikora.starter.core.di

import android.app.Activity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface ActivitySubcomponent : AndroidInjector<Activity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<Activity>()
}