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
    val originalUrl = extractUrl(params)
    val keyword = extractKeyword(params)
    val shortUrl = extractShortUrl(params)

    println("Url to shorting: $originalUrl")
    println("Keyword param: $keyword")
    println("Short url param: $shortUrl")
    println()

    if (shortUrl != null) {
        try {
            val searchableUrl = findOriginalUrlByShort(shortUrl)
            println("Original URL: $searchableUrl")
        } catch (e: InvalidParameterException) {
            println(e.message)
        }
    } else {
        try {
            val saveAndGetShort = saveAndGetShort(originalUrl, keyword)
            println("Short URL: $saveAndGetShort")
        } catch (e: IllegalStateException){
            println(e.message)
        }
    }
}


