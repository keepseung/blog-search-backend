package com.company.blog.controller

import com.company.blog.controller.model.SearchBlogResponse
import com.company.blog.controller.model.SearchPopularKeywordResponse
import com.company.blog.service.BlogQueryService
import com.company.blog.service.BlogService
import com.company.blog.web.response.MultiResponse
import com.company.blog.web.response.PageResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("api/blog")
class BlogController(
    private val blogService: BlogService,
    private val blogQueryService: BlogQueryService,
) {
    @GetMapping("search")
    fun searchBlog(
        @Valid @ModelAttribute
        request: SearchBlogRequest,
    ): PageResponse<SearchBlogResponse> =
        blogService.search(request.toDto()).let { dto ->
            val meta = dto.meta
            PageResponse(
                data = dto.documents.map { document -> SearchBlogResponse.of(document) },
                pageableCount = meta.pageableCount,
                totalCount = meta.totalCount,
                isEnd = meta.isEnd,
            )
        }

    @GetMapping("popular-keyword")
    fun findPopularKeyword(): MultiResponse<SearchPopularKeywordResponse> =
        MultiResponse(data = blogQueryService.findPopularKeyword().map { SearchPopularKeywordResponse.of(it) })
}
