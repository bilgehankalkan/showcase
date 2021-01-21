package com.trendyol.showcase.showcase

import android.graphics.RectF
import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.trendyol.showcase.ui.showcase.HighlightType
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import com.trendyol.showcase.ui.tooltip.TextPosition
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowcaseModel(
    val rectF: RectF,
    val radius: Float,
    val titleText: String,
    val descriptionText: String,
    @ColorInt val titleTextColor: Int,
    @ColorInt val descriptionTextColor: Int,
    @ColorInt val popupBackgroundColor: Int,
    @ColorInt val closeButtonColor: Int,
    val showCloseButton: Boolean,
    val highlightType: HighlightType,
    @DrawableRes val arrowResource: Int,
    val arrowPosition: ArrowPosition,
    val arrowPercentage: Int?,
    val windowBackgroundColor: Int,
    val windowBackgroundAlpha: Int,
    val titleTextSize: Float,
    val descriptionTextSize: Float,
    val highlightPadding: Float,
    val cancellableFromOutsideTouch: Boolean,
    val isDebugMode: Boolean,
    val textPosition: TextPosition,
    val imageUrl: String,
    @LayoutRes val customContent: Int?,
    val isStatusBarVisible: Boolean
) : Parcelable {

    fun horizontalCenter() = rectF.left + ((rectF.right - rectF.left) / 2)
    fun verticalCenter() = rectF.top + ((rectF.bottom - rectF.top) / 2)

    fun bottomOfCircle() = verticalCenter() + radius
    fun topOfCircle() = verticalCenter() - radius
}
