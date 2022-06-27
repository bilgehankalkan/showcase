package com.trendyol.showcase.showcase

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.trendyol.showcase.ui.showcase.ShowcaseActivity
import com.trendyol.showcase.util.ActionType

internal class DefaultLifecycleObserver(
    private val lifecycleOwner: LifecycleOwner,
    context: Context
) : LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private var activity: ShowcaseActivity? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
        (context.applicationContext as Application).registerActivityLifecycleCallbacks(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (shouldFinishShowcase()) {
            finishShowcase()
        }
    }

    fun isLifecycleReady(): Boolean = isDestroyed().not()

    private fun isDestroyed(): Boolean =
        lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED

    private fun shouldFinishShowcase() = activity != null && isDestroyed()

    private fun finishShowcase(): Unit? {
        lifecycleOwner.lifecycle.removeObserver(this)
        return this.activity?.finishShowcase(ActionType.EXIT, -1)
    }

    override fun onActivityStarted(activity: Activity) {
        this.activity = activity as? ShowcaseActivity
        if (shouldFinishShowcase()) {
            finishShowcase()
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        (activity.applicationContext as Application).unregisterActivityLifecycleCallbacks(this)
        this.activity = null
    }
}