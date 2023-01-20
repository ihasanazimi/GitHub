package com.example.masir.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.masir.db.converter.SingleUserConverters
import com.example.masir.db.dao.SingleUserDao
import com.example.masir.model.SingleUserObj

@Database(entities = [SingleUserObj::class], version =4 , exportSchema = false)
@TypeConverters( SingleUserConverters::class ,)
abstract class RoomDB : RoomDatabase() {

    // Dao`s
    abstract fun userDao(): SingleUserDao

    companion object {
        @Volatile //access just one there on main thread! thread safe..
        var database: RoomDB? = null
        // singleTon design pattern
        fun getDataBase(context: Context): RoomDB {
            val tempInstance = database
            if (database != null) return tempInstance as RoomDB
            //synchronized  -->  means -->  access just one there on main thread!
            synchronized(this) {
                val instance = Room.databaseBuilder(context, RoomDB::class.java, "database").fallbackToDestructiveMigration().allowMainThreadQueries().build()
                database = instance
                return instance
            }
        }
    }
}