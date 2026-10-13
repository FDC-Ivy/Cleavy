package com.cleavy.cleavy

import android.app.Application
import com.cleavy.cleavy.di.AppContainer

class CleavyApplication : Application() {
    val appContainer: AppContainer by lazy {
        AppContainer(applicationContext)
    }
}