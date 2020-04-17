#### **to build:**

`./gradlew clean build`

#### **to run**:

1. `java -jar build/libs/url_shortener.jar "Original URL: http://ololo.com/tro/1" Keyword: some_keyword`
2. `java -jar build/libs/url_shortener.jar "Original URL: http://ololo.com/tro"`
3. `java -jar build/libs/url_shortener.jar https://short.en/some_key1`

Short Url parameter in priority. Next If it doesn't exist, the program will try to find Original Url parameter.  