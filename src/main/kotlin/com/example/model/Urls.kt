package com.example.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Urls : IntIdTable() {
    var originalUrl = varchar("originalUrl", 255).uniqueIndex()
    var keyword = varchar("keyword", 20).uniqueIndex()
}

class UrlEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UrlEntity>(Urls)

    var originalUrl by Urls.originalUrl
    var keyword by Urls.keyword
}