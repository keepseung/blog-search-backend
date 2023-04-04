package com.company.blog.api.controller

import com.company.blog.domain.dto.SearchDto
import com.company.blog.domain.entity.SortType
import javax.validation.constraints.NotBlank

data class SearchBlogRequest(
    @field:NotBlank(message = "검색어는 필수입니다.")
    val query: String,
    val page: Int?,
    val size: Int?,
    val sort: SortType?,
) {
    fun toDto() = SearchDto(
        query = this.query,
        page = this.page,
        size = this.size,
        sortType = this.sort,
    )
}
