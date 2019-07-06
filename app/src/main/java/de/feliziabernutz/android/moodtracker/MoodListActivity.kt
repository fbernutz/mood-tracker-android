package de.feliziabernutz.android.moodtracker

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoodListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_list)

        val listView = findViewById<ListView>(R.id.mood_list)
        val db = MoodApp.db
        db?.let {
            GlobalScope.launch {
                val adapter = MoodAdapter(db.moodDao().all)
                launch(Main) {
                    listView.adapter = adapter
                }
            }
        }
    }
}
