package com.trendyol.showcase.util

import android.graphics.Rect
import android.graphics.RectF

internal fun Rect.toRectF() = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
