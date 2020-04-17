package com.example.utils

import java.net.MalformedURLException
import java.net.URISyntaxException
import java.net.URL
import java.security.InvalidParameterException


private const val RANDOM_KEYWORD_LENGTH = 5
private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun parseParams(args: Array<String>): List<Map<String, String>> {
    return args.mapNotNull { str ->
        str.split("::").let {
            if (it.size > 1)
                mapOf("key" to it.first(), "value" to it.last())
            else tryToParseUrl(str)
        }
    }
}

private fun tryToParseUrl(str: String): Map<String, String>? {
    return if (isUrlValid(str))
        mapOf("Short URL" to str)
    else null
}

private fun isUrlValid(url: String?): Boolean {
    return try {
        val obj = URL(url)
        obj.toURI()
        true
    } catch (e: MalformedURLException) {
        false
    } catch (e: URISyntaxException) {
        false
    }
}

fun extractUrl(argsMap: List<Map<String, String>>): String {
    return argsMap.firstOrNull { it["key"] == "Original URL" }?.get("value")
        ?: throw InvalidParameterException("Required parameter 'Original URL' not specified")
}

fun extractKeyword(argsMap: List<Map<String, String>>): String {
    return argsMap.firstOrNull { it["key"] == "Keyword" }?.get("value")
        ?: generateKeyword()
}

fun extractShortUrl(argsMap: List<Map<String, String>>): String? {
    return argsMap.firstOrNull { it["key"] == "Short URL" }?.get("value")
}

private fun generateKeyword(): String {
    return (1..RANDOM_KEYWORD_LENGTH)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("");
}