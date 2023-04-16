package com.company.blog.common.exception

import com.company.blog.common.response.ErrorCode

class BaseException(
    val errorCode: ErrorCode,
) : RuntimeException()
