package de.feliziabernutz.android.moodtracker

import android.app.Application
import android.arch.persistence.room.Room
import kotlinx.coroutines.experimental.launch

class MoodApp : Application() {
    override fun onCreate() {
        super.onCreate()

        launch {
            db = Room.databaseBuilder(applicationContext,
                    AppDatabase::class.java, "moods.db").build()
        }
    }

    companion object {
        var db: AppDatabase? = null
    }
}
