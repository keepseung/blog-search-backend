package com.company.blog.externalapi.config

import com.company.blog.externalapi.KakaoApi
import com.company.blog.externalapi.NaverApi
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class RetrofitConfig {
    @Bean
    fun kakaoApi(): KakaoApi = Retrofit.Builder()
        .client(
            OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
                )
                .build()
        )
        .baseUrl(KAKAO_BASE_URL)
        .addConverterFactory(
            JacksonConverterFactory.create(
                jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            )
        )
        .build().create(KakaoApi::class.java)

    @Bean
    fun naverApi(): NaverApi = Retrofit.Builder()
        .client(
            OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
                )
                .build()
        )
        .baseUrl(NAVER_BASE_URL)
        .addConverterFactory(
            JacksonConverterFactory.create(
                jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            )
        )
        .build().create(NaverApi::class.java)

    companion object {
        private const val KAKAO_BASE_URL = "https://dapi.kakao.com"
        private const val NAVER_BASE_URL = "https://openapi.naver.com"
    }
}
