package io.msikora.starter.core.di

import android.support.v4.app.Fragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface FragmentSubcomponent : AndroidInjector<Fragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<Fragment>()
}