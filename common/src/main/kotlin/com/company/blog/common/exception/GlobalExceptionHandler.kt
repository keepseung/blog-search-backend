package com.company.blog.common.exception

import com.company.blog.common.logger.BlogLogger
import com.company.blog.common.response.ErrorData
import com.company.blog.common.response.ExceptionResponse
import com.company.blog.common.response.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ExceptionResponse> {
        log.error(e.stackTraceToString())
        val unknownError = ErrorCode.UNKNOWN_ERROR
        return ResponseEntity
            .status(unknownError.httpStatus.value())
            .body(ExceptionResponse(error = ErrorData(unknownError.name, unknownError.message)))
    }

    @ExceptionHandler(BaseException::class)
    fun handleBusinessException(e: BaseException): ResponseEntity<ExceptionResponse> {
        log.warn(e.message)
        val errorCode = e.errorCode
        return ResponseEntity
            .status(errorCode.httpStatus.value())
            .body(ExceptionResponse(error = ErrorData(errorCode.name, errorCode.message)))
    }

    @ExceptionHandler(BindException::class)
    fun bindException(exception: BindException): ResponseEntity<ExceptionResponse> {
        val fieldExceptionMessage = getFieldExceptionMessage(exception.bindingResult)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(
                ExceptionResponse(
                    error = ErrorData(
                        ErrorCode.BAD_REQUEST.name,
                        fieldExceptionMessage
                    )
                )
            )
    }

    private fun getFieldExceptionMessage(bindingResult: BindingResult): String =
        bindingResult.fieldErrors.joinToString(",") { it.defaultMessage ?: "" }

    companion object : BlogLogger()
}
