package com.company.blog.externalapi

import com.fasterxml.jackson.annotation.JsonProperty

data class DefaultBlogSearchResponse(
    val documents: List<Document>,
    val meta: Meta,
) {
    companion object {
        fun of(naverBlogSearchResponse: NaverBlogSearchResponse) = DefaultBlogSearchResponse(
            meta = Meta(
                totalCount = naverBlogSearchResponse.total,
                pageableCount = null,
                isEnd = null,
            ),
            documents = naverBlogSearchResponse.items.map { Document.of(it) }
        )
    }
}

data class Meta(
    @JsonProperty("total_count")
    val totalCount: Int,
    @JsonProperty("pageable_count")
    val pageableCount: Int?,
    @JsonProperty("is_end")
    val isEnd: Boolean?,
)

data class Document(
    val title: String,
    val contents: String,
    val url: String,
    val blogname: String,
    val thumbnail: String?,
    val datetime: String,
) {
    companion object {
        fun of(naverItem: Item) = Document(
            title = naverItem.title,
            contents = naverItem.description,
            url = naverItem.link,
            blogname = naverItem.bloggername,
            thumbnail = null,
            datetime = naverItem.postdate,
        )
    }
}
