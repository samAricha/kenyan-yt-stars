package com.myapplication

import android.app.Application
import com.myapplication.di.androidModule
import dev.icerock.moko.mvvm.core.BuildConfig
import di.KoinInit
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level


class KenyanYtApp : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinInit().init {
            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(androidContext = this@KenyanYtApp)
            modules(
                listOf(
                    androidModule,
                ),
            )
        }
    }
}
