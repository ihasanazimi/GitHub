package com.example.masir

import android.app.Application
import android.content.Context
import android.os.Handler
import com.example.masir.db.RoomDB
import com.example.masir.repository.UserRepositoryImpl
import com.example.masir.repository.data_source.local.LocalUserDataSourceImpl
import com.example.masir.repository.data_source.remote.RemoteUserDataSourceImpl
import com.example.masir.services.http.ApiService
import com.example.masir.ui.main.feachers.user.UsersVM
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.*

class ApplicationLoader : Application() {

    companion object{
        var context :Context ?= null
        var timer :Timer ?= null
        lateinit var applicationHandler : Handler
    }

    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        applicationHandler = Handler(this.mainLooper)

        /** Init Timber
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())*/


        /** 1- Koin -> modules.. */
        val myModules = module {
            single { ApiService().api }
            single { RoomDB.getDataBase(this@ApplicationLoader) }
            factory { UsersVM(UserRepositoryImpl(RemoteUserDataSourceImpl(get()), LocalUserDataSourceImpl(get()))) }
        }

        /** 2- Start Coin By Modules... */
        startKoin {
            androidContext(this@ApplicationLoader)
            modules(myModules)
        }
        }
    }