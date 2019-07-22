package com.trendyol.showcase.ui.showcase

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.trendyol.showcase.R
import com.trendyol.showcase.databinding.LayoutShowcaseBinding
import com.trendyol.showcase.showcase.ShowcaseModel
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import com.trendyol.showcase.ui.tooltip.TooltipViewState
import com.trendyol.showcase.util.OnTouchClickListener
import com.trendyol.showcase.util.TooltipFieldUtil
import com.trendyol.showcase.util.getShowcaseActivity
import com.trendyol.showcase.util.shape.CircleShape
import com.trendyol.showcase.util.shape.RectangleShape
import com.trendyol.showcase.util.statusBarHeight

class ShowcaseView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutShowcaseBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
        R.layout.layout_showcase, this, true)
    var showcaseModel: ShowcaseModel? = null
        set(value) {
            field = value
            bind(value)
        }

    override fun dispatchDraw(canvas: Canvas) {
        showcaseModel?.also {
            when (it.highlightType) {
                HighlightType.CIRCLE -> CircleShape(
                    statusBarHeight(),
                    width,
                    height,
                    it.horizontalCenter(),
                    it.verticalCenter(),
                    it.radius + it.highlightPadding)
                HighlightType.RECTANGLE -> RectangleShape(
                    statusBarHeight(),
                    width,
                    height,
                    it.rectF.left - (it.highlightPadding / 2),
                    it.rectF.top - (it.highlightPadding / 2),
                    it.rectF.right + (it.highlightPadding / 2),
                    it.rectF.bottom + (it.highlightPadding / 2))
            }.draw(it.windowBackgroundColor, it.windowBackgroundAlpha, canvas)
        }
        super.dispatchDraw(canvas)
    }

    private fun listenClickEvents() {
        val onTouchClickListener = OnTouchClickListener()

        onTouchClickListener.clickListener = { _, x, y ->
            if (showcaseModel?.cancellableFromOutsideTouch == true) {
                getShowcaseActivity()?.onBackPress(isHighlightClick(x, y))
            } else if (isHighlightClick(x, y)) {
                getShowcaseActivity()?.onBackPress(true)
            }
        }

        setOnTouchListener(onTouchClickListener)
    }

    private fun isHighlightClick(x: Float, y: Float) =
        showcaseModel?.let {
            val newRectF = it.rectF

            when (it.highlightType) {
                HighlightType.CIRCLE -> {
                    newRectF.left -= (it.radius + it.highlightPadding)
                    newRectF.right += (it.radius + it.highlightPadding)
                    newRectF.top -= (it.radius + it.highlightPadding - statusBarHeight())
                    newRectF.bottom += (it.radius + it.highlightPadding - statusBarHeight())
                }
                HighlightType.RECTANGLE -> {
                    newRectF.left -= (it.highlightPadding / 2)
                    newRectF.right += (it.highlightPadding / 2)
                    newRectF.top -= (it.highlightPadding / 2)
                    newRectF.bottom += (it.highlightPadding / 2)
                }
            }
            newRectF.contains(x, y)
        } ?: false

    private fun bind(showcaseModel: ShowcaseModel?) {
        showcaseModel?.let {
            listenClickEvents()

            val arrowPosition = if (it.arrowPosition == ArrowPosition.AUTO) {
                TooltipFieldUtil.calculateArrowPosition(resources, it.verticalCenter())
            } else {
                it.arrowPosition
            }

            binding.showcaseViewState = ShowcaseViewState(when (it.highlightType) {
                HighlightType.CIRCLE -> TooltipFieldUtil.calculateMarginForCircle(resources, it.topOfCircle(), it.bottomOfCircle(), arrowPosition, statusBarHeight())
                HighlightType.RECTANGLE -> TooltipFieldUtil.calculateMarginForRectangle(resources, it.rectF.top, it.rectF.bottom, arrowPosition, statusBarHeight())
            })

            binding.tooltipViewState = TooltipViewState(
                it.titleText,
                it.descriptionText,
                it.titleTextColor,
                it.descriptionTextColor,
                it.popupBackgroundColor,
                it.closeButtonColor,
                it.showCloseButton,
                arrowPosition,
                it.arrowPercentage,
                it.titleTextSize,
                it.descriptionTextSize,
                TooltipFieldUtil.calculateArrowMargin(resources, it.horizontalCenter()))
            binding.executePendingBindings()
        }
    }
}
