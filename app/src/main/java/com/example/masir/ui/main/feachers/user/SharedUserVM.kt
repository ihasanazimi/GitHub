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

class SharedUserVM(private val repository: UsersRepository) : BaseViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLiveData.postValue(arrayListOf(throwable.message.toString()))
        showProgress.value = false
        Log.e("SharedUserVM", throwable.message.toString())
    }


    private val _foundUsers = MutableLiveData<SearchResultWidget>()
    val foundUsers = _foundUsers.toEvent()

    val singleUser = MutableLiveData<SingleUserObj>()

    private val _targetUserFollowers = MutableLiveData<List<User>>()
    val targetUserFollowers = _targetUserFollowers.toEvent()

    private val _targetUserFollowing = MutableLiveData<List<User>>()
    val targetUserFollowing = _targetUserFollowing.toEvent()

    private val _targetUserRepo = MutableLiveData<List<GitHubRepositoryObj>>()
    val targetUserRepo = _targetUserRepo.toEvent()

    val favoritesList = MutableLiveData<List<SingleUserObj>>()


    fun searchUser(userName : String){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler) {
            val result = repository.searchUser(userName)
            _foundUsers.value = result
            showProgress.value = false
        }
    }

    suspend fun getUserDetails(userName : String){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler)  {
            val followersResult = async { repository.getAllFollowersList(userName) }
            val followingResult = async { repository.getAllFollowingsList(userName) }
            val repositoryResult = async { repository.getAllRepoList(userName) }
            val userResult = async { repository.getSingleUser(userName) }

            val followers = followersResult.await()
            val following = followingResult.await()
            val repository = repositoryResult.await()
            val user = userResult.await()

            _targetUserFollowers.value = followers
            _targetUserFollowing.value = following
            _targetUserRepo.value = repository
            singleUser.value = user

            showProgress.value = false
        }
    }

    fun getAllFollowerOfUser(userName : String){
        showProgress.value = false
        viewModelScope.launch(coroutineExceptionHandler)  {
            val followersList = repository.getAllFollowersList(userName)
            _targetUserFollowers.value = followersList
            showProgress.value = false
        }
    }

    fun getAllFollowingOfUser(userName : String){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler)  {
            val followingList = repository.getAllFollowingsList(userName)
            _targetUserFollowing.value = followingList
            showProgress.value = false
        }
    }

    fun getAllRepoOfUser(userName : String){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler)  {
            val repoList = repository.getAllRepoList(userName)
            _targetUserRepo.value = repoList
            showProgress.value = false
        }
    }

    fun getAllFavoritesList(page : Int){
        showProgress.value = true
        viewModelScope.launch(coroutineExceptionHandler)  {
            val fList = repository.getFavoritesList(page)
            val temps = this@SharedUserVM.favoritesList.value?.toMutableList()
            temps?.addAll(fList)
            favoritesList.value = temps!!
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



//    fun updatePostOnLiveData(updatedPost: Post){
//        showProgress.value = true
//        val temps = _users.value?.toMutableList()
//        val targetPost = temps?.find { it.id == updatedPost.id }
//        val index = temps?.indexOf(targetPost)
//        if (index != null) temps.set(index,updatedPost)
//        _users.value = temps as ArrayList<Post>?
//        showProgress.value = false
//    }

}