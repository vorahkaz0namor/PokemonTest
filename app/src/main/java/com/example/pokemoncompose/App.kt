package com.example.pokemoncompose

import android.app.Application
import com.example.pokemoncompose.di.AppComponent
import com.example.pokemoncompose.di.DaggerAppComponent

class App : Application() {

    companion object {
        private lateinit var appComponent: AppComponent
        fun getAppComponent(): AppComponent = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().app(this).build()
    }
}