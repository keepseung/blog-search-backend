package com.company.blog.api.service

import com.company.blog.common.exception.ExternalApiException
import com.company.blog.domain.dto.DefaultBlogSearchDto
import com.company.blog.domain.dto.SearchDto
import com.company.blog.domain.externalapi.ExternalSearchService
import org.springframework.stereotype.Service

@Service
class BlogSearchService(
    val kakaoExternalBlogSearchAdapter: ExternalSearchService,
    val naverExternalBlogSearchAdapter: ExternalSearchService,
) {
    fun search(searchDto: SearchDto): DefaultBlogSearchDto = kakaoExternalBlogSearchAdapter.searchBlog(searchDto)?.let {
        DefaultBlogSearchDto(
            documents = it.documents,
            meta = it.meta
        )
    } ?: run {
        val searchResponse =
            naverExternalBlogSearchAdapter.searchBlog(searchDto) ?: throw ExternalApiException("네이버 블로그 API 호출 에러")
        DefaultBlogSearchDto(
            documents = searchResponse.documents,
            meta = searchResponse.meta
        )
    }
}
