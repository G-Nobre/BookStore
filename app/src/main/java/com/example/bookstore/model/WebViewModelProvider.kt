package com.example.bookstore.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookstore.App
import com.example.bookstore.db.BookDb

class WebViewModelProvider(private val context: Context):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            BookViewModel::class.java -> BookViewModel(
                App.webClient,
                BookDb.getInstance(context)
            )
            else -> throw IllegalArgumentException("Class $modelClass not supported by this provider")
        } as T
    }
}