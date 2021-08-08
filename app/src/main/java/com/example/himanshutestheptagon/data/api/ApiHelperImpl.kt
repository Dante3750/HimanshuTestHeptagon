package com.example.himanshutestheptagon.data.api

import com.example.himanshutestheptagon.data.model.ItemList
import com.example.himanshutestheptagon.util.AppConstant
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getListData(): Response<ItemList> {
        return apiService.getListdata(AppConstant.LIST_URL)
    }
}