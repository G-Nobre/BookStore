package com.example.bookstore.datasource

import androidx.paging.DataSource
import com.example.bookstore.model.dtos.BookDto
import com.example.bookstore.model.network.WebClient
import kotlinx.coroutines.CoroutineScope

class BookDataSourceFactory(private val viewModelScope:CoroutineScope,private val webClient: WebClient): DataSource.Factory<Int, BookDto>() {
    override fun create(): DataSource<Int, BookDto> = BookDataSource(viewModelScope,webClient)
}