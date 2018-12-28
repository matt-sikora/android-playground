package io.msikora.starter.core.ui.common

import android.arch.lifecycle.MutableLiveData

class NonNullMutableLiveData<T> : MutableLiveData<T> {

    constructor(value: T) {
        this.value = value
    }

    @Suppress("RedundantOverride")
    override fun setValue(value: T) = super.setValue(value)

    override fun getValue(): T {
        return super.getValue()!!
    }
}