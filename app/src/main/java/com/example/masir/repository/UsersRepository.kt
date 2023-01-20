package com.example.masir.repository

import com.example.masir.model.GitHubRepositoryObj
import com.example.masir.model.SearchResultWidget
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User

interface UsersRepository {

    suspend fun getAllFollowersList(userName : String): List<User>
    suspend fun getAllFollowingsList(userName : String): List<User>
    suspend fun getAllRepoList(userName : String): List<GitHubRepositoryObj>
    fun getFavoritesList(page : Int) : List<SingleUserObj>
    suspend fun getSingleUser(userName: String) : SingleUserObj
    suspend fun searchUser(userName : String) : SearchResultWidget
    fun addFavorite(user : SingleUserObj)
    fun updateFavorite(user : SingleUserObj)
    fun deleteFavorite(user : SingleUserObj)
    fun clearList()

}