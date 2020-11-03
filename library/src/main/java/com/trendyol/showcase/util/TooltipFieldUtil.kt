package com.trendyol.showcase.util

import android.content.res.Resources
import android.graphics.Rect
import com.trendyol.showcase.ui.tooltip.ArrowPosition
import kotlin.math.pow
import kotlin.math.sqrt

object TooltipFieldUtil {

    fun calculateArrowPosition(resources: Resources, verticalCenter: Float): ArrowPosition {
        val screenHeight = resources.displayMetrics.heightPixels

        return if (screenHeight / 2 > verticalCenter) ArrowPosition.UP else ArrowPosition.DOWN
    }

    fun calculateRadius(rect: Rect): Float {
        val x = rect.width().toDouble() / 2
        val y = rect.height().toDouble() / 2

        return sqrt(x.pow(2) + y.pow(2)).toFloat()
    }

    fun calculateMarginForCircle(resources: Resources, top: Float, bottom: Float, arrowPosition: ArrowPosition, statusBarDiff: Int) = when (arrowPosition) {
        ArrowPosition.UP -> bottom.toInt() + statusBarDiff
        ArrowPosition.DOWN -> (resources.displayMetrics.heightPixels - top.toInt()) + ((bottom.toInt() - top.toInt()) / 3) // + statusBarDiff
        else -> 0//throw IllegalArgumentException("arrowPosition should be ArrowPosition.UP or ArrowPosition.DOWN")
    }

    fun calculateMarginForRectangle(resources: Resources, top: Float, bottom: Float, arrowPosition: ArrowPosition, statusBarDiff: Int) = when (arrowPosition) {
        ArrowPosition.UP -> bottom.toInt() + statusBarDiff
        ArrowPosition.DOWN -> bottom.toInt() - top.toInt()
        else -> 0//throw IllegalArgumentException("arrowPosition should be ArrowPosition.UP or ArrowPosition.DOWN")
    }

    fun calculateArrowMargin(resources: Resources, horizontalCenter: Float): Int {
        val arrowHalfWidth = (15 * resources.displayMetrics.density)

        return (horizontalCenter - arrowHalfWidth).toInt()
    }
}
