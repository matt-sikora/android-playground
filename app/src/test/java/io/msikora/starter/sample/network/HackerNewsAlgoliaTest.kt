package io.msikora.starter.sample.network

import io.msikora.starter.sample.di.HackerNewsModule.hackerNewsAlgolia
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockWebServer
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import okhttp3.mockwebserver.MockResponse
import org.joda.time.DateTimeZone
import org.junit.After
import org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class HackerNewsAlgoliaTest {

    private lateinit var sut: HackerNewsAlgolia
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        server.enqueue(MockResponse().setBody(SEARCH_RESPONSE))
        sut = hackerNewsAlgolia(server.url("").toString(), Schedulers.from { it.run() })
    }

    @Test
    fun search() {
        val testObserver = sut.search("foo", "story").test()
        assertEquals("/api/v1/search?query=foo&tags=story", server.takeRequest().path)
        with(testObserver) {
            assertNoErrors()
            assertValueCount(1)
            assertValue {
                it.hits[0] == Hit(
                    title = "Amazon to Acquire Whole Foods for \$13.7B",
                    url = "https://www.bloomberg.com/news/articles/2017-06-16/amazon-to-buy-whole-foods?cmpid=socialflow-twitter-business&utm_content=business&utm_campaign=socialflow-organic&utm_source=twitter&utm_medium=social",
                    createdAt = DateTime(DateTimeZone.UTC).withDate(2017, 6, 16).withTime(13, 3, 9, 0)
                )
            }
            assertComplete()
        }
    }

    @After
    fun cleanup() {
        server.shutdown()
    }
}
