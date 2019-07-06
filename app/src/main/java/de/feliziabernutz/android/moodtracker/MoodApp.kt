package de.feliziabernutz.android.moodtracker

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoodApp : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch {
            db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "moods.db"
            ).build()
        }
    }

    companion object {
        var db: AppDatabase? = null
    }
}
