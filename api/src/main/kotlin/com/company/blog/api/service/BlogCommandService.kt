package com.company.blog.api.service

import com.company.blog.domain.entity.SearchKeyword
import com.company.blog.domain.entity.SearchKeywordCount
import com.company.blog.domain.respository.SearchKeywordCountRepository
import com.company.blog.domain.respository.SearchKeywordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BlogCommandService(
    val searchKeywordRepository: SearchKeywordRepository,
    val searchKeywordCountRepository: SearchKeywordCountRepository,
) {
    fun plusKeywordCount(query: String) {
        val keyword = when (val searchKeyword = searchKeywordRepository.findWithLockByKeyword(query)) {
            null -> {
                searchKeywordRepository.save(
                    SearchKeyword(
                        keyword = query,
                        count = SAVE_COUNT,
                    )
                )
            }
            else -> {
                searchKeyword.plusCount()
                searchKeyword
            }
        }

        searchKeywordCountRepository.save(SearchKeywordCount(
            keywordId = keyword.id,
        ))
    }

    companion object {
        private const val SAVE_COUNT = 1
    }
}
