package de.feliziabernutz.android.moodtracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoodDao {
    @get:Query("SELECT * FROM mood")
    val all: List<MoodEntity>

    @Insert
    fun insert(mood: MoodEntity)

    @Query("SELECT * FROM mood WHERE date = :date")
    fun byDate(date: String): List<MoodEntity>

    @Update
    fun update(mood: MoodEntity)
}
