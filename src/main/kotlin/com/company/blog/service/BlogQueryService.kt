package com.company.blog.service

import com.company.blog.respository.SearchKeywordRepository
import com.company.blog.service.dto.SearchPopularKeywordDto
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
