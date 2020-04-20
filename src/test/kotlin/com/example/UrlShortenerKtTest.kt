package com.example

import com.example.utils.generateKeyword
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class UrlShortenerKtTest {

    @Test
    fun startUrlShorting_valid_original_url_and_keyword() {
        //given
        val args = arrayOf("Original URL: http://test.com/1/1", "Keyword: some_keyword")

        //expect
        Assertions.assertEquals("Short URL: https://short.en/some_keyword", startUrlShorting(args))
    }

    @Test
    fun startUrlShorting_valid_original_url_without_keyword() {
        //given
        val args = arrayOf("Original URL: http://test.com/1/2")
        mockkStatic("com.example.utils.ParameterUtilsKt")
        every { generateKeyword() } returns "12345"

        //expect
        Assertions.assertEquals("Short URL: https://short.en/12345", startUrlShorting(args))
    }

    @Test
    fun startUrlShorting_valid_short_url() {
        //given
        val args = arrayOf("https://short.en/12345")

        //expect
        Assertions.assertEquals("Original URL: http://test.com/1/2", startUrlShorting(args))
    }
}