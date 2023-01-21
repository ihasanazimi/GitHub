package com.example.masir.ui.main.feachers.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.masir.model.GitHubRepositoryObj
import com.example.masir.model.SearchResultWidget
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User
import com.example.masir.repository.UsersRepository
import com.example.masir.utility.BaseViewModel
import com.example.masir.utility.extentions.toEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UsersVM(private val repository: UsersRepository) : BaseViewModel() {

    var pageNumber = 1

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLiveData.postValue(arrayListOf(throwable.message.toString()))
        showProgress.value = false
        Log.e("UserVM", throwable.message.toString())
    }


    val foundUsers = MutableLiveData<SearchResultWidget>()

    val singleUser = MutableLiveData<SingleUserObj>()

    private val _targetUserFollowers = MutableLiveData<List<User>>()
    val targetUserFollowers = _targetUserFollowers.toEvent()

    private val _targetUserFollowing = MutableLiveData<List<User>>()
    val targetUserFollowing = _targetUserFollowing.toEvent()

    val favoritesList = MutableLiveData<List<SingleUserObj>>()


    fun searchUser(userName : String){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler) {
            val result = repository.searchUser(userName)
            foundUsers.value = result
            showProgress.value = false
        }
    }

    suspend fun getUserDetails(userName : String){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler)  {

            val followersResult = async { repository.getAllFollowersList(userName,1) }
            val followingResult = async { repository.getAllFollowingsList(userName,1) }
            val userResult = async { repository.getSingleUser(userName) }

            val followers = followersResult.await()
            val following = followingResult.await()
            val user = userResult.await()

            _targetUserFollowers.value = followers
            _targetUserFollowing.value = following
            singleUser.value = user

            showProgress.value = false
        }
    }

    fun isSaved(login: String) : Boolean{
        var isSaved = false
        viewModelScope.launch(coroutineExceptionHandler){
            val obj = repository.getSingleUserExist(login)
            isSaved = obj != null
        }
        return isSaved
    }

    fun getAllFollowerOfUser(userName : String){
        showProgress.value = false
        viewModelScope.launch(coroutineExceptionHandler)  {
            val followersList = repository.getAllFollowersList(userName,pageNumber)
            val temps = _targetUserFollowers.value?.toMutableList()
            temps?.addAll(followersList)
            _targetUserFollowers.value = temps!!
            showProgress.value = false
        }
    }

    fun getAllFollowingOfUser(userName : String){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler)  {
            val followingList = repository.getAllFollowingsList(userName,pageNumber)
            _targetUserFollowing.value = followingList
            showProgress.value = false
        }
    }

    fun getAllFavoritesList(page : Int){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler)  {
            val fList = repository.getFavoritesList(page)
            favoritesList.value = fList
            showProgress.value = false
        }
    }

    fun updateUser(user: SingleUserObj){
        showProgress.value = true
        repository.updateFavorite(user)
        showProgress.value = false
    }

    fun addFavorite(user: SingleUserObj){
        repository.addFavorite(user)
    }

    fun removeFavorite(user: SingleUserObj){
        repository.deleteFavorite(user)
    }



    fun updateFavoriteOnLiveData(user: SingleUserObj){
        val temps = favoritesList.value?.toMutableList()
        val targetUser = temps?.find { it.login == user.login }
        val index = temps?.indexOf(targetUser)
        if (index != null) temps.set(index,user)
        favoritesList.value = temps as ArrayList<SingleUserObj>?
    }

    fun removeFavoriteOnLiveData(user: SingleUserObj){
        val temps = favoritesList.value?.toMutableList()
        val targetUser = temps?.find { it.login == user.login }
        val index = temps?.indexOf(targetUser)
        if (index != null) temps.removeAt(index)
        favoritesList.value = temps as ArrayList<SingleUserObj>?
    }

    fun addFavoriteOnLiveData(user: SingleUserObj){
        val temps = favoritesList.value?.toMutableList()
        temps?.add(user)
        favoritesList.value = temps as ArrayList<SingleUserObj>?
    }

    fun stopLoading(){
        showProgress.value = false
    }

    override fun onCleared() {
        super.onCleared()
        stopLoading()
    }

}