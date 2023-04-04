package com.company.blog.common.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BlogLogger {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)
}
