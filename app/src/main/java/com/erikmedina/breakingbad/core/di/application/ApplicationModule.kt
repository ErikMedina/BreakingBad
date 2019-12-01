package com.erikmedina.breakingbad.core.di.application

import com.erikmedina.breakingbad.MyApp
import com.erikmedina.breakingbad.core.data.remote.network.ApiRest
import com.erikmedina.breakingbad.core.data.remote.network.RetrofitBuilder
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val myApp: MyApp) {

    @Provides
    internal fun provideApplication(): MyApp {
        return myApp
    }

    @Singleton
    @Provides
    fun provideApiRest(retrofitBuilder: RetrofitBuilder): ApiRest {
        return retrofitBuilder.apiRest()
    }

    @Singleton
    @Provides
    internal fun provideGson(): Gson {
        return Gson()
    }
}
