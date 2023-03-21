package com.company.blog.util // ktlint-disable filename

import com.company.blog.domain.SearchKeyword
import com.company.blog.externalapi.DefaultBlogSearchResponse
import com.company.blog.externalapi.Document
import com.company.blog.externalapi.Meta
import com.company.blog.service.dto.DefaultBlogSearchDto
import io.github.serpro69.kfaker.Faker

val faker = Faker()

fun searchKeywordEntityList() = listOf(
    SearchKeyword(
        keyword = "자바",
        count = 10,
    ),
    SearchKeyword(
        keyword = "코틀린",
        count = 20,
    ),
    SearchKeyword(
        keyword = "파이썬",
        count = 30,
    ),
)

fun defaultBlogSearchDto() = DefaultBlogSearchDto(
    documents = listOf(
        createDocument(),
        createDocument(),
        createDocument(),
    ),
    meta = Meta(
        totalCount = faker.random.nextInt(0, 100_000),
        pageableCount = faker.random.nextInt(0, 100_000),
        isEnd = faker.random.nextBoolean(),
    )
)

fun defaultBlogSearchResponse() = DefaultBlogSearchResponse(
    documents = listOf(
        createDocument(),
        createDocument(),
        createDocument(),
    ),
    meta = Meta(
        totalCount = faker.random.nextInt(0, 100_000),
        pageableCount = faker.random.nextInt(0, 100_000),
        isEnd = faker.random.nextBoolean(),
    )
)

fun createDocument() = Document(
    title = faker.random.randomString(),
    contents = faker.random.randomString(),
    url = faker.random.randomString(),
    blogname = faker.random.randomString(),
    thumbnail = faker.random.randomString(),
    datetime = faker.random.randomString(),
)
