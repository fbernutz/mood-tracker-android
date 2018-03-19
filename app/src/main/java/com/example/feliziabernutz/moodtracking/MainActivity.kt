package com.example.feliziabernutz.moodtracking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.view.View
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onClickListener = View.OnClickListener { view ->
            val db = MoodApp.db
            db?.let {
                launch {
                    db.moodDao().insert(MoodEntity(
                            mood = when (view.id) {
                                R.id.bad_mood -> Mood.BAD.ordinal
                                R.id.normal_mood -> Mood.NORMAL.ordinal
                                R.id.good_mood -> Mood.GOOD.ordinal
                                else -> Mood.NORMAL.ordinal
                            }))

                    launch(UI) {
                        Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_SHORT).show()
//                        finish()
                        view.isSelected = !(view.isSelected)
                        //TODO: dont finish app, select button
                    }
                }
            }
        }

        findViewById<ImageButton>(R.id.bad_mood).setOnClickListener(onClickListener)
        findViewById<ImageButton>(R.id.normal_mood).setOnClickListener(onClickListener)
        findViewById<ImageButton>(R.id.good_mood).setOnClickListener(onClickListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.mood_list -> {
                startActivity(Intent(this, MoodListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
