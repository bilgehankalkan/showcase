package com.trendyol.sample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class MedusaLifecycleOwner : LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry
}
