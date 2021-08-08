package com.example.himanshutestheptagon.data.api

import com.example.himanshutestheptagon.data.model.ItemList
import retrofit2.Response

interface ApiHelper {
    suspend fun getListData(
    ): Response<ItemList>
}