package com.example.mynewsapp.news

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("everything")
    fun getTopNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<NewsResponse>
}