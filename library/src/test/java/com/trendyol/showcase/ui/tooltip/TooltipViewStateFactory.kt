package com.trendyol.showcase.ui.tooltip

import android.graphics.Color
import com.trendyol.showcase.R

object TooltipViewStateFactory {

    fun getInstance() = TooltipViewState(
        titleText = "",
        descriptionText = "",
        titleTextColor = Color.BLACK,
        descriptionTextColor = Color.BLACK,
        backgroundColor = Color.WHITE,
        closeButtonColor = Color.BLACK,
        showCloseButton = true,
        arrowResource = R.drawable.ic_showcase_arrow_down,
        arrowPosition = ArrowPosition.DOWN,
        arrowPercentage = 50,
        arrowMargin = 0,
        titleTextSize = 16F,
        descriptionTextSize = 14F,
        textPosition = TextPosition.CENTER,
        imageUrl = ""
    )
}