package com.kadiraltinok.library.util

import android.content.res.Resources
import android.view.View
import com.kadiraltinok.library.ui.tooltip.ArrowPosition

object TooltipFieldUtil {

    fun calculateArrowPosition(resources: Resources, verticalCenter: Float): ArrowPosition {
        val screenHeight = resources.displayMetrics.heightPixels

        return if (screenHeight / 2 > verticalCenter) ArrowPosition.UP else ArrowPosition.DOWN
    }

    fun getRect(view: View): IntArray {
        val array = IntArray(2)
        view.getLocationOnScreen(array)

        return intArrayOf(array[0], array[1], array[0] + view.width, array[1] + view.height)
    }

    fun calculateRadius(view: View): Float {
        val x = (view.width / 2).toFloat()
        val y = (view.height / 2).toFloat()

        return Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0)).toFloat()
    }

    fun calculateArrowPercentage(resources: Resources, horizontalCenter: Float): Float {
        val arrowCenterWidth = (15 * resources.displayMetrics.density).toInt()
        val screenCenter = resources.displayMetrics.widthPixels / 2
        val arrowWidthFactor = when {
            screenCenter == horizontalCenter.toInt() -> 0
            screenCenter > horizontalCenter -> -arrowCenterWidth
            else -> arrowCenterWidth
        }

        val percentage = (horizontalCenter + arrowWidthFactor) / (screenCenter * 2)

        return when {
            percentage < 0.1 -> 0.1F
            percentage > 0.9 -> 0.9F
            else -> percentage
        }
    }

    fun calculateMarginForCircle(resources: Resources, top: Float, bottom: Float, arrowPosition: ArrowPosition, statusBarDiff: Int) = when (arrowPosition) {
        ArrowPosition.UP -> bottom.toInt() + statusBarDiff
        ArrowPosition.DOWN -> resources.displayMetrics.heightPixels - top.toInt()// + statusBarDiff
        else -> 0//throw IllegalArgumentException("arrowPosition should be ArrowPosition.UP or ArrowPosition.DOWN")
    }

    fun calculateMarginForRectangle(resources: Resources, top: Float, bottom: Float, arrowPosition: ArrowPosition, statusBarDiff: Int) = when (arrowPosition) {
        ArrowPosition.UP -> bottom.toInt() + statusBarDiff
        ArrowPosition.DOWN -> resources.displayMetrics.heightPixels - top.toInt()// + statusBarDiff
        else -> 0//throw IllegalArgumentException("arrowPosition should be ArrowPosition.UP or ArrowPosition.DOWN")
    }

    fun calculateArrowMargin(resources: Resources, horizontalCenter: Float): Int {
        val arrowHalfWidth = (15 * resources.displayMetrics.density)

        return (horizontalCenter - arrowHalfWidth).toInt()
    }
}

