package com.company.blog.api.controller

import com.company.blog.api.service.BlogQueryService
import com.company.blog.api.service.BlogService
import com.company.blog.api.util.defaultBlogSearchDto
import com.company.blog.api.util.searchKeywordEntityList
import com.company.blog.common.exception.BaseException
import com.company.blog.common.response.ErrorCode
import com.company.blog.domain.dto.SearchDto
import com.company.blog.domain.dto.SearchPopularKeywordDto
import com.company.blog.domain.entity.SortType
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [BlogController::class])
@AutoConfigureMockMvc
@DisplayName("블로그 컨트롤러 단위 테스트")
class BlogControllerUnitTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var blogService: BlogService

    @MockkBean
    lateinit var blogQueryService: BlogQueryService

    @Nested
    @DisplayName("블로그 검색 단위 테스트")
    inner class SearchBlog {
        @Test
        fun `카카오 블로그 검색 API, 네이버 블로그 API 둘 다 장애인 경우 500 응답 반환`() {
            val searchDto = SearchDto("스프링 코틀린", 1, 10, SortType.ACCURACY)

            every { blogService.search(any()) } throws BaseException(ErrorCode.EXTERNAL_API_EXCEPTION)

            mockMvc
                .perform(
                    get("/api/blog/search")
                        .param("query", searchDto.query)
                        .param("page", searchDto.page.toString())
                        .param("size", searchDto.size.toString())
                        .param("sort", searchDto.sortType!!.name)
                )
                .andExpect(status().is5xxServerError)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(ErrorCode.EXTERNAL_API_EXCEPTION.name))
                .andDo(MockMvcResultHandlers.print())
        }

        @Test
        fun `query 값이 비어있는 경우 400 예외 반환`() {
            val searchDto = SearchDto("스프링 코틀린", 1, 10, SortType.ACCURACY)

            val defaultBlogSearchDto = defaultBlogSearchDto()
            every { blogService.search(any()) } returns defaultBlogSearchDto

            mockMvc
                .perform(
                    get("/api/blog/search")
                        .param("query", "")
                        .param("page", searchDto.page.toString())
                        .param("size", searchDto.size.toString())
                        .param("sortType", searchDto.sortType.toString())
                )
                .andExpect(status().is4xxClientError)
                .andExpect(jsonPath("$.error.code").value(ErrorCode.BAD_REQUEST.name))
                .andExpect(jsonPath("$.error.message").value("검색어는 필수입니다."))
                .andDo(MockMvcResultHandlers.print())
        }

        @Test
        fun `블로그 검색 성공`() {
            val searchDto = SearchDto("스프링 코틀린", 1, 10, SortType.ACCURACY)

            val defaultBlogSearchDto = defaultBlogSearchDto()
            val firstDocument = defaultBlogSearchDto.documents.first()
            val meta = defaultBlogSearchDto.meta

            every { blogService.search(any()) } returns defaultBlogSearchDto

            mockMvc
                .perform(
                    get("/api/blog/search")
                        .param("query", searchDto.query)
                        .param("page", searchDto.page.toString())
                        .param("size", searchDto.size.toString())
                        .param("sort", searchDto.sortType!!.name)
                )
                .andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray)
                .andExpect(jsonPath("$.data[0].title").value(firstDocument.title))
                .andExpect(jsonPath("$.data[0].contents").value(firstDocument.contents))
                .andExpect(jsonPath("$.totalCount").value(meta.totalCount))
                .andExpect(jsonPath("$.pageableCount").value(meta.pageableCount))
                .andExpect(jsonPath("$.isEnd").value(meta.isEnd))
                .andDo(MockMvcResultHandlers.print())
        }
    }

    @Nested
    @DisplayName("인기 검색어 조회 단위 테스트")
    inner class PopularKeyword {
        @Test
        fun findPopularKeywordReturnSuccess() {
            val searchPopularKeywordDtos = searchKeywordEntityList().map { SearchPopularKeywordDto.of(it) }
            every { blogQueryService.findPopularKeyword() } returns searchPopularKeywordDtos

            mockMvc
                .perform(get("/api/blog/popular-keyword"))
                .andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray)
                .andDo(MockMvcResultHandlers.print())
        }
    }
}
