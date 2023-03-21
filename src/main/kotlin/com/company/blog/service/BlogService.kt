package com.company.blog.service

import com.company.blog.service.dto.DefaultBlogSearchDto
import com.company.blog.service.dto.SearchDto
import org.springframework.stereotype.Service

@Service
class BlogService(
    val blogSearchService: BlogSearchService,
    val blogCommandService: BlogCommandService,
) {
    fun search(searchDto: SearchDto): DefaultBlogSearchDto {
        val search = blogSearchService.search(searchDto)
        blogCommandService.saveKeyword(searchDto.query)
        return search
    }
}
