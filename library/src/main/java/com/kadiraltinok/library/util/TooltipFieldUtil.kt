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
        val x = (view.width / 2).toDouble()
        val y = (view.height / 2).toDouble()

        return Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0)).toFloat()
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
