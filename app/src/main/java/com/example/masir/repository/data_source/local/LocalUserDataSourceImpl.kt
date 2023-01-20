package com.example.masir.repository.data_source.local

import com.example.masir.db.RoomDB
import com.example.masir.model.SingleUserObj

class LocalUserDataSourceImpl(private val db : RoomDB) : LocalUserDataSource{

    override fun getFavoritesList(page: Int): ArrayList<SingleUserObj> {
        return db.userDao().getFavoritesUserPaging(page) as ArrayList<SingleUserObj>
    }

    override fun getSingleUser(login: String): SingleUserObj {
        return db.userDao().getSingleUser(login)
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