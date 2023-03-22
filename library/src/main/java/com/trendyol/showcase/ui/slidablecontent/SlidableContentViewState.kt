package com.trendyol.showcase.ui.slidablecontent

import com.trendyol.showcase.ui.tooltip.TextPosition

internal class SlidableContentViewState(val slidableContent: SlidableContent) {

    fun isTitleVisible() = slidableContent.title.isNullOrEmpty().not()

    fun isDescriptionVisible() = slidableContent.description.isNullOrEmpty().not()

    fun getTextPosition(): Int {
        return when (slidableContent.textPosition) {
            TextPosition.CENTER -> 4
            TextPosition.END -> 3
            else -> 2
        }
    }
}