package com.company.blog.service

import com.company.blog.domain.SearchKeyword
import com.company.blog.respository.SearchKeywordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BlogCommandService(
    val searchKeywordRepository: SearchKeywordRepository,
) {
    fun plusKeywordCount(query: String) {
        searchKeywordRepository.findWithLockByKeyword(query)?.plusCount() ?: run {
            searchKeywordRepository.save(
                SearchKeyword(
                    keyword = query,
                    count = SAVE_COUNT,
                )
            )
        }
    }

    companion object {
        private const val SAVE_COUNT = 1
    }
}
