package com.company.blog.domain.externalapi

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverApi {
    @GET("v1/search/blog.json")
    suspend fun searchBlog(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int?,
        @Query("start") start: Int?,
        @Query("sort") sort: String?,
    ): NaverBlogSearchResponse?
}
