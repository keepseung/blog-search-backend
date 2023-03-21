package com.company.blog.controller

import com.company.blog.controller.model.SearchBlogResponse
import com.company.blog.domain.SortType
import com.company.blog.service.BlogQueryService
import com.company.blog.service.BlogService
import com.company.blog.service.dto.SearchDto
import com.company.blog.util.defaultBlogSearchDto
import com.company.blog.web.response.PageResponse
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

//@WebMvcTest(controllers = [BlogController::class])
@AutoConfigureWebTestClient
class BlogControllerUnitTest {
//    @Autowired
//    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var blogService: BlogService

    @MockkBean
    lateinit var blogQueryService: BlogQueryService
//
//    @Test
//    fun searchKeyword() {
//        val searchDto = SearchDto("스프링 코틀린", 1, 10, SortType.ACCURACY)
//
//        val defaultBlogSearchDto = defaultBlogSearchDto()
//        every { blogService.search(any()) } returns defaultBlogSearchDto
//        val pageResponse = webTestClient
//            .get()
//            .uri{builder-> builder
//                .path("/api/blog/search")
//                .queryParam("query", searchDto.query)
//                .queryParam("page", searchDto.page)
//                .queryParam("size", searchDto.size)
//                .build()}
//            .exchange()
//            .expectStatus().isOk
//            .expectBody<PageResponse<SearchBlogResponse>>()
//            .returnResult()
//            .responseBody
    }

}
