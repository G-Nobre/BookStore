package com.example.bookstore.model

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.bookstore.DbNotInitializedException
import com.example.bookstore.datasource.BookDataSource
import com.example.bookstore.datasource.BookDataSourceFactory
import com.example.bookstore.datasource.DbBookDataSource
import com.example.bookstore.datasource.DbBookDataSourceFactory
import com.example.bookstore.db.BookDb
import com.example.bookstore.db.entities.Favorites
import com.example.bookstore.model.dtos.BookDto
import com.example.bookstore.model.network.WebClient
import com.example.bookstore.model.network.processResponse

class BookViewModel(private val webClient: WebClient, private val bookDb: BookDb?) : ViewModel() {

    var booksList: MutableList<BookDto> = mutableListOf()

    private val config: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(30)
        .setPageSize(20)
        .setPrefetchDistance(1)
        .build()

    lateinit var liveData: LiveData<PagedList<BookDto>>
    lateinit var liveDataDb: LiveData<PagedList<BookDto>>

    var isFiltered = false

/*    private val liveData: MutableLiveData<List<BookDto>> by lazy {
        MutableLiveData<List<BookDto>>()
    }*/

    fun initViewModel(){
        liveData = LivePagedListBuilder(
            BookDataSourceFactory(viewModelScope,webClient),
            config
        ).build()
        initDbLiveData()
    }

    fun initDbLiveData() {
        liveDataDb = LivePagedListBuilder(
            DbBookDataSourceFactory(bookDb!!, this),
            config
        ).build()
    }

    fun observe(owner: LifecycleOwner, observer: (PagedList<BookDto>) -> Unit) {
        liveData.observe(owner, {
            booksList.clear()
            booksList.addAll(it.snapshot())
            observer(it)
        })
    }

    fun getFavoriteBook(bookId: String, handleResponse: (List<Favorites>) -> Unit) {
        if (bookDb == null) throw DbNotInitializedException("Db can't be null.")

        bookDb.favoritesDao().getFavoriteBookById(bookId).processResponse(handleResponse)
    }

    private fun getAllFavoriteBooks(handleResponse: (List<Favorites>) -> Unit) {
        if (bookDb == null) throw DbNotInitializedException("Db can't be null.")

        bookDb.favoritesDao().getAllFavoriteBooks().processResponse(handleResponse)
    }

    fun insertFavoriteBook(bookId: String, onComplete: () -> Unit) {
        if (bookDb == null) throw DbNotInitializedException("Db can't be null.")

        bookDb.favoritesDao().insertFavoriteBook(Favorites(book_id = bookId)).processResponse(onComplete)
    }

    fun deleteFavoriteBook(bookId: String, onComplete: () -> Unit) {
        if (bookDb == null) throw DbNotInitializedException("Db can't be null.")

        bookDb.favoritesDao().deleteFavoriteBook(bookId).processResponse(onComplete)
    }

    fun changeFilterStatus(makeToastFunction:(String)->Unit){

        if(isFiltered) {
//            removeFilterByFavoriteBooks()
            makeToastFunction("Fitering By Favorite Books")
        }
        else {
//            filterByFavoriteBooks()
            makeToastFunction("Removed Filter")
        }
    }

    /*private fun filterByFavoriteBooks() {
        getAllFavoriteBooks {
            val filteredBooks:List<BookDto> = booksList.filter { book -> it.find { favorite -> favorite.book_id == book.id } != null }
            //livedata : PagedList<BookDto>
            bookDb.postValue()

            isFiltered = true
        }
    }

    private fun removeFilterByFavoriteBooks(){
        liveData.postValue(booksList)
        isFiltered = false
    }*/

}
