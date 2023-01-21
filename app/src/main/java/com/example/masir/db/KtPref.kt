package com.example.masir.db

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.masir.ApplicationLoader
import com.example.masir.utility.extentions.dataStore
import kotlinx.coroutines.flow.first

object KtPref {

    const val DARK_MODE = "DARK_MODE"

    suspend fun isDarkMode(key : String ) : Boolean?{
        val dataStoreKey = booleanPreferencesKey(key)
        val preference = ApplicationLoader.context!!.dataStore.data.first()
        return preference[dataStoreKey]
    }


    // // write into data Store
    suspend fun saveTheme(key : String, value : Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        ApplicationLoader.context!!.dataStore.edit { dark ->
            dark[dataStoreKey] = value/* this is value */
        }
    }
}