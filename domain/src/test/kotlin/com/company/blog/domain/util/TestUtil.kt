package com.company.blog.domain.util

import com.company.blog.domain.entity.SearchKeyword

fun searchKeywordEntityList() = listOf(
    SearchKeyword(
        keyword = "자바",
        count = 10,
    ),
    SearchKeyword(
        keyword = "코틀린",
        count = 20,
    ),
    SearchKeyword(
        keyword = "파이썬",
        count = 30,
    ),
)
