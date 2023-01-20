package com.example.masir.repository.data_source.remote

import com.example.masir.model.GitHubRepositoryObj
import com.example.masir.model.SearchResultWidget
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User

interface RemoteUserDataSource {

    suspend fun getAllFollowersList(userName : String): List<User>
    suspend fun getAllFollowingsList(userName : String): List<User>
    suspend fun getAllRepoList(userName : String): List<GitHubRepositoryObj>
    suspend fun searchUser(userName : String) : SearchResultWidget
    suspend fun getSingleUser(userName : String) : SingleUserObj

}