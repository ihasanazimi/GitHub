package com.example.masir.ui.main.feachers.user.details.follow_lists

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.masir.model.User
import com.example.masir.repository.UsersRepository
import com.example.masir.utility.BaseViewModel
import com.example.masir.utility.extentions.toEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class FollowVM(val repository: UsersRepository) : BaseViewModel() {

    var pageNumber = 1

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLiveData.postValue(arrayListOf(throwable.message.toString()))
        showProgress.value = false
        Log.e("FollowVM", throwable.message.toString())
    }


    private val _followers = MutableLiveData<List<User>>()
    val followers = _followers.toEvent()

    private val _following = MutableLiveData<List<User>>()
    val following = _following.toEvent()


    fun getAllFollowerOfUser(userName : String){
        showProgress.value = false
        viewModelScope.launch(coroutineExceptionHandler)  {
            val followersList = repository.getAllFollowersList(userName,pageNumber)
            if (followersList.isNotEmpty()){
                val temps = _followers.value?.toMutableList()
                temps?.addAll(followersList)
                _followers.value = temps?: arrayListOf()
            }else pageNumber--
            showProgress.value = false
        }
    }

    fun getAllFollowingOfUser(userName : String){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler)  {
            val followingList = repository.getAllFollowingsList(userName,pageNumber)
            if (followingList.isNotEmpty()){
                val temps = _following.value?.toMutableList()
                temps?.addAll(followingList)
                _following.value = temps?: arrayListOf()
            }else pageNumber--
            showProgress.value = false
        }
    }

    fun putFollowersInLiveData(users : List<User>){
        _followers.value = users
    }

    fun putFollowingInLiveData(users : List<User>){
        _following.value = users
    }


}