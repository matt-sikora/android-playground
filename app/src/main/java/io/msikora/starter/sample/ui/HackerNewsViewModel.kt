package io.msikora.starter.sample.ui

import android.arch.lifecycle.*
import io.msikora.starter.core.ui.common.NonNullMutableLiveData
import io.msikora.starter.core.ui.common.NonNullObserver
import io.msikora.starter.sample.network.HackerNewsAlgolia
import io.msikora.starter.sample.network.Hit
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class HackerNewsViewModel(
    private val hackerNewsAlgolia: HackerNewsAlgolia
) : ViewModel() {

    private val querySubject = BehaviorSubject.createDefault("")
    val query = NonNullMutableLiveData("")

    private val _searchResult = NonNullMutableLiveData<List<Hit>>(emptyList())
    val searchResult: LiveData<List<Hit>>
        get() = _searchResult

    private val updateQuerySubject = NonNullObserver<String> { querySubject.onNext(it) }
    private val searchDisposable: Disposable

    init {
        query.observeForever(updateQuerySubject)
        searchDisposable = querySubject
            .distinctUntilChanged()
            .debounce(200, TimeUnit.MILLISECONDS)
            .flatMap {
                hackerNewsAlgolia.search(it, "story").toObservable()
            }
            .subscribe({
                _searchResult.postValue(_searchResult.value.plus(it.hits))
            })
    }

    override fun onCleared() {
        super.onCleared()
        query.removeObserver(updateQuerySubject)
        searchDisposable.dispose()
    }

//    fun updateQuery(query: String) {
//        hackerNewsAlgolia.search(query, "story")
//            .subscribe { result -> _searchResult.postValue(_searchResult.value.plus(result.hits)) }
//    }

}
