package io.msikora.starter.sample.ui

import io.msikora.starter.sample.network.HackerNewsAlgolia
import io.msikora.starter.sample.network.Hit
import io.msikora.starter.sample.network.SearchResult
import io.reactivex.Single
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import android.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.*
import org.junit.Assert.*
import org.junit.rules.TestRule
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit

val HIT = Hit("A", "url", DateTime(DateTimeZone.UTC).withDate(2018, 12, 31).withTime(12, 0, 0, 0))
val HIT_FOR_A = Hit("A", "url", DateTime(DateTimeZone.UTC).withDate(2018, 12, 31).withTime(12, 0, 0, 0))

@RunWith(MockitoJUnitRunner::class)
class HackerNewsViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var hackerNewsAlgolia: HackerNewsAlgolia
    private lateinit var observedStories: MutableList<List<Hit>>
    private lateinit var testScheduler: TestScheduler
    private lateinit var sut: HackerNewsViewModel

    @Before
    fun setup() {
        testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        assertEquals(Schedulers.computation(), testScheduler)
        `when`(hackerNewsAlgolia.search("", "story")).thenReturn(Single.just(SearchResult(listOf(HIT))))
        `when`(hackerNewsAlgolia.search("A", "story")).thenReturn(Single.just(SearchResult(listOf(HIT_FOR_A))))

        observedStories = ArrayList()
        sut = HackerNewsViewModel(hackerNewsAlgolia)
        sut.searchResult.observeForever {
            observedStories.add(it!!)
        }
    }

    @Test
    fun searchInitial() {
        testScheduler.advanceTimeBy(199, TimeUnit.MILLISECONDS)
        assertTrue(observedStories[0].isEmpty())
        assertEquals(1, observedStories.size)
        verifyZeroInteractions(hackerNewsAlgolia)
    }


    @Test
    fun searchInitialAfterTimeout() {
        testScheduler.advanceTimeBy(200, TimeUnit.MILLISECONDS)
        assertEquals(2, observedStories.size)
        assertEquals(HIT, observedStories[1][0])
        verify(hackerNewsAlgolia).search("", "story")
        verifyNoMoreInteractions(hackerNewsAlgolia)
    }

    @Test
    fun search() {
        sut.query.value = "A"
        testScheduler.advanceTimeBy(200, TimeUnit.SECONDS)
        assertEquals(2, observedStories.size)
        assertEquals(HIT, observedStories[1][0])
        verify(hackerNewsAlgolia).search("A", "story")
        verifyNoMoreInteractions(hackerNewsAlgolia)
    }

    @After
    fun cleanup() {
        RxJavaPlugins.reset()
    }
}