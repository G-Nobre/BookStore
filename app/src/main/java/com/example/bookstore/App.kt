package com.example.bookstore

import android.app.Application
import com.example.bookstore.model.network.IWebClient
import com.example.bookstore.model.network.WebClient
import com.example.bookstore.model.network.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    companion object {
        lateinit var webClient: WebClient
    }
    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(IWebClient::class.java)

        webClient = WebClient(service)
    }
}