package com.example.bookstore.datasource

import androidx.paging.DataSource
import com.example.bookstore.db.BookDb
import com.example.bookstore.model.BookViewModel
import com.example.bookstore.model.dtos.BookDto

class DbBookDataSourceFactory(private val bookDb: BookDb, private val viewModel: BookViewModel): DataSource.Factory<Int, BookDto>() {
    override fun create(): DataSource<Int, BookDto> = DbBookDataSource(bookDb,viewModel)
}