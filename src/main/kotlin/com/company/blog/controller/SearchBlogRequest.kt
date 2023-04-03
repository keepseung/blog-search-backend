package com.company.blog.controller

import com.company.blog.domain.SortType
import com.company.blog.service.dto.SearchDto
import javax.validation.constraints.NotBlank

data class SearchBlogRequest(
    @field:NotBlank(message = "검색어는 필수입니다.")
    val query: String,
    val page: Int?,
    val size: Int?,
    val sortType: SortType?,
) {
    fun toDto() = SearchDto(
        query = this.query,
        page = this.page,
        size = this.size,
        sortType = this.sortType,
    )
}
