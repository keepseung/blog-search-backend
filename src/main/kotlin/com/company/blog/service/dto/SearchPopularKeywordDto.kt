package com.company.blog.service.dto

import com.company.blog.domain.SearchKeyword

data class SearchPopularKeywordDto(
    val id: Long,
    val keyword: String,
    var count: Int,
) {
    companion object {
        fun of(searchKeyword: SearchKeyword) = SearchPopularKeywordDto(
            id = searchKeyword.id,
            keyword = searchKeyword.keyword,
            count = searchKeyword.count,
        )
    }
}
