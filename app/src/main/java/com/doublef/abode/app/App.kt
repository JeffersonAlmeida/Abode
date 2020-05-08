package com.doublef.abode.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.doublef.abode.data.Repository
import com.doublef.abode.data.Storage
import com.doublef.abode.features.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

open class App : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    private val appModule = module {
        single { MainViewModel(get()) }
        single { Repository(get()) }
        single { Storage(get()) }
        single { getSharedPrefs(androidApplication()) }
    }

    private fun getSharedPrefs(androidApplication: Application): SharedPreferences{
        return  androidApplication.getSharedPreferences("default",  Context.MODE_PRIVATE)
    }

}