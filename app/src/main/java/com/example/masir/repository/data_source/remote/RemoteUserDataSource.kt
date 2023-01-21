package com.example.masir.repository.data_source.remote

import com.example.masir.model.GitHubRepositoryObj
import com.example.masir.model.SearchResultWidget
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User

interface RemoteUserDataSource {

    suspend fun getAllFollowersList(userName : String, page: Int): List<User>
    suspend fun getAllFollowingsList(userName : String, page: Int): List<User>
    suspend fun searchUser(userName : String) : SearchResultWidget
    suspend fun getSingleUser(userName : String) : SingleUserObj

}