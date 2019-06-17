package com.kadiraltinok.library.util

import android.app.Activity
import android.content.ContextWrapper
import android.view.View

fun View.statusBarHeight() = -resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"))

fun View.getActivity(): Activity? {
    var mContext = context
    while (mContext is ContextWrapper) {
        if (mContext is Activity) {
            return mContext
        }
        mContext = (context as ContextWrapper).baseContext
    }
    return null
}
