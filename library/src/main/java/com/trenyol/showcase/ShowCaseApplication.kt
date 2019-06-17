package com.trenyol.showcase

import android.app.Application

class ShowCaseApplication: Application() {

    var cancelListener: CancelListener? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    override fun onTerminate() {
        cancelListener = null
        super.onTerminate()
    }

    companion object {

        lateinit var INSTANCE: ShowCaseApplication
    }
}