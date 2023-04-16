package com.company.blog.domain.externalapi

import com.company.blog.common.exception.BaseException
import com.company.blog.common.logger.BlogLogger
import com.company.blog.common.response.ErrorCode
import com.company.blog.domain.dto.SearchDto
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class KakaoExternalBlogSearchAdapter(
    val kakaoApi: KakaoApi,
) : ExternalSearchService {
    @Value("\${kakao.api-token}")
    lateinit var kakaoApiToken: String

    override fun searchBlog(request: SearchDto): DefaultBlogSearchResponse {
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
        }.getOrElse { e ->
            log.error("카카오 블로그 API 호출 에러: ${e.message}")
            throw BaseException(ErrorCode.EXTERNAL_API_EXCEPTION)
        }
    }
    companion object: BlogLogger()
}
