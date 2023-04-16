package com.company.blog.domain.respository

import com.company.blog.domain.entity.SearchKeyword
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import javax.persistence.LockModeType

interface SearchKeywordRepository : JpaRepository<SearchKeyword, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findWithLockByKeyword(keyword: String): SearchKeyword?
    fun findByKeyword(keyword: String): SearchKeyword?
    fun findTop1ByKeyword(keyword: String): SearchKeyword?
    fun findTop10ByOrderByCountDesc(): List<SearchKeyword>
}
