package com.company.blog.domain.dto

import com.company.blog.domain.entity.SortType

data class SearchDto(
    val query: String,
    val page: Int?,
    val size: Int?,
    val sortType: SortType?,
)
