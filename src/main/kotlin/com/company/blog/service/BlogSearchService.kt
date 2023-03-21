package com.company.blog.service

import com.company.blog.service.dto.DefaultBlogSearchDto
import com.company.blog.service.dto.SearchDto
import com.company.blog.web.exception.ExternalApiException
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
