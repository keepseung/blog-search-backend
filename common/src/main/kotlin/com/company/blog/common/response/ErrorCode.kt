package com.company.blog.common.response

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: HttpStatus, val message: String) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    EXTERNAL_API_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "외부 서비스와 통신 중 에러 발생했습니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예기치 못한 서버 에러입니다."),
}
