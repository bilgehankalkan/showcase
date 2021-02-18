package com.trendyol.showcasev2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.graphics.Region
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import kotlinx.parcelize.Parcelize

class ShowcaseView : FrameLayout {

    private var viewClipper: TargetViewClipper = RectangleTargetViewClipper()
    private lateinit var configuration: Configuration

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

    val rect = Rect()

    override fun dispatchDraw(canvas: Canvas?) {
        view.getGlobalVisibleRect(rect)
        viewClipper.clip(rect, canvas)
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
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
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
    fun clip(targetViewRect: Rect, canvas: Canvas?)
}

class RectangleTargetViewClipper : TargetViewClipper {
    override fun clip(targetViewRect: Rect, canvas: Canvas?) {
        canvas!!.clipRect(targetViewRect, Region.Op.DIFFERENCE)
    }
}

class CircleTargetViewClipper : TargetViewClipper {
    val path = Path()

    override fun clip(targetViewRect: Rect, canvas: Canvas?) {
        val halfWidth = targetViewRect.width() / 2f
        val halfHeight = targetViewRect.height() / 2f
        path.addCircle(targetViewRect.exactCenterX(), targetViewRect.exactCenterY(), Math.max(halfHeight, halfWidth), Path.Direction.CCW)
        canvas!!.clipPath(path, Region.Op.DIFFERENCE)
    }
}