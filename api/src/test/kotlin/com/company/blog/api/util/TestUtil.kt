package com.company.blog.api.util

import com.company.blog.domain.dto.DefaultBlogSearchDto
import com.company.blog.domain.entity.SearchKeyword
import com.company.blog.domain.externalapi.DefaultBlogSearchResponse
import com.company.blog.domain.externalapi.Document
import com.company.blog.domain.externalapi.Meta
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
