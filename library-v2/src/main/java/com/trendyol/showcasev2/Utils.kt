package com.trendyol.showcasev2

import android.util.Log

inline fun <reified T> T.log(message: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d("ShowcaseV2", "${T::class.java.simpleName}: ${message.invoke()}")
    }
}