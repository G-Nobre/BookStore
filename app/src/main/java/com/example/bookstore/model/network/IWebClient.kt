package com.example.bookstore.model.network

import com.example.bookstore.model.dtos.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IWebClient {
    @GET("/books/v1/volumes")
    suspend fun loadBooks(
        @Query("q") q: String = "android",
        @Query("maxResults") limit: Int = 20,
        @Query("startIndex") startIndex: Int = 0
    ): Response<ResponseDto>
}