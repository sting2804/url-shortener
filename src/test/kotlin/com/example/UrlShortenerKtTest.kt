package com.example

import com.example.db.initDatasource
import com.example.utils.generateKeyword
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import saveAndGetShort

internal class UrlShortenerKtTest {

    @BeforeEach
    internal fun setUp() {
        initDatasource()
    }

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
        saveAndGetShort("http://test.com/1/2", "12345")

        //expect
        Assertions.assertEquals("Original URL: http://test.com/1/2", startUrlShorting(args))
    }

    @Test
    fun startUrlShorting_valid_original_url_and_too_long_keyword_21_char() {
        //given
        val args = arrayOf("Original URL: http://test.com/1/3", "Keyword: 123456789012345678901")

        //expect
        val res = Assertions.assertThrows(Exception::class.java) {
            startUrlShorting(args)
        }
        Assertions.assertTrue(res.message!!.contains("can't be stored to database column because exceeds length "))
    }

    @Test
    fun startUrlShorting_invalid_original_url() {
        //given
        val args = arrayOf("Original URL: //test.com/1/4", "Keyword: 1234567890123456789")

        //expect
        val res = Assertions.assertThrows(IllegalArgumentException::class.java) {
            startUrlShorting(args)
        }
        Assertions.assertEquals("Original URL has bad format", res.message)
    }

    @Test
    fun startUrlShorting_invalid_short_url_format_required_original_url() {
        //given
        val args = arrayOf("//test.com/1/4")

        //expect
        val res = Assertions.assertThrows(IllegalArgumentException::class.java) {
            startUrlShorting(args)
        }
        Assertions.assertEquals("Required parameter 'Original URL' not specified", res.message)
    }
}