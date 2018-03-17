package com.example.feliziabernutz.moodtracking

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "mood")
data class MoodEntity(@PrimaryKey(autoGenerate = true) var id: Int? = null,
                @ColumnInfo(name = "mood") var mood: Int,
                @ColumnInfo(name = "date") var date: String = today()
)
