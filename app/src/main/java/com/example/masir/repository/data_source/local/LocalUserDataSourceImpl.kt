package com.example.masir.repository.data_source.local

import com.example.masir.db.RoomDB
import com.example.masir.model.SingleUserObj
import com.example.masir.model.User

class LocalUserDataSourceImpl(private val db : RoomDB) : LocalUserDataSource{

    override fun getFavoritesList(page : Int): List<SingleUserObj> {
        return db.userDao().getFavoritesUserPaging(page)
    }

    override fun addFavorite(user: SingleUserObj) {
        db.userDao().insertUser(user)
    }

    override fun updateFavorite(user: SingleUserObj) {
        db.userDao().updateUser(user)
    }

    override fun deleteFavorite(user: SingleUserObj) {
        db.userDao().deleteUser(user)
    }

    override fun clearList() {
        db.userDao().clearTable()
    }
}