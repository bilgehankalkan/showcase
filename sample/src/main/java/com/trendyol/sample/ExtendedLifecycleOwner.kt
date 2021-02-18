package com.trendyol.sample

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

// mimics medusa view lifecycle
internal class ExtendedLifecycleOwner(originalLifecycleOwner: LifecycleOwner) : LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        originalLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                lifecycleRegistry.handleLifecycleEvent(event)
            }
        })
    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}