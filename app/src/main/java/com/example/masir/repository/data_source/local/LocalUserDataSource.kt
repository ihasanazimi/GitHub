package com.example.masir.repository.data_source.local

import com.example.masir.model.SingleUserObj
import com.example.masir.model.User

interface LocalUserDataSource {

    fun getFavoritesList(page : Int) : ArrayList<SingleUserObj>
    fun addFavorite(user : SingleUserObj)
    fun updateFavorite(user : SingleUserObj)
    fun deleteFavorite(user : SingleUserObj)
    fun clearList()

}