package com.example.himanshutestheptagon.data.repo

import com.example.himanshutestheptagon.data.api.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getListData() =
        withContext(Dispatchers.IO) {
            val netResObject =
                apiHelper.getListData()
            if (netResObject.code() == 400){
            }
            return@withContext netResObject.body()
        }

}