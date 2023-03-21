package com.company.blog.externalapi

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/search/blog")
    suspend fun searchBlog(
        @Header("Authorization") token: String,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
    ): DefaultBlogSearchResponse
}
