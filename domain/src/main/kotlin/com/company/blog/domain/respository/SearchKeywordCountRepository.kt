package com.company.blog.domain.respository

import com.company.blog.domain.entity.SearchKeywordCount
import org.springframework.data.jpa.repository.JpaRepository

interface SearchKeywordCountRepository : JpaRepository<SearchKeywordCount, Long>
