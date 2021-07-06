package com.trendyol.showcasev2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.toRect
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import kotlin.math.max
import kotlinx.parcelize.Parcelize

class ShowcaseView : FrameLayout {

    var inflateTooltip: ((ShowcaseView, Rect) -> View)? = null
    private var viewClipper: TargetViewClipper = RectangleTargetViewClipper()
    private lateinit var configuration: Configuration
    private var tooltipView: View? = null
    private var tooltipRect: Rect? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        addView(FrameLayout(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            alpha = 0.5f
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.black))
        })
    }

    override fun dispatchDraw(canvas: Canvas) {
        val rect = Rect()
        view.getGlobalVisibleRect(rect)
        val rect2 = viewClipper.clip(rect, canvas)

        callInflate(rect2)

        super.dispatchDraw(canvas)
    }

    fun onTargetSizeChanged(w: Int, h: Int) {
        view.layoutParams = (view.layoutParams as LayoutParams).apply {
            this.width = w
            this.height = h
        }
    }

    fun onTargetLayoutChanged(rect: Rect) {
        view.layoutParams = (view.layoutParams as LayoutParams)
            .apply {
                this.width = rect.right - rect.left
                this.height = rect.bottom - rect.top
                this.marginStart = rect.left
                this.topMargin = rect.top
            }
    }


    val view by lazy {
        FrameLayout(context).apply {
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            this@ShowcaseView.addView(this)
        }
    }

    fun dismissShowcase() {
        Log.d("ShowcaseView", "dismiss")
        (parent as ViewGroup).removeView(this)
    }

    override fun onSaveInstanceState(): Parcelable {
        return bundleOf(
            SUPER_SAVED_STATE_KEY to super.onSaveInstanceState(),
            SAVED_STATE_KEY to configuration
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedBundle = state as? Bundle ?: return super.onRestoreInstanceState(state)
        this.configuration = savedBundle.getParcelable(SAVED_STATE_KEY)!!
        super.onRestoreInstanceState(savedBundle.getParcelable(SUPER_SAVED_STATE_KEY))
    }

    fun setViewClipper(viewClipper: TargetViewClipper) {
        this.viewClipper = viewClipper
    }

    fun setConfiguration(configuration: Configuration) {
        this.configuration = configuration
    }

    private fun callInflate(rect: Rect) {
        if (tooltipRect == null && tooltipView == null) {
            tooltipRect = rect
            tooltipView = inflateTooltip?.invoke(this, rect)
            addView(tooltipView)
        }
    }

    @Parcelize
    data class Configuration constructor(
        val flag: Int = 2
    ) : Parcelable

    companion object {

        private const val SUPER_SAVED_STATE_KEY = "SHOW_CASE_SUPER_VIEW"
        private const val SAVED_STATE_KEY = "SHOW_CASE_THIS_VIEW"
    }
}

interface TargetViewClipper {

    fun clip(targetViewRect: Rect, canvas: Canvas): Rect
}

class RectangleTargetViewClipper : TargetViewClipper {

    override fun clip(targetViewRect: Rect, canvas: Canvas): Rect {
        canvas.clipRect(targetViewRect, Region.Op.DIFFERENCE)
        return targetViewRect
    }
}

class CircleTargetViewClipper : TargetViewClipper {

    override fun clip(targetViewRect: Rect, canvas: Canvas): Rect {
        val halfWidth = targetViewRect.width() / 2f
        val halfHeight = targetViewRect.height() / 2f
        val radius = max(halfHeight, halfWidth)
        val path = Path()
        path.addCircle(
            targetViewRect.exactCenterX(),
            targetViewRect.exactCenterY(),
            radius,
            Path.Direction.CCW
        )
        canvas.clipPath(path, Region.Op.DIFFERENCE)

        return getAreaFromPath(path)
    }
}

private fun getAreaFromPath(sourcePath: Path): Rect {
    val rectF = RectF()
    sourcePath.computeBounds(rectF, true)
    //You can get width and height by calling rectF.width(), rectF.height()
    return rectF.toRect()
}
