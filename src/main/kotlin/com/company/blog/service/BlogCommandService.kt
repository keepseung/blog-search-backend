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
    fun saveKeyword(query: String) {
        searchKeywordRepository.findByKeyword(query)?.plusCount() ?: run {
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
