package de.feliziabernutz.android.moodtracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.view.View
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.android.UI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moods = arrayOf<ImageButton>(
                findViewById(R.id.bad_mood),
                findViewById(R.id.normal_mood),
                findViewById(R.id.good_mood))

        var moodForToday: MoodEntity? = null

        val db = MoodApp.db
        db?.let {
            launch {
                val moodsForToday = db.moodDao().byDate(today())
                moodForToday = when (moodsForToday.count()) {
                    0 -> null
                    1 -> moodsForToday.first()
                    else -> moodsForToday.last() //TODO: something went wrong, throw exception?
                }

                launch(UI) {
                    val currentMood = moodForToday
                    currentMood?.let {
                        val moodIndex = currentMood.mood
                        moods.forEachIndexed { index, imageButton ->
                            imageButton.isSelected = index == moodIndex
                        }
                    }
                }
            }
        }

        val onClickListener = View.OnClickListener { view ->
            db?.let {
                launch {
                    if (moodForToday != null) {
                        moodForToday!!.mood = when (view.id) {
                            R.id.bad_mood -> Mood.BAD.ordinal
                            R.id.normal_mood -> Mood.NORMAL.ordinal
                            R.id.good_mood -> Mood.GOOD.ordinal
                            else -> Mood.NORMAL.ordinal
                        }

                        db.moodDao().update(moodForToday!!)
                        launch(UI) {
                            Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val newMood = MoodEntity(
                                mood = when (view.id) {
                                    R.id.bad_mood -> Mood.BAD.ordinal
                                    R.id.normal_mood -> Mood.NORMAL.ordinal
                                    R.id.good_mood -> Mood.GOOD.ordinal
                                    else -> Mood.NORMAL.ordinal
                                }
                        )
                        db.moodDao().insert(newMood)
                        moodForToday = newMood
                        launch(UI) {
                            Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_SHORT).show()
                        }
                    }

                    launch(UI) {
                        val selectedMood = moods.indexOf(view)
                        moods.forEachIndexed { index, imageButton ->
                            imageButton.isSelected = index == selectedMood
                        }
                    }
                }
            }
        }

        moods.forEach { button ->
            button.isSelected = false
            button.setOnClickListener(onClickListener)
        }

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
