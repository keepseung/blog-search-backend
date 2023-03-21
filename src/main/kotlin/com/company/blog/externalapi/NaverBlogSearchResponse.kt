package com.company.blog.externalapi

data class NaverBlogSearchResponse(
    val total: Int,
    val items: List<Item>,
)

data class Item(
    val title: String,
    val description: String,
    val link: String,
    val bloggername: String,
    val postdate: String,
)
