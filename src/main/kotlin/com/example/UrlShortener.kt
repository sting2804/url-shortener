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
    startUrlShorting(args)
}

@Throws(InvalidParameterException::class, IllegalStateException::class)
fun startUrlShorting(args: Array<String>): String {
    initDatasource()
    val params = parseParams(args)
    val shortUrl = extractShortUrl(params)

    return if (shortUrl != null) {
        println("Short url param: $shortUrl")
        println()
        "Original URL: ${findOriginalUrlByShort(shortUrl)}"

    } else {
        val originalUrl = extractUrl(params)
        val keyword = extractKeyword(params)
        println("Url to shorting: $originalUrl")
        println("Keyword param: $keyword")
        println()

        "Short URL: ${saveAndGetShort(originalUrl, keyword)}"
    }
}


