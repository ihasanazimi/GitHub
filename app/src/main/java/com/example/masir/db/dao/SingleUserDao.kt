package com.example.masir.db.dao

import androidx.room.*
import com.example.masir.model.SingleUserObj

@Dao
interface SingleUserDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertUser(singleUser: SingleUserObj) : Long

    @Update
    fun updateUser(singleUser: SingleUserObj)

    @Query("SELECT * FROM SingleUserObj LIMIT 10 OFFSET :page * 10 ;")
    fun getFavoritesUserPaging(page : Int) : List<SingleUserObj>

    @Query("SELECT * FROM SingleUserObj")
    fun getAllFavoritesUser() : List<SingleUserObj>

    @Delete
    fun deleteUser(singleUser: SingleUserObj) : Int

    @Query("DELETE FROM SingleUserObj")
    fun clearTable()

}