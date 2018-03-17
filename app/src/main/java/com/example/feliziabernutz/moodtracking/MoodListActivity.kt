package com.example.feliziabernutz.moodtracking

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MoodListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_list)

        val listView = findViewById<ListView>(R.id.mood_list)
        val db = MoodApp.db
        db?.let {
            launch {
                val adapter = MoodAdapter(db.moodDao().all)
                launch(UI) {
                    listView.adapter = adapter
                }
            }
        }
    }
}