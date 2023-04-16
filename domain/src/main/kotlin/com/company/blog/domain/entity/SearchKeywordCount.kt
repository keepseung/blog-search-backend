package com.company.blog.domain.entity

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@DynamicUpdate
class SearchKeywordCount(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val keywordId: Long,
) : BaseEntity()
