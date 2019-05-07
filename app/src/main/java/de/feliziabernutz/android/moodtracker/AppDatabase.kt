package de.feliziabernutz.android.moodtracker

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = [MoodEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
}
