package com.company.blog.web.exception

import com.company.blog.web.logger.BlogLogger
import com.company.blog.web.response.ErrorData
import com.company.blog.web.response.ExceptionResponse
import com.company.blog.web.response.MessageCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DefaultExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ExceptionResponse> {
        log.error(e.stackTraceToString())
        val unknownError = MessageCode.UNKNOWN_ERROR
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ExceptionResponse(error = ErrorData(unknownError.name, unknownError.message)))
    }

    @ExceptionHandler(BaseException::class)
    fun handleBusinessException(e: BaseException): ResponseEntity<ExceptionResponse> {
        log.warn(e.message)
        return ResponseEntity
            .status(e.status.value())
            .body(ExceptionResponse(error = ErrorData(e.code.name, e.message)))
    }

    @ExceptionHandler(BindException::class)
    fun bindException(exception: BindException): ResponseEntity<ExceptionResponse> {
        val fieldExceptionMessage = getFieldExceptionMessage(exception.bindingResult)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(
                ExceptionResponse(
                    error = ErrorData(
                        MessageCode.BAD_REQUEST.name,
                        fieldExceptionMessage
                    )
                )
            )
    }

    private fun getFieldExceptionMessage(bindingResult: BindingResult): String =
        bindingResult.fieldErrors.joinToString(",") { it.defaultMessage ?: "" }

    companion object : BlogLogger()
}
