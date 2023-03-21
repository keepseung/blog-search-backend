package com.company.blog.service.dto

import com.company.blog.externalapi.Document
import com.company.blog.externalapi.Meta

data class DefaultBlogSearchDto(
    val documents: List<Document>,
    val meta: Meta,
)
