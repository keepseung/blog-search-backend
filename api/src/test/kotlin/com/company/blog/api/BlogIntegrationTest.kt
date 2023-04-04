package com.company.blog.api

import com.company.blog.api.util.defaultBlogSearchResponse
import com.company.blog.api.util.searchKeywordEntityList
import com.company.blog.common.exception.ExternalApiException
import com.company.blog.common.response.MessageCode
import com.company.blog.domain.dto.SearchDto
import com.company.blog.domain.entity.SortType
import com.company.blog.domain.externalapi.KakaoExternalBlogSearchAdapter
import com.company.blog.domain.externalapi.NaverExternalBlogSearchAdapter
import com.company.blog.domain.respository.SearchKeywordRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("블로그 통합 테스트")
class BlogIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var searchKeywordRepository: SearchKeywordRepository

    @MockkBean
    lateinit var naverExternalBlogSearchAdapter: NaverExternalBlogSearchAdapter

    @MockkBean
    lateinit var kakaoExternalBlogSearchAdapter: KakaoExternalBlogSearchAdapter

    @BeforeEach
    fun setUp() {
        searchKeywordRepository.deleteAll()
        val searchKeywords = searchKeywordEntityList()
        searchKeywordRepository.saveAll(searchKeywords)
    }

    @Nested
    @DisplayName("블로그 검색 통합 테스트")
    inner class SearchBlog {
        @Test
        fun `카카오 블로그 검색 API로 블로그 검색 성공`() {
            val query = "코틀린 스프링"
            val searchDto = SearchDto(query, 1, 10, SortType.ACCURACY)

            val defaultBlogSearchResponse = defaultBlogSearchResponse()
            val firstDocument = defaultBlogSearchResponse.documents.first()
            val meta = defaultBlogSearchResponse.meta

            every { kakaoExternalBlogSearchAdapter.searchBlog(searchDto) } returns defaultBlogSearchResponse

            mockMvc
                .perform(
                    MockMvcRequestBuilders.get("/api/blog/search")
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

            // 검색수 저장 검증
            val keyword = searchKeywordRepository.findByKeyword(query)
            assertEquals(1, keyword!!.count)
        }

        @Test
        fun `카카오 블로그 검색 API 장애인 경우 네이버 블로그 API로 검색 성공`() {
            val query = "스프링 코틀린"
            val searchDto = SearchDto(query, 1, 10, SortType.ACCURACY)

            val defaultBlogSearchResponse = defaultBlogSearchResponse()
            val firstDocument = defaultBlogSearchResponse.documents.first()

            every { kakaoExternalBlogSearchAdapter.searchBlog(searchDto) } returns null
            every { naverExternalBlogSearchAdapter.searchBlog(searchDto) } returns defaultBlogSearchResponse

            mockMvc
                .perform(
                    MockMvcRequestBuilders.get("/api/blog/search")
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
                .andDo(MockMvcResultHandlers.print())

            // 검색수 저장 검증
            val keyword = searchKeywordRepository.findByKeyword(query)
            assertEquals(1, keyword!!.count)
        }

        @Test
        fun `카카오 블로그 검색 API, 네이버 블로그 API 둘 다 장애인 경우 500 응답 반환`() {
            val query = "스프링 코틀린"
            val searchDto = SearchDto(query, 1, 10, SortType.ACCURACY)

            val defaultBlogSearchResponse = defaultBlogSearchResponse()

            every { kakaoExternalBlogSearchAdapter.searchBlog(searchDto) } returns null
            val errorMessage = "외부 API 호출시 예외 발생했습니다."
            every { naverExternalBlogSearchAdapter.searchBlog(searchDto) } throws ExternalApiException(errorMessage)

            mockMvc
                .perform(
                    MockMvcRequestBuilders.get("/api/blog/search")
                        .param("query", searchDto.query)
                        .param("page", searchDto.page.toString())
                        .param("size", searchDto.size.toString())
                        .param("sort", searchDto.sortType!!.name)
                )
                .andExpect(status().is5xxServerError)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.code").value(MessageCode.EXTERNAL_API_EXCEPTION.name))
                .andExpect(jsonPath("$.error.message").value(errorMessage))
                .andDo(MockMvcResultHandlers.print())

            // 검색수 저장 안된 것 검증
            val keyword = searchKeywordRepository.findByKeyword(query)
            assertEquals(null, keyword)
        }

        @Test
        fun `카카오 블로그 검색 API로 블로그 검색 성공 - 이전에 검색했던 검색어의 조회수가 증가함`() {
            val searchKeyword = searchKeywordEntityList()[0]
            val query = searchKeyword.keyword
            val count = searchKeyword.count
            val searchDto = SearchDto(query, 1, 10, SortType.ACCURACY)

            val defaultBlogSearchResponse = defaultBlogSearchResponse()
            val firstDocument = defaultBlogSearchResponse.documents.first()
            val meta = defaultBlogSearchResponse.meta

            every { kakaoExternalBlogSearchAdapter.searchBlog(searchDto) } returns defaultBlogSearchResponse

            mockMvc
                .perform(
                    MockMvcRequestBuilders.get("/api/blog/search")
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

            // 검색수 저장 검증
            val keyword = searchKeywordRepository.findByKeyword(query)
            assertEquals(count + 1, keyword!!.count)
        }
    }

    @Nested
    @DisplayName("인기 검색어 목록 테스트")
    inner class PopularKeyword {
        @Test
        fun `인기 검색어 목록 조회 성공`() {
            val searchKeyword = searchKeywordRepository.findTop10ByOrderByCountDesc().first()

            mockMvc
                .perform(MockMvcRequestBuilders.get("/api/blog/popular-keyword"))
                .andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray)
                .andExpect(jsonPath("$.data[0].id").value(searchKeyword!!.id))
                .andExpect(jsonPath("$.data[0].keyword").value(searchKeyword.keyword))
                .andExpect(jsonPath("$.data[0].count").value(searchKeyword.count))
                .andDo(MockMvcResultHandlers.print())
        }
    }
}
