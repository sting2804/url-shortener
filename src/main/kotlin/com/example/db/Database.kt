package com.example.db

import com.example.model.Urls
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DbSettings {
    val db by lazy {
        Database.connect("jdbc:h2:~/file/db", driver = "org.h2.Driver")
    }
}

fun initDatasource() {
    DbSettings.db
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Urls)
    }
}