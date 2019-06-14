package com.kadiraltinok.library.showcase

import androidx.annotation.ColorInt
import com.kadiraltinok.library.ui.showcase.HighlightType
import com.kadiraltinok.library.ui.tooltip.ArrowPosition
import java.io.Serializable

data class ShowcaseModel(val left: Float,
                         val top: Float,
                         val right: Float,
                         val bottom: Float,
                         val radius: Float,
                         val titleText: String,
                         val descriptionText: String,
                         @ColorInt val titleTextColor: Int,
                         @ColorInt val descriptionTextColor: Int,
                         @ColorInt val popupBackgroundColor: Int,
                         @ColorInt val closeButtonColor: Int,
                         val showCloseButton: Boolean,
                         val arrowPosition: ArrowPosition,
                         val highlightType: HighlightType,
                         val arrowPercentage: Float?,
                         val windowBackgroundColor: Int,
                         val windowBackgroundAlpha: Int,
                         val titleTextSize: Float,
                         val descriptionTextSize: Float
) : Serializable {

    fun horizontalCenter() = (left + ((right - left) / 2))
    fun verticalCenter() = (top + ((bottom - top) / 2))

    fun bottomOfCircle() = verticalCenter() + radius
    fun topOfCircle() = verticalCenter() - radius
}
