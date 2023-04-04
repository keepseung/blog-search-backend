package com.company.blog.api.controller.model

import com.company.blog.domain.externalapi.Document

data class SearchBlogResponse(
    val title: String,
    val contents: String,
    val url: String,
    val blogname: String,
    val thumbnail: String?,
    val datetime: String,
) {
    companion object {
        fun of(document: Document) = SearchBlogResponse(
            title = document.title,
            contents = document.contents,
            url = document.url,
            blogname = document.blogname,
            thumbnail = document.thumbnail,
            datetime = document.datetime,
        )
    }
}
