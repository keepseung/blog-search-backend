package com.company.blog.controller

import com.company.blog.controller.model.SearchBlogResponse
import com.company.blog.controller.model.SearchPopularKeywordResponse
import com.company.blog.domain.SortType
import com.company.blog.service.BlogQueryService
import com.company.blog.service.BlogService
import com.company.blog.service.dto.SearchDto
import com.company.blog.web.exception.BadRequestException
import com.company.blog.web.response.MultiResponse
import com.company.blog.web.response.PageResponse
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/blog")
class BlogController(
    private val blogService: BlogService,
    private val blogQueryService: BlogQueryService,
) {
    @GetMapping("search")
    fun searchBlog(
        @RequestParam("query", required = true) query: String,
        @RequestParam("page", required = false)
        page: Int?,
        @RequestParam("size", required = false)
        size: Int?,
        @RequestParam("sort")
        sort: String?,
    ): PageResponse<SearchBlogResponse> {
        if (!StringUtils.hasText(query)) {
            throw BadRequestException("query 값이 비어있으면 안됩니다.")
        }
        val sortType = sort?.let {
            runCatching { SortType.valueOf(sort) }
                .getOrElse { throw BadRequestException("sortType 값이 부정확합니다.") }
        }

        return blogService.search(
            SearchDto(
                query = query,
                page = page,
                size = size,
                sortType = sortType,
            )
        ).let { dto ->
            val meta = dto.meta
            PageResponse(
                data = dto.documents.map { document -> SearchBlogResponse.of(document) },
                pageableCount = meta.pageableCount,
                totalCount = meta.totalCount,
                isEnd = meta.isEnd,
            )
        }
    }

    @GetMapping("popular-keyword")
    fun findPopularKeyword(): MultiResponse<SearchPopularKeywordResponse> =
        MultiResponse(data = blogQueryService.findPopularKeyword().map { SearchPopularKeywordResponse.of(it) })
}
