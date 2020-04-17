import com.example.model.UrlEntity
import com.example.model.Urls
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.transaction

private const val SHORT_URL = "https://short.en/"

fun saveAndGetShort(originalUrl: String, keyword: String) {
    transaction {
        val urls = findAllUrlsByKeywordAndOriginalUrl(keyword, originalUrl)
        val url = if (urls.empty())
            UrlEntity.new {
                this.originalUrl = originalUrl
                this.keyword = keyword
            } else urls.first()

        println("Short URl: ${SHORT_URL + url.keyword}")
    }
}

fun findOriginalUrlByShort(shortUrl: String) {
    transaction {
        val urls = findAllUrlsByKeyword(shortUrl.substringAfterLast('/'))

        println("Original URl: ${urls.firstOrNull()?.originalUrl}")
    }
}

private fun findAllUrlsByKeyword(keyword: String): SizedIterable<UrlEntity> {
    return UrlEntity.find {
        Urls.keyword eq keyword
    }
}

private fun findAllUrlsByKeywordAndOriginalUrl(keyword: String, originalUrl: String): SizedIterable<UrlEntity> {
    return UrlEntity.find {
        (Urls.keyword eq keyword) or (Urls.originalUrl eq originalUrl)
    }
}