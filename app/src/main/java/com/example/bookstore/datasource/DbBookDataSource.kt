package com.example.bookstore.datasource

import androidx.paging.PageKeyedDataSource
import com.example.bookstore.DbNotInitializedException
import com.example.bookstore.db.BookDb
import com.example.bookstore.db.entities.Favorites
import com.example.bookstore.model.BookViewModel
import com.example.bookstore.model.dtos.BookDto
import com.example.bookstore.model.network.processResponse

class DbBookDataSource(private val bookDb: BookDb,private val viewModel: BookViewModel):PageKeyedDataSource<Int, BookDto>() {
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BookDto>) {
        getAllFavoriteBooks{ callback.onResult(it,null) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BookDto>) {
        getAllFavoriteBooks { callback.onResult(it,null) }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BookDto>) {
        getAllFavoriteBooks { callback.onResult(it,null, null) }
    }

    private fun getAllFavoriteBooks(onResult:(List<BookDto>)->Unit){
        bookDb.favoritesDao().getAllFavoriteBooks().processResponse {
            onResult(viewModel.booksList.filter { book -> it.find { favorite -> favorite.book_id == book.id } != null })
        } //==> List<Favorites> => MAP => List<BookDto>
    }
}