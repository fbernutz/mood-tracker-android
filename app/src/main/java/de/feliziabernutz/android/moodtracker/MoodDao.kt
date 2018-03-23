package de.feliziabernutz.android.moodtracker

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

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
