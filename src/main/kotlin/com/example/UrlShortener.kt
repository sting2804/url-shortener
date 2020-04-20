package com.example

import com.example.db.initDatasource
import com.example.utils.extractKeyword
import com.example.utils.extractShortUrl
import com.example.utils.extractUrl
import com.example.utils.parseParams
import findOriginalUrlByShort
import saveAndGetShort
import java.security.InvalidParameterException

fun main(args: Array<String>) {
    initDatasource()
    val params = parseParams(args)
    val shortUrl = extractShortUrl(params)

    if (shortUrl != null) {
        println("Short url param: $shortUrl")
        println()

        try {
            val searchableUrl = findOriginalUrlByShort(shortUrl)
            println("Original URL: $searchableUrl")
        } catch (e: InvalidParameterException) {
            println(e.message)
        }
    } else {
        val originalUrl = extractUrl(params)
        val keyword = extractKeyword(params)
        println("Url to shorting: $originalUrl")
        println("Keyword param: $keyword")
        println()

        try {
            val saveAndGetShort = saveAndGetShort(originalUrl, keyword)
            println("Short URL: $saveAndGetShort")
        } catch (e: IllegalStateException) {
            println(e.message)
        }
    }
}


