package com.erikmedina.breakingbad.core.data.remote.network

import com.erikmedina.breakingbad.core.data.remote.model.StackOverflowEntity
import io.reactivex.Single
import retrofit2.http.GET

/**
 * The API for all the requests of the application
 */
interface ApiRest {

    @GET("characters")
    fun getCharacters(): Single<StackOverflowEntity>
}
