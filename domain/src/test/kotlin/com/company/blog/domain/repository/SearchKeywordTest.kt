package com.company.blog.domain.repository

import com.company.blog.domain.respository.SearchKeywordRepository
import com.company.blog.domain.util.searchKeywordEntityList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.stream.Stream

@DataJpaTest
class SearchKeywordTest {

    @Autowired
    lateinit var searchKeywordRepository: SearchKeywordRepository

    @BeforeEach
    fun setUp() {
        searchKeywordRepository.deleteAll()
        val searchKeywords = searchKeywordEntityList()
        searchKeywordRepository.saveAll(searchKeywords)
    }

    @Test
    fun findTop10ByOrderByCountDesc() {
        val searchKeywords = searchKeywordRepository.findTop10ByOrderByCountDesc()
        assertEquals(3, searchKeywords.size)
    }

    @ParameterizedTest
    @MethodSource("keywordAndSize")
    fun findByKeyword(name: String, expectedSize: Int) {
        val searchKeyword = searchKeywordRepository.findWithLockByKeyword(name)
        assertEquals(expectedSize, searchKeyword!!.count)
    }

    companion object {
        @JvmStatic
        fun keywordAndSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments("자바", 10),
                Arguments.arguments("코틀린", 20),
                Arguments.arguments("파이썬", 30),
            )
        }
    }
}
