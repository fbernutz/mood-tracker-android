package com.example.feliziabernutz.moodtracking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.view.View
import android.arch.persistence.room.Room
import android.widget.Toast
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI

class MainActivity : AppCompatActivity() {
    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch {
            db = Room.databaseBuilder(applicationContext,
                    AppDatabase::class.java, "moods.db").build()
        }

        val onClickListener = View.OnClickListener { v ->
            val d = db
            d?.let {
                launch {
                    d.moodDao().insert(MoodEntity(
                            mood = when (v.id) {
                                R.id.bad_mood -> Mood.BAD.ordinal
                                R.id.normal_mood -> Mood.NORMAL.ordinal
                                R.id.good_mood -> Mood.GOOD.ordinal
                                else -> Mood.NORMAL.ordinal
                            }))

                    launch(UI) {
                        Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        findViewById<ImageButton>(R.id.bad_mood).setOnClickListener(onClickListener)
        findViewById<ImageButton>(R.id.normal_mood).setOnClickListener(onClickListener)
        findViewById<ImageButton>(R.id.good_mood).setOnClickListener(onClickListener)
    }
}
