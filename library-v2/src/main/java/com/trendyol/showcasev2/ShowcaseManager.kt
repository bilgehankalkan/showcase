package com.trendyol.showcasev2

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.doOnLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleEventObserver as LifecycleEventObserver1

class ShowcaseManager constructor(
    private val lifecycleOwner: LifecycleOwner,
    private val showcaseViewFactory: ShowcaseViewFactory
){

    private var targetView: View? = null
    private var decorView: View? = null
    private val currentPosition: Rect = Rect()
    private var currentSize: Pair<Int, Int>? = null

    private val scrollChangeObserver = ViewTreeObserver.OnScrollChangedListener {
        log { "scrollChange" }
        val targetView = this.targetView ?: return@OnScrollChangedListener
        targetView.getGlobalVisibleRect(currentPosition)
        currentSize = targetView.measuredWidth to targetView.measuredHeight
        requireNotNull(findShowcaseView()).onTargetLayoutChanged(currentPosition)
    }

    private val preDrawListener = ViewTreeObserver.OnPreDrawListener {
        log { "preDraw" }
        val targetView = this.targetView ?: return@OnPreDrawListener true
        targetView.getGlobalVisibleRect(currentPosition)
        currentSize = targetView.measuredWidth to targetView.measuredHeight
        notifyShowcaseViewAboutPositionAndSize()
        true
    }

    private val lifecycleObserver = object : LifecycleEventObserver1 {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                lifecycleOwner.lifecycle.removeObserver(this)
                log { "lifecycleOwner destroyed" }
                dismiss()
            }
        }
    }

    fun focus(
        view: View,
        viewClipper: TargetViewClipper = RectangleTargetViewClipper(),
        inflateTooltip: ((ShowcaseView, Rect) -> View)? = null,
        dismissListener: (() -> Boolean)? = null
    ) {
        if (targetView != null) {
            log { "realigned to different target ${targetView!!.id}" }
            dismiss()
        }
        this.targetView = view
        observeLifecycleOwner(lifecycleOwner)
        if (lifecycleOwner.isAtLeastStarted()) {
            attachLayoutObservers(view)
            val showcaseView = buildShowcaseView(view.context, viewClipper, dismissListener, inflateTooltip)
            addToDecorView(view, showcaseView)
        }
    }

    private fun attachLayoutObservers(view: View) {
        observePreDraws(view)
        observeScrollChanges(view)
    }

    private fun observePreDraws(view: View) = with(view) {
        if (viewTreeObserver.isAlive.not()) return
        viewTreeObserver.removeOnPreDrawListener(preDrawListener)
        viewTreeObserver.addOnPreDrawListener(preDrawListener)
    }

    private fun observeScrollChanges(view: View) = with(view) {
        if (viewTreeObserver.isAlive.not()) return
        viewTreeObserver.removeOnScrollChangedListener(scrollChangeObserver)
        viewTreeObserver.addOnScrollChangedListener(scrollChangeObserver)
    }


    private fun observeLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
    }

    private fun findShowcaseView(): ShowcaseView? {
        return requireNotNull(decorView).findViewWithTag(ShowcaseTargetView.SHOWCASE_TAG)
    }

    private fun addToDecorView(targetView: View, showcaseView: ShowcaseView) {
        val decorView = (targetView.rootView as ViewGroup).also { this.decorView = it }
        showcaseView.doOnLayout {
            log { "Showcase doOnLayout" }
            targetView.getGlobalVisibleRect(currentPosition)
            currentSize = targetView.measuredWidth to targetView.measuredHeight
            notifyShowcaseViewAboutPositionAndSize()
        }
        decorView.addView(showcaseView)
    }

    private fun buildShowcaseView(
        context: Context,
        clipper: TargetViewClipper,
        dismissListener: (() -> Boolean)?,
        inflateTooltip: ((ShowcaseView, Rect) -> View)?
    ): ShowcaseView {
        return showcaseViewFactory
            .buildShowcaseView(context, ShowcaseView.Configuration(), clipper, inflateTooltip)
            .apply {
                tag = ShowcaseTargetView.SHOWCASE_TAG
                setDismissListener(this, dismissListener)
            }
    }

    private fun setDismissListener(showcaseView: ShowcaseView, dismissListener: (() -> Boolean)?) {
        showcaseView.setOnClickListener {
            if (dismissListener == null) dismiss()
            else if (dismissListener.invoke()) dismiss()
        }
    }

    private fun notifyShowcaseViewAboutPositionAndSize() {
        val showcase = requireNotNull(findShowcaseView())
        val targetPosition = currentPosition
        val targetSize = requireNotNull(currentSize)

        showcase.onTargetLayoutChanged(targetPosition)
        showcase.onTargetSizeChanged(targetSize.first, targetSize.second)
    }

    fun dismiss() {
        log { "dismiss" }
        val showcaseView = requireNotNull(findShowcaseView())
        showcaseView.dismissShowcase()
        cleanUp()
    }

    private fun cleanUp() {
        targetView?.viewTreeObserver?.removeOnScrollChangedListener(scrollChangeObserver)
        targetView?.viewTreeObserver?.removeOnPreDrawListener(preDrawListener)
        lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        decorView = null
        targetView = null
    }

    private fun LifecycleOwner.isAtLeastStarted(): Boolean {
        return lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
    }

}