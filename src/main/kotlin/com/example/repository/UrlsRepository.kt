import com.example.model.UrlEntity
import com.example.model.Urls
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.InvalidParameterException

private const val SHORT_URL = "https://short.en/"

@Throws(IllegalStateException::class)
fun saveAndGetShort(originalUrl: String, keyword: String): String {
    return transaction {
        val urls = findAllUrlsByKeywordAndOriginalUrl(keyword, originalUrl)
        if (urls.empty())
            UrlEntity.new {
                this.originalUrl = originalUrl
                this.keyword = keyword
            } else urls.first()
    }.let {
        SHORT_URL + it.keyword
    }
}

@Throws(InvalidParameterException::class)
fun findOriginalUrlByShort(shortUrl: String): String {
    return transaction {
        findAllUrlsByKeyword(shortUrl.substringAfterLast('/'))
    }.originalUrl
}

@Throws(InvalidParameterException::class)
fun findAllUrlsByKeyword(keyword: String): UrlEntity {
    return UrlEntity.find {
        Urls.keyword eq keyword
    }.let {
        if (it.empty())
            throw InvalidParameterException("No urls found by the keyword $keyword")
        else it.first()
    }
}

private fun findAllUrlsByKeywordAndOriginalUrl(keyword: String, originalUrl: String): SizedIterable<UrlEntity> {
    return UrlEntity.find {
        (Urls.keyword eq keyword) or (Urls.originalUrl eq originalUrl)
    }
}