package com.example.masir.repository.data_source.remote

import com.example.masir.model.GitHubRepositoryObj
import com.example.masir.model.SearchResultWidget
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User
import com.example.masir.services.http.Api

class RemoteUserDataSourceImpl(private val api  : Api) : RemoteUserDataSource {

    override suspend fun getAllFollowersList(userName : String): List<User> {
        return api.getFollowersOfUser(userName)
    }

    override suspend fun getAllFollowingsList(userName : String): List<User> {
        return api.getFollowingOfUser(userName)
    }

    override suspend fun getAllRepoList(userName : String): List<GitHubRepositoryObj> {
        return api.getRepoOfUser(userName)
    }

    override suspend fun searchUser(userName: String): SearchResultWidget {
        return api.searchUser(userName)
    }

    override suspend fun getSingleUser(userName: String): SingleUserObj {
        return api.getSingleUser(userName)
    }
}