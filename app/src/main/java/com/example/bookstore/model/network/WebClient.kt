package com.example.bookstore.model.network

import android.util.Log
import com.example.bookstore.model.dtos.ResponseDto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


class WebClient(private val client: IWebClient) {

    suspend fun loadBooks(limit: Int, startIndex: Int): Response<ResponseDto> {
        return withContext(Dispatchers.IO) {
            client.loadBooks(limit = limit, startIndex = startIndex)
        }
    }

}