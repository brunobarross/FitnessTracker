package com.altamiro.fitnesstracker

import android.app.Application
import com.altamiro.fitnesstracker.model.AppDatabase
class App : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }

}