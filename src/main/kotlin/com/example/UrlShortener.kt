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
    startUrlShorting(args)

    try {
        println(startUrlShorting(args))
    } catch (e: InvalidParameterException) {
        println(e.message)
    } catch (e: IllegalStateException) {
        println(e.message)
    }
}

fun startUrlShorting(args: Array<String>): String {
    initDatasource()
    val params = parseParams(args)
    val originalUrl = extractUrl(params)
    val keyword = extractKeyword(params)
    val shortUrl = extractShortUrl(params)

    println("Url to shorting: $originalUrl")
    println("Keyword param: $keyword")
    println("Short url param: $shortUrl")
    println()

    return if (shortUrl != null) {
        val searchableUrl = findOriginalUrlByShort(shortUrl)
        "Original URL: $searchableUrl"

    } else {
        val saveAndGetShort = saveAndGetShort(originalUrl, keyword)
        "Short URL: $saveAndGetShort"
    }
}


