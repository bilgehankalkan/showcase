package com.trenyol.showcase.ui.showcase

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.trenyol.showcase.R
import com.trenyol.showcase.databinding.LayoutIntroBinding
import com.trenyol.showcase.showcase.ShowcaseModel
import com.trenyol.showcase.ui.tooltip.ArrowPosition
import com.trenyol.showcase.ui.tooltip.TooltipViewState
import com.trenyol.showcase.util.TooltipFieldUtil
import com.trenyol.showcase.util.statusBarHeight
import com.trenyol.showcase.util.shape.CircleShape
import com.trenyol.showcase.util.shape.RectangleShape

class ShowCaseView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutIntroBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
        R.layout.layout_intro, this, true)
    var showcaseModel: ShowcaseModel? = null
        set(value) {
            field = value
            bind(value)
        }

    override fun dispatchDraw(canvas: Canvas) {
        showcaseModel?.also {
            when (it.highlightType) {
                HighlightType.CIRCLE -> CircleShape(statusBarHeight(), width, height, it.horizontalCenter(), it.verticalCenter(), it.radius)
                HighlightType.RECTANGLE -> RectangleShape(statusBarHeight(), width, height, it.left, it.top, it.right, it.bottom)
            }.draw(it.windowBackgroundColor, it.windowBackgroundAlpha, canvas)
        }
        super.dispatchDraw(canvas)
    }

    private fun bind(showcaseModel: ShowcaseModel?) {
        showcaseModel?.let {
            val arrowPosition = if (it.arrowPosition == ArrowPosition.AUTO)
                TooltipFieldUtil.calculateArrowPosition(resources, it.verticalCenter()) else it.arrowPosition

            binding.showcaseViewState = ShowCaseViewState(when (it.highlightType) {
                HighlightType.CIRCLE -> TooltipFieldUtil.calculateMarginForCircle(resources, it.topOfCircle(), it.bottomOfCircle(), arrowPosition, statusBarHeight())
                HighlightType.RECTANGLE -> TooltipFieldUtil.calculateMarginForRectangle(resources, it.top, it.bottom, arrowPosition, statusBarHeight())
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
