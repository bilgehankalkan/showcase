package com.trendyol.showcase.util

import android.content.res.Resources
import android.graphics.Rect
import android.graphics.RectF

internal fun Rect.toRectF() = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())

internal fun Resources.getHeightInPixels() = displayMetrics.heightPixels

internal fun Resources.getDensity() = displayMetrics.density
