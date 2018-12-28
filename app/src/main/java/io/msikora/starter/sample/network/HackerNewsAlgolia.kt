package io.msikora.starter.sample.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsAlgolia {

    @GET("/api/v1/search")
    fun search(@Query("query") query: String, @Query("tags") tag: String): Single<SearchResult>
}


