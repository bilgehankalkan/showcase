package com.trendyol.showcase.ui.binding

import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import com.trendyol.showcase.ui.tooltip.TooltipView
import com.trendyol.showcase.ui.tooltip.TooltipViewState

object BindingSetter {

    @JvmStatic
    @BindingAdapter("tooltipViewState")
    internal fun TooltipView.setTooltipViewState(tooltipViewState: TooltipViewState) {
        bind(tooltipViewState)
    }

    @JvmStatic
    @BindingAdapter(value = ["applyMargin", "arrowPosition"], requireAll = true)
    internal fun TooltipView.placeTooltip(margin: Int, arrowPosition: ArrowPosition) {
        if (arrowPosition == ArrowPosition.UP) {
            (layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                topToTop = 0 // parent
                bottomToBottom = -1
                topMargin = margin
            }
        } else if (arrowPosition == ArrowPosition.DOWN) {
            (layoutParams as? ConstraintLayout.LayoutParams)?.apply {
                topToTop = -1
                bottomToBottom = 0 // parent
                bottomMargin = margin
            }
        }
    }

    @JvmStatic
    @BindingAdapter("textSizeInSP")
    internal fun TextView.setTextSizeInSp(size: Float) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    @JvmStatic
    @BindingAdapter(value = ["arrowHorizontalPosition", "arrowPercentage"], requireAll = true)
    internal fun ImageView.layoutMarginStart(margin: Int, percentage: Int?) {
        (layoutParams as? ConstraintLayout.LayoutParams)?.apply {
            percentage?.let {
                endToEnd = 0
                horizontalBias = (it.toFloat() / 100)
            } ?: run {
                this.marginStart = margin
            }
        }
    }
}
