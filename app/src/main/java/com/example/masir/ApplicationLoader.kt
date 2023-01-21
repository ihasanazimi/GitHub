package com.example.masir

import android.app.Application
import android.content.Context
import android.os.Handler
import android.provider.Settings.Global
import com.example.masir.db.KtPref
import com.example.masir.db.RoomDB
import com.example.masir.repository.UserRepositoryImpl
import com.example.masir.repository.data_source.local.LocalUserDataSourceImpl
import com.example.masir.repository.data_source.remote.RemoteUserDataSourceImpl
import com.example.masir.services.http.ApiService
import com.example.masir.ui.main.feachers.user.UsersVM
import com.example.masir.ui.main.feachers.user.details.follow_lists.FollowVM
import ir.ha.practice.utility.util.ThemeUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    @DelicateCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext
        applicationHandler = Handler(this.mainLooper)

        /** Init Timber
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())*/

        GlobalScope.launch {
            if (KtPref.isDarkMode(KtPref.DARK_MODE)==null) KtPref.saveTheme(KtPref.DARK_MODE,false)
            val isDark = KtPref.isDarkMode(KtPref.DARK_MODE)
            ThemeUtils.changeTheme(isDark!!)
        }


        /** 1- Koin -> modules.. */
        val myModules = module {
            single { ApiService().api }
            single { RoomDB.getDataBase(this@ApplicationLoader) }
            factory { UsersVM(UserRepositoryImpl(RemoteUserDataSourceImpl(get()), LocalUserDataSourceImpl(get()))) }
            factory { FollowVM(UserRepositoryImpl(RemoteUserDataSourceImpl(get()), LocalUserDataSourceImpl(get()))) }
        }

        /** 2- Start Coin By Modules... */
        startKoin {
            androidContext(this@ApplicationLoader)
            modules(myModules)
        }
        }
    }