package de.feliziabernutz.android.moodtracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood")
data class MoodEntity(@PrimaryKey(autoGenerate = true) var id: Int? = null,
                      @ColumnInfo(name = "mood") var mood: Int,
                      @ColumnInfo(name = "date") var date: String = today()
)
