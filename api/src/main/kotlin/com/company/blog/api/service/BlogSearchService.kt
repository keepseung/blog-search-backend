package com.company.blog.api.service

import com.company.blog.common.logger.BlogLogger
import com.company.blog.domain.dto.DefaultBlogSearchDto
import com.company.blog.domain.dto.SearchDto
import com.company.blog.domain.externalapi.ExternalSearchService
import com.company.blog.domain.externalapi.Meta
import org.springframework.stereotype.Service

@Service
class BlogSearchService(
    private val kakaoExternalBlogSearchAdapter: ExternalSearchService,
    private val naverExternalBlogSearchAdapter: ExternalSearchService,
) {
    private val exteranlBlogSearchAdapters = listOf(kakaoExternalBlogSearchAdapter, naverExternalBlogSearchAdapter)

    fun search(searchDto: SearchDto): DefaultBlogSearchDto {
        val lastIndex = exteranlBlogSearchAdapters.size - 1
        for (i in 0..lastIndex) {
            val exteranlBlogSearchAdapter = exteranlBlogSearchAdapters[i]
            runCatching {
                exteranlBlogSearchAdapter.searchBlog(searchDto).let { response ->
                    DefaultBlogSearchDto(
                        documents = response.documents,
                        meta = response.meta
                    )
                }
            }.onSuccess { defaultBlogSearchDto ->
                return defaultBlogSearchDto
            }.onFailure {
                log.warn("외부 블로그 검색 API 실패함 class : {}", exteranlBlogSearchAdapter.javaClass)
                if (i == lastIndex) {
                    throw it
                }
            }
        }
        return DefaultBlogSearchDto(listOf(), Meta(0, 0, false))
    }

    companion object : BlogLogger()
}
