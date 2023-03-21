package com.company.blog.service.dto

import com.company.blog.domain.SortType

data class SearchDto(
    val query: String,
    val page: Int?,
    val size: Int?,
    val sortType: SortType?,
)
