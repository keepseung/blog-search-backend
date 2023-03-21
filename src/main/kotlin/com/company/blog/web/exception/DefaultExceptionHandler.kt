package com.company.blog.web.exception

import com.company.blog.web.logger.BlogLogger
import com.company.blog.web.response.ErrorData
import com.company.blog.web.response.ExceptionResponse
import com.company.blog.web.response.MessageCode
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class DefaultExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun <T> handleException(e: Exception): ResponseEntity<ExceptionResponse> {
        log.error(e.stackTraceToString())
        val unknownError = MessageCode.UNKNOWN_ERROR
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ExceptionResponse(error = ErrorData(unknownError.name, unknownError.message)))
    }

    @ExceptionHandler(BaseException::class)
    fun <T> handleBusinessException(e: BaseException): ResponseEntity<ExceptionResponse> {
        log.warn(e.message)
        return ResponseEntity
            .status(e.status.value())
            .body(ExceptionResponse(error = ErrorData(e.code.name, e.message)))
    }

    companion object : BlogLogger()
}
