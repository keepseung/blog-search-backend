package com.company.blog.common.response

data class SingleResponse<out T>(
    val data: T? = null,
)

data class MultiResponse<out T>(
    val data: List<T> = listOf(),
)

data class PageResponse<out T>(
    val data: List<T> = listOf(),
    val totalCount: Int,
    val pageableCount: Int? = null,
    val isEnd: Boolean? = null,
)

data class ExceptionResponse(
    val error: ErrorData,
)

data class ErrorData(
    val code: String,
    val message: String?,
)
