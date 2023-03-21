package com.company.blog.web.exception

import com.company.blog.web.response.MessageCode
import org.springframework.http.HttpStatus

open class BaseException(
    message: String?,
    val code: MessageCode = MessageCode.BAD_REQUEST,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
) : RuntimeException(message)

class BadRequestException(message: String) :
    BaseException(
        message = message,
        code = MessageCode.BAD_REQUEST,
        status = HttpStatus.BAD_REQUEST,
    )
class ExternalApiException(message: String) :
    BaseException(
        message = message,
        code = MessageCode.EXTERNAL_API_EXCEPTION,
        status = HttpStatus.INTERNAL_SERVER_ERROR,
    )
