package com.company.blog.externalapi

import com.company.blog.service.ExternalSearchService
import com.company.blog.service.dto.SearchDto
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class KakaoExternalBlogSearchAdapter(
    val kakaoApi: KakaoApi,
) : ExternalSearchService {
    @Value("\${kakao.api-token}")
    lateinit var kakaoApiToken: String

    override fun searchBlog(request: SearchDto): DefaultBlogSearchResponse? {
        val (keyword, page, size, sortType) = request
        return runCatching {
            runBlocking {
                kakaoApi.searchBlog(
                    token = "KakaoAK $kakaoApiToken",
                    query = keyword,
                    page = page,
                    size = size,
                    sort = sortType?.name?.lowercase(),
                )
            }
        }.getOrNull()
    }
}
