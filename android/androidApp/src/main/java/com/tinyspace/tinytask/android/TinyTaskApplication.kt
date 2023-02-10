package com.tinyspace.tinytask.android

import android.app.Application
import com.tinyspace.tinytask.di.initKoin


class TinyTaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}