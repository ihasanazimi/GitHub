package com.example.masir.utility

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    open val errorLiveData = MutableLiveData<ArrayList<String>>()
    open var showProgress = MutableLiveData<Boolean>(false)

    // clear data after change state and was destroyed view
    open fun clearErrorLiveData(){
        val data = errorLiveData.value?.apply { clear() } ?: arrayListOf()
        errorLiveData.value = data
    }

    override fun onCleared() {
        super.onCleared()
        clearErrorLiveData()
    }
}



