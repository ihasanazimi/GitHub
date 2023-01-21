package com.example.masir.repository

import com.example.masir.model.GitHubRepositoryObj
import com.example.masir.model.SearchResultWidget
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User
import com.example.masir.repository.data_source.local.LocalUserDataSourceImpl
import com.example.masir.repository.data_source.remote.RemoteUserDataSourceImpl
import kotlin.math.log

class UserRepositoryImpl(private val remoteRepo : RemoteUserDataSourceImpl, private val localRepo : LocalUserDataSourceImpl) : UsersRepository {

    override suspend fun getAllFollowersList(userName : String , page: Int): List<User> {
        return remoteRepo.getAllFollowersList(userName,page)
    }

    override suspend fun getAllFollowingsList(userName : String , page: Int): List<User> {
        return remoteRepo.getAllFollowingsList(userName,page)
    }


    override fun getFavoritesList(page : Int): List<SingleUserObj> {
        return localRepo.getFavoritesList(page)
    }

    override suspend fun getSingleUser(login: String): SingleUserObj {
        return remoteRepo.getSingleUser(login)
    }

    override suspend fun getSingleUserExist(login: String): SingleUserObj {
        return localRepo.getSingleUser(login)
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