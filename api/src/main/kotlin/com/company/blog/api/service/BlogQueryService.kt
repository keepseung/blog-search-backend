package com.company.blog.api.service

import com.company.blog.domain.dto.SearchPopularKeywordDto
import com.company.blog.domain.respository.SearchKeywordRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BlogQueryService(
    val searchKeywordRepository: SearchKeywordRepository,
) {
    fun findPopularKeyword(): List<SearchPopularKeywordDto> =
        searchKeywordRepository.findTop10ByOrderByCountDesc().map { SearchPopularKeywordDto.of(it) }
}
