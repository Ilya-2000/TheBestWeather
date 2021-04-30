package com.impact.thebestweather.utils

import android.util.Log
import android.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class RxSearchView {
    private val TAG = "RxSearchView"
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    private fun observeSearchView(searchView: SearchView): Observable<String> {

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(newText: String?): Boolean {
                    publishSubject.onComplete()
                    searchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    query?.let { publishSubject.onNext(it) }
                    return false
                }

            })
        return publishSubject
    }
}