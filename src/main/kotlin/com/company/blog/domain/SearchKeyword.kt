package com.company.blog.domain

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Version

@Entity
@DynamicUpdate
class SearchKeyword(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val keyword: String,
    var count: Int,
    @Version
    var version: Long = 0,
) : BaseEntity() {
    fun plusCount() {
        this.count += 1
    }
}
