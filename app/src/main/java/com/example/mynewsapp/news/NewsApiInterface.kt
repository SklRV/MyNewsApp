package com.example.mynewsapp.news

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("everything")
    fun getTopRatedMovies(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Single<NewsResponse>
}