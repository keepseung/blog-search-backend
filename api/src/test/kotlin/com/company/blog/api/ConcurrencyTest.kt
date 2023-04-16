package com.company.blog.api

import com.company.blog.api.service.BlogCommandService
import com.company.blog.domain.respository.SearchKeywordRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class ConcurrencyTest {

    @Autowired
    lateinit var blogCommandService: BlogCommandService

    @Autowired
    lateinit var searchKeywordRepository: SearchKeywordRepository

    @Test
    fun `검색 수 동시성 테스트`() {
        val query = "코프링"

        val threadCount = 200
        val executorService = Executors.newFixedThreadPool(threadCount)
        val latch = CountDownLatch(threadCount)
        blogCommandService.plusKeywordCount(query)
        for (i in 1..threadCount) {
            executorService.execute {
                blogCommandService.plusKeywordCount(query)
                latch.countDown()
            }
        }
        latch.await()

        // 검색수 저장 검증
        val keyword = searchKeywordRepository.findTop1ByKeyword(query)
        Assertions.assertEquals(threadCount+1, keyword!!.count)
    }
}
