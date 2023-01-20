package com.example.masir.repository

import com.example.masir.model.GitHubRepositoryObj
import com.example.masir.model.SearchResultWidget
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User
import com.example.masir.repository.data_source.local.LocalUserDataSourceImpl
import com.example.masir.repository.data_source.remote.RemoteUserDataSourceImpl

class UserRepositoryImpl(private val remoteRepo : RemoteUserDataSourceImpl, private val localRepo : LocalUserDataSourceImpl) : UsersRepository {

    override suspend fun getAllFollowersList(userName : String): List<User> {
        return remoteRepo.getAllFollowersList(userName)
    }

    override suspend fun getAllFollowingsList(userName : String): List<User> {
        return remoteRepo.getAllFollowingsList(userName)
    }

    override suspend fun getAllRepoList(userName: String): List<GitHubRepositoryObj> {
        return remoteRepo.getAllRepoList(userName)
    }

    override fun getFavoritesList(page : Int): List<SingleUserObj> {
        return localRepo.getFavoritesList(page)
    }

    override suspend fun getSingleUser(userName: String): SingleUserObj {
        return remoteRepo.getSingleUser(userName)
    }

    override suspend fun searchUser(userName: String): SearchResultWidget {
        return remoteRepo.searchUser(userName)
    }

    override fun addFavorite(user: SingleUserObj) {
        localRepo.addFavorite(user)
    }

    override fun updateFavorite(user: SingleUserObj) {
        localRepo.updateFavorite(user)
    }

    override fun deleteFavorite(user: SingleUserObj) {
        localRepo.deleteFavorite(user)
    }

    override fun clearList() {
        localRepo.clearList()
    }
}