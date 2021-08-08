package com.example.himanshutestheptagon.data.api

import com.example.himanshutestheptagon.data.model.ItemList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getListdata(
        @Url url: String,
    ): Response<ItemList>

}