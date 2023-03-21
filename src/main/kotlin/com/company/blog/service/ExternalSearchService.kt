package com.company.blog.service

import com.company.blog.externalapi.DefaultBlogSearchResponse
import com.company.blog.service.dto.SearchDto

interface ExternalSearchService {
    fun searchBlog(request: SearchDto): DefaultBlogSearchResponse?
}
