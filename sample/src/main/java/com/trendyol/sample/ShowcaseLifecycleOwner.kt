package com.trendyol.sample

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.trendyol.showcase.showcase.ShowcaseStateController
import com.trendyol.showcase.showcase.ShowcaseState

class ShowcaseLifecycleOwner(
    private val lifecycle: Lifecycle,
    private val controller: ShowcaseStateController,
) : LifecycleEventObserver {

    init {
        lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            onDestroy()
            return
        }
        if (event == Lifecycle.Event.ON_STOP) {
            onDestroy()
            return
        }
    }

    private fun onDestroy() {
        lifecycle.removeObserver(this)
        controller.onStateChanged(ShowcaseState.DESTROY)
    }

}