package com.example.musicplayer

import android.app.Application
import androidx.room.Room
import com.example.musicplayer.room.MusicPlayerDatabase

class MainApplication : Application() {
    companion object {
        lateinit var db: MusicPlayerDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            MusicPlayerDatabase::class.java,
            MusicPlayerDatabase.NAME
        ).build()
    }



}