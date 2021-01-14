package com.trendyol.showcase.util

import android.content.ContextWrapper
import android.view.View
import com.trendyol.showcase.ui.showcase.ShowcaseActivity

internal fun View.statusBarHeight() =
    -resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"))

internal fun View.getShowcaseActivity(): ShowcaseActivity? {
    var mContext = context
    while (mContext is ContextWrapper) {
        if (mContext is ShowcaseActivity) {
            return mContext
        }
        mContext = (context as ContextWrapper).baseContext
    }
    return null
}
