package com.example.himanshutestheptagon.ui.viewmodel

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.himanshutestheptagon.data.model.ItemList
import com.example.himanshutestheptagon.data.repo.MainRepository
import com.example.himanshutestheptagon.util.Resource
import kotlinx.coroutines.launch
import life.alhilal.utils.NetworkHelper

class MainViewModel @ViewModelInject constructor(
    private val networkHelper: NetworkHelper,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val response = MutableLiveData<Resource<ItemList>>()
    fun getResponse(): LiveData<Resource<ItemList>> {
        return response
    }

    @SuppressLint("LogNotTimber")
    fun getList() {
        viewModelScope.launch {
            response.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    val res = mainRepository.getListData()
                    response.postValue(Resource.success(res))
                } catch (e: Exception) {
                    response.postValue(Resource.error(e.toString(), null))
                }
            } else {
                response.postValue(Resource.noInternet("", null))
            }
        }
    }

}