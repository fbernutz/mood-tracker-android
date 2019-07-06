package de.feliziabernutz.android.moodtracker

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MoodEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
}
