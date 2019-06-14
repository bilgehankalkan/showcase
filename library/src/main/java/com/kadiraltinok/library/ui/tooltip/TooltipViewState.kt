package com.kadiraltinok.library.ui.tooltip

import android.view.View
import androidx.annotation.ColorInt

data class TooltipViewState(
    val titleText: String,
    val descriptionText: String,
    @ColorInt val titleTextColor: Int,
    @ColorInt val descriptionTextColor: Int,
    @ColorInt val backgroundColor: Int,
    @ColorInt val closeButtonColor: Int,
    val showCloseButton: Boolean = true,
    val arrowPosition: ArrowPosition,
    val arrowPercentage: Float,
    val titleTextSize: Float,
    val descriptionTextSize: Float
) {

    fun getTopArrowVisibility() =
        if (arrowPosition == ArrowPosition.UP) View.VISIBLE else View.GONE

    fun getBottomArrowVisibility() =
        if (arrowPosition == ArrowPosition.DOWN) View.VISIBLE else View.GONE

    fun getCloseButtonVisibility() = if (showCloseButton) View.VISIBLE else View.GONE
}
