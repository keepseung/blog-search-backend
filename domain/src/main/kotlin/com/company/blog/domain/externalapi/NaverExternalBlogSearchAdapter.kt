package com.company.blog.domain.externalapi

import com.company.blog.common.exception.ExternalApiException
import com.company.blog.common.logger.BlogLogger
import com.company.blog.domain.dto.SearchDto
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class NaverExternalBlogSearchAdapter(
    val naverApi: NaverApi,
) : ExternalSearchService {
    @Value("\${naver.client-id}")
    lateinit var naverClientId: String

    @Value("\${naver.client-secret}")
    lateinit var naverClientSecret: String

    override fun searchBlog(request: SearchDto): DefaultBlogSearchResponse? {
        val (keyword, page, size, sortType) = request
        return runCatching {
            runBlocking {
                naverApi.searchBlog(
                    clientId = naverClientId,
                    clientSecret = naverClientSecret,
                    query = keyword,
                    display = size,
                    start = page,
                    sort = sortType?.naverSortType?.lowercase(),
                )?.let { searchResponse ->
                    DefaultBlogSearchResponse.of(
                        searchResponse
                    )
                }
            }
        }.getOrElse { e ->
            log.error("네이버 블로그 API 호출 에러: ${e.message}")
            throw ExternalApiException("네이버 블로그 API 호출 에러")
        }
    }

    companion object : BlogLogger()
}
