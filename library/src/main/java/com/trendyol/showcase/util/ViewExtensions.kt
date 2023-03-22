package com.trendyol.showcase.util

import android.content.ContextWrapper
import android.view.View
import com.trendyol.showcase.ui.showcase.ShowcaseActivity

internal fun View.statusBarHeight(isStatusBarVisible: Boolean = true): Int {
    return if (isStatusBarVisible.not()) {
        0
    } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
        val displayCutout = rootWindowInsets?.displayCutout
        if (displayCutout == null) {
            -resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"))
        } else {
            -displayCutout.safeInsetTop
        }
    } else {
        -resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"))
    }
}

/**
 *
 * @see <a>https://stackoverflow.com/a/4744499</a>
 */
internal fun View.isNavigationBarVisible(): Boolean {
    val identifier = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    return identifier > 0 && resources.getBoolean(identifier)
}

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
