#### **to build:**

`./gradlew clean build`

#### **to run**:

1. `java -jar build/libs/url_shortener.jar "Original URL: http://ololo.com/tro/1" "Keyword: some_keyword"` --normal variant
1. `java -jar build/libs/url_shortener.jar "Original URL: http://ololo.com/tro"` -- test keyword generator
1. `java -jar build/libs/url_shortener.jar https://short.en/some_key1` -- find original url by short

1. `java -jar build/libs/url_shortener.jar "Original URL: http://ololo.com/tro/1" "Keyword: 12345678901234567890_21_symbols_test"`
1. `java -jar build/libs/url_shortener.jar "Original URL: http:_//ololo.com/tro/1"` --bad original url test
1. `java -jar build/libs/url_shortener.jar http:_//ololo.com/tro/1` --bad short url test, original url required then


Short Url parameter in priority. Next If it doesn't exist, the program will try to find Original Url parameter.  