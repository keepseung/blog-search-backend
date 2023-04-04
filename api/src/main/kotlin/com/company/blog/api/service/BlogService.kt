package com.company.blog.api.service

import com.company.blog.domain.dto.DefaultBlogSearchDto
import com.company.blog.domain.dto.SearchDto
import org.springframework.stereotype.Service

@Service
class BlogService(
    val blogSearchService: BlogSearchService,
    val blogCommandService: BlogCommandService,
) {
    fun search(searchDto: SearchDto): DefaultBlogSearchDto {
        val search = blogSearchService.search(searchDto)
        blogCommandService.plusKeywordCount(searchDto.query)
        return search
    }
}
