package com.trendyol.showcase.ui.tooltip

import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.trendyol.showcase.R
import com.trendyol.showcase.util.Constants

internal data class TooltipViewState(
    val titleText: String,
    val descriptionText: String,
    @ColorInt val titleTextColor: Int,
    @ColorInt val descriptionTextColor: Int,
    @ColorInt val backgroundColor: Int,
    @ColorInt val closeButtonColor: Int,
    val showCloseButton: Boolean = true,
    @DrawableRes val arrowResource: Int,
    val arrowPosition: AbsoluteArrowPosition,
    val arrowPercentage: Int?,
    val arrowMargin: Int,
    val titleTextSize: Float,
    val descriptionTextSize: Float,
    val textPosition: TextPosition,
    val imageUrl: String,
    val showCustomContent: Boolean
) {

    fun getImageViewVisibility(): Int = if (imageUrl.isEmpty()) View.GONE else View.VISIBLE

    fun getTextPosition(): Int {
        return when (textPosition) {
            TextPosition.CENTER -> 4
            TextPosition.END -> 3
            else -> 2
        }
    }

    fun getTitleVisibility() = titleText.isNotEmpty()

    fun getDescriptionVisibility() = descriptionText.isNotEmpty()

    fun getTopArrowResource() = if (arrowResource == Constants.DEFAULT_ARROW_RESOURCE) R.drawable.ic_showcase_arrow_up else arrowResource

    fun getTopArrowVisibility() = if (arrowPosition == AbsoluteArrowPosition.UP) View.VISIBLE else View.GONE

    fun getBottomArrowResource() = if (arrowResource == Constants.DEFAULT_ARROW_RESOURCE) R.drawable.ic_showcase_arrow_down else arrowResource

    fun getBottomArrowVisibility() = if (arrowPosition == AbsoluteArrowPosition.DOWN) View.VISIBLE else View.GONE

    fun getCloseButtonVisibility() = if (showCloseButton) View.VISIBLE else View.GONE

    fun isContentVisible(): Int = if (showCustomContent) View.GONE else View.VISIBLE
}
