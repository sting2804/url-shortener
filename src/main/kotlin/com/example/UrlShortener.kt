package com.example

import com.example.db.initDatasource
import com.example.utils.extractKeyword
import com.example.utils.extractShortUrl
import com.example.utils.extractUrl
import com.example.utils.parseParams
import findOriginalUrlByShort
import saveAndGetShort

fun main(args: Array<String>) {
    initDatasource()
    val params = parseParams(args)
    val originalUrl = extractUrl(params)
    val keyword = extractKeyword(params)
    val shortUrl = extractShortUrl(params)

    println("Original URL param: $originalUrl")
    println("Keyword param: $keyword")
    println("Short url param: $shortUrl")
    println()

    if (shortUrl != null)
        findOriginalUrlByShort(shortUrl)
    else saveAndGetShort(originalUrl, keyword)
}


