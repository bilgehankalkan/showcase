package com.trendyol.showcase.ui.tooltip

import com.trendyol.showcase.R

object TooltipViewStateFactory {

    fun getInstance() = TooltipViewState(
        titleText = "",
        descriptionText = "",
        titleTextColor = R.color.black,
        descriptionTextColor = R.color.black,
        backgroundColor = R.color.white,
        closeButtonColor = R.color.black,
        showCloseButton = true,
        arrowResource = R.drawable.ic_custom_arrow_down,
        arrowPosition = ArrowPosition.DOWN,
        arrowPercentage = 50,
        arrowMargin = 0,
        titleTextSize = 16F,
        descriptionTextSize = 14F,
        textPosition = TextPosition.CENTER,
        imageUrl = ""
    )
}