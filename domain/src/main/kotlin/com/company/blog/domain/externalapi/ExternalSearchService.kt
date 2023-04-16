package com.company.blog.domain.externalapi

import com.company.blog.domain.dto.SearchDto

interface ExternalSearchService {
    fun searchBlog(request: SearchDto): DefaultBlogSearchResponse
}
