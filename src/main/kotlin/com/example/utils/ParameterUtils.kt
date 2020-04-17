package com.example.utils

import java.lang.Exception
import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.security.InvalidParameterException


private const val RANDOM_KEYWORD_LENGTH = 5
private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
private const val splitPattern = ":\\s"

fun parseParams(args: Array<String>): List<Map<String, String>> {
    return args.mapNotNull { str ->
        str.split(Regex(splitPattern)).let {
            if (it.size > 1)
                mapOf("key" to it.first(), "value" to it.last())
            else tryToParseUrl(str)
        }
    }
}

private fun tryToParseUrl(str: String): Map<String, String>? {
    return if (isUrlValid(str))
        mapOf("key" to "Short URL", "value" to str)
    else null
}

private fun isUrlValid(url: String?): Boolean {
    return try {
        parseUrl(url)
        true
    } catch (e: Exception) {
        println("Bad url $url; ${e.message}")
        false
    }
}

@Throws(URISyntaxException::class, MalformedURLException::class)
private fun parseUrl(url: String?): URI? {
    val obj = URL(url)
    return obj.toURI()
}

@Throws(InvalidParameterException::class)
fun extractUrl(argsMap: List<Map<String, String>>): String {
    return argsMap.firstOrNull { it["key"] == "Original URL" }?.get("value")?.let {
        try {
            parseUrl(it).toString()
        } catch (e: Exception) {
            throw InvalidParameterException("Original URL has bad format.")
        }
    }
        ?: throw InvalidParameterException("Required parameter 'Original URL' not specified")
}

fun extractKeyword(argsMap: List<Map<String, String>>): String {
    return argsMap.firstOrNull { it["key"] == "Keyword" }?.get("value")
        ?: generateKeyword()
}

private fun generateKeyword(): String {
    return (1..RANDOM_KEYWORD_LENGTH)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("");
}

fun extractShortUrl(argsMap: List<Map<String, String>>): String? {
    return argsMap.firstOrNull { it["key"] == "Short URL" }?.get("value")
}
