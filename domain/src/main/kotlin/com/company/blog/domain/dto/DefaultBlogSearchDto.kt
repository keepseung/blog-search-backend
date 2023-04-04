package com.company.blog.domain.dto

import com.company.blog.domain.externalapi.Document
import com.company.blog.domain.externalapi.Meta

data class DefaultBlogSearchDto(
    val documents: List<Document>,
    val meta: Meta,
)
