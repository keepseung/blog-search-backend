package com.company.blog.api.controller.model

import com.company.blog.domain.dto.SearchPopularKeywordDto

data class SearchPopularKeywordResponse(
    val id: Long,
    val keyword: String,
    var count: Int,
) {
    companion object {
        fun of(searchKeyword: SearchPopularKeywordDto) = SearchPopularKeywordResponse(
            id = searchKeyword.id,
            keyword = searchKeyword.keyword,
            count = searchKeyword.count,
        )
    }
}
