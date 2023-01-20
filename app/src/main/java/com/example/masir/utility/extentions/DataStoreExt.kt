package com.example.masir.utility.extentions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// Singleton Data Store
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")