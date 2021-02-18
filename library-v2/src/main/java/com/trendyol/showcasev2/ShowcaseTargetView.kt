package com.trendyol.showcasev2

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.core.view.doOnLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Deprecated("use showcasemanager")
class ShowcaseTargetView : FrameLayout {
    var showCaseViewFactory: ShowcaseViewFactory = DefaultShowcaseViewFactory()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val rect = Rect()

    private val scrollChangeObserver = ViewTreeObserver.OnScrollChangedListener {
        notifyShowcaseViewAboutPosition()
    }

    init {
        observeScrollChanges()
        viewTreeObserver.addOnPreDrawListener {
            Log.d("Showcase", "predraw")
            notifyShowcaseViewAboutPosition()
            notifyShowcaseViewAboutSizeChange()
            true
        }
    }

    private fun observeScrollChanges() {
        if (viewTreeObserver.isAlive.not()) return
        viewTreeObserver.removeOnScrollChangedListener(scrollChangeObserver)
        viewTreeObserver.addOnScrollChangedListener(scrollChangeObserver)
    }

    /*
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        notifyShowcaseViewAboutSizeChange()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        notifyShowcaseViewAboutPosition()
    }*/

    var started = false

    private fun findShowcaseView(): ShowcaseView? {
        if (started.not()) return null
        return rootView.findViewWithTag(SHOWCASE_TAG)
    }

    fun startShowcaseView(
        lifecycleOwner: LifecycleOwner,
        configuration: ShowcaseView.Configuration = ShowcaseView.Configuration(),
        viewClipper: TargetViewClipper = RectangleTargetViewClipper()
    ) {
        check(findShowcaseView() == null) { "Showcase is already started. Try dismissing current one before starting a new one." }
        val showcaseView = showCaseViewFactory
            .buildShowcaseView(
                context,
                configuration,
                viewClipper
            )
            .apply { tag = SHOWCASE_TAG }
        started = true
        addToDecorView(showcaseView)

        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    lifecycleOwner.lifecycle.removeObserver(this)
                    dismissShowcase()
                }
            }
        })
    }

    private fun addToDecorView(showcaseView: ShowcaseView) {
        showcaseView.doOnLayout {
            Log.d("Showcase", "doOnLayout")

            notifyShowcaseViewAboutSizeChange()
            notifyShowcaseViewAboutPosition()
            it.setOnClickListener {
                if (dismissListener == null) {
                    dismissShowcase()
                }
                if (dismissListener?.invoke() == true) {
                    dismissShowcase()
                }
            }
        }
        (rootView as ViewGroup).addView(showcaseView)
    }

    var dismissListener: (() -> Boolean)? = null

    private fun notifyShowcaseViewAboutPosition() {
        getGlobalVisibleRect(rect)
        findShowcaseView()?.onTargetLayoutChanged(rect)
    }

    private fun notifyShowcaseViewAboutSizeChange() {
        findShowcaseView()?.onTargetSizeChanged(measuredWidth, measuredHeight)
    }

    fun dismissShowcase() {
        findShowcaseView()?.dismissShowcase()
        started = false
    }

    override fun onDetachedFromWindow() {
        dismissShowcase()
        super.onDetachedFromWindow()
    }

    companion object {
        const val SHOWCASE_TAG = "SHOWCASE_TAG"
    }
}