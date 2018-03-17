package com.example.feliziabernutz.moodtracking

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database

@Database(entities = arrayOf(MoodEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
}
