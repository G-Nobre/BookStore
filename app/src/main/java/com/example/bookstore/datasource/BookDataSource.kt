package com.example.bookstore.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import com.example.bookstore.model.dtos.BookDto
import com.example.bookstore.model.network.WebClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BookDataSource(private val viewModelScope: CoroutineScope,private val webClient: WebClient) : PageKeyedDataSource<Int,BookDto>() {
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BookDto>) {
        getBooks(params.requestedLoadSize,params.key){ callback.onResult(it,params.key + 20) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BookDto>) {
        getBooks(params.requestedLoadSize,params.key){ callback.onResult(it,params.key - 20) }

    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BookDto>) {
        getBooks(params.requestedLoadSize,0){ callback.onResult(it,null, 20) }

    }

    private fun getBooks(limit: Int, skip: Int, onResult: (List<BookDto>) -> Unit) {
        Log.v("CRAZY_MESSAGE","Limit: $limit, Skip: $skip")
        viewModelScope.launch {
            onResult(webClient.loadBooks(limit = limit, startIndex = skip).body()?.items?: listOf())
        }
    }
}