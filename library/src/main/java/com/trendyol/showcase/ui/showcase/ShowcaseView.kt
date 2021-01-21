package com.trendyol.showcase.ui.showcase

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.trendyol.showcase.R
import com.trendyol.showcase.databinding.LayoutShowcaseBinding
import com.trendyol.showcase.showcase.ShowcaseModel
import com.trendyol.showcase.ui.tooltip.TooltipViewState
import com.trendyol.showcase.util.OnTouchClickListener
import com.trendyol.showcase.util.TooltipFieldUtil
import com.trendyol.showcase.util.getShowcaseActivity
import com.trendyol.showcase.util.shape.CircleShape
import com.trendyol.showcase.util.shape.RectangleShape
import com.trendyol.showcase.util.statusBarHeight

class ShowcaseView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutShowcaseBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.layout_showcase,
        this,
        true
    )

    var showcaseModel: ShowcaseModel? = null
        set(value) {
            field = value
            bind(value)
        }

    override fun dispatchDraw(canvas: Canvas) {
        if (showcaseModel == null) return super.dispatchDraw(canvas)
        showcaseModel?.also { model ->
            val shape = when (model.highlightType) {
                HighlightType.CIRCLE -> {
                    CircleShape(
                        statusBarDiff = getStatusBarHeight(model.isStatusBarVisible),
                        screenWidth = width,
                        screenHeight = height,
                        x = model.horizontalCenter(),
                        y = model.verticalCenter(),
                        radius = model.radius + model.highlightPadding
                    )
                }
                HighlightType.RECTANGLE -> {
                    RectangleShape(
                        statusBarDiff = getStatusBarHeight(model.isStatusBarVisible),
                        screenWidth = width,
                        screenHeight = height,
                        left = model.rectF.left - (model.highlightPadding / 2),
                        top = model.rectF.top - (model.highlightPadding / 2),
                        right = model.rectF.right + (model.highlightPadding / 2),
                        bottom = model.rectF.bottom + (model.highlightPadding / 2)
                    )
                }
            }
            shape.draw(model.windowBackgroundColor, model.windowBackgroundAlpha, canvas)
        }
        super.dispatchDraw(canvas)
    }

    private fun getStatusBarHeight(isStatusBarVisible: Boolean): Int = if (isStatusBarVisible) {
        statusBarHeight()
    } else {
        0
    }

    private fun listenClickEvents() {
        OnTouchClickListener().apply {
            clickListener = { _, x, y ->
                if (showcaseModel?.cancellableFromOutsideTouch == true) {
                    getShowcaseActivity()?.onBackPress(isHighlightClick(x, y))
                } else if (isHighlightClick(x, y)) {
                    getShowcaseActivity()?.onBackPress(true)
                }
            }
        }.also { setOnTouchListener(it) }
    }

    private fun isHighlightClick(x: Float, y: Float) = showcaseModel?.let {
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
        if (showcaseModel == null) return
        listenClickEvents()

        val arrowPosition = TooltipFieldUtil.decideArrowPosition(
            showcaseModel = showcaseModel,
            screenHeight = resources.displayMetrics.heightPixels
        )
        val arrowMargin = TooltipFieldUtil.calculateArrowMargin(
            horizontalCenter = showcaseModel.horizontalCenter(),
            density = resources.displayMetrics.density
        )
        val marginFromBottom = when (showcaseModel.highlightType) {
            HighlightType.CIRCLE -> TooltipFieldUtil.calculateMarginForCircle(
                top = showcaseModel.topOfCircle(),
                bottom = showcaseModel.bottomOfCircle(),
                arrowPosition = arrowPosition,
                statusBarHeight = statusBarHeight(),
                isStatusBarVisible = showcaseModel.isStatusBarVisible,
                screenHeight = resources.displayMetrics.heightPixels
            )
            HighlightType.RECTANGLE -> TooltipFieldUtil.calculateMarginForRectangle(
                top = showcaseModel.rectF.top,
                bottom = showcaseModel.rectF.bottom,
                arrowPosition = arrowPosition,
                statusBarHeight = statusBarHeight(),
                isStatusBarVisible = showcaseModel.isStatusBarVisible,
                screenHeight = resources.displayMetrics.heightPixels
            )
        }

        binding.showcaseViewState = ShowcaseViewState(margin = marginFromBottom)
        binding.tooltipViewState = TooltipViewState(
            titleText = showcaseModel.titleText,
            descriptionText = showcaseModel.descriptionText,
            titleTextColor = showcaseModel.titleTextColor,
            descriptionTextColor = showcaseModel.descriptionTextColor,
            backgroundColor = showcaseModel.popupBackgroundColor,
            closeButtonColor = showcaseModel.closeButtonColor,
            showCloseButton = showcaseModel.showCloseButton,
            arrowResource = showcaseModel.arrowResource,
            arrowPosition = arrowPosition,
            arrowPercentage = showcaseModel.arrowPercentage,
            arrowMargin = arrowMargin,
            titleTextSize = showcaseModel.titleTextSize,
            descriptionTextSize = showcaseModel.descriptionTextSize,
            textPosition = showcaseModel.textPosition,
            imageUrl = showcaseModel.imageUrl,
            showCustomContent = showcaseModel.customContent != null,
            isStatusBarVisible = showcaseModel.isStatusBarVisible
        )
        binding.executePendingBindings()

        if (showcaseModel.customContent != null) {
            setCustomContent(showcaseModel.customContent)
        }
    }

    private fun setCustomContent(@LayoutRes customContent: Int) {
        binding.tooltipView.setCustomContent(customContent)
    }
}
