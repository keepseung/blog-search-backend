package com.company.blog.controller.model

import com.company.blog.service.dto.SearchPopularKeywordDto

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
