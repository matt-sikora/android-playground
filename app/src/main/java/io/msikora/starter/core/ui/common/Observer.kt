package io.msikora.starter.core.ui.common

import android.arch.lifecycle.Observer

class NonNullObserver<T>(
    private val observer: ((T) -> Unit)
) : Observer<T> {

    override fun onChanged(t: T?) {
        t?.let { observer(t) }
    }
}
