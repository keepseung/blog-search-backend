package com.company.blog.respository

import com.company.blog.domain.SearchKeyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import javax.persistence.LockModeType

interface SearchKeywordRepository : JpaRepository<SearchKeyword, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    fun findWithLockByKeyword(keyword: String): SearchKeyword?
    fun findByKeyword(keyword: String): SearchKeyword?
    fun findTop10ByOrderByCountDesc(): List<SearchKeyword>
}
