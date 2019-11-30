package com.erikmedina.breakingbad

import android.app.Application
import com.erikmedina.breakingbad.core.di.application.ApplicationComponent
import com.erikmedina.breakingbad.core.di.application.ApplicationModule
import com.erikmedina.breakingbad.core.di.application.DaggerApplicationComponent

class MyApp : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }
}
