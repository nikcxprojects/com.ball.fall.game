package com.ball.fall.game

import android.app.Application
import com.onesignal.OneSignal

class App : Application()  {

    private val ONESIGNAL_APP_ID = "1f87511d-63d5-4f76-b749-f18ab3082811"

    override fun onCreate() {
        super.onCreate()
        initOneConfig()
    }

    private fun initOneConfig() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this)

        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}