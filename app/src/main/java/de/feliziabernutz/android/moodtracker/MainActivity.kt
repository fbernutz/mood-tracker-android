package de.feliziabernutz.android.moodtracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moods = arrayOf<ImageButton>(
            findViewById(R.id.bad_mood),
            findViewById(R.id.normal_mood),
            findViewById(R.id.good_mood)
        )

        var moodForToday: MoodEntity? = null

        val db = MoodApp.db
        db?.let {
            GlobalScope.launch {
                val moodsForToday = db.moodDao().byDate(today())
                moodForToday = when (moodsForToday.count()) {
                    0 -> null
                    1 -> moodsForToday.first()
                    else -> moodsForToday.last() // TODO: something went wrong, throw exception?
                }

                launch(Main) {
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
                GlobalScope.launch {
                    if (moodForToday != null) {
                        moodForToday!!.mood = moodForView(view.id).ordinal
                        db.moodDao().update(moodForToday!!)

                        launch(Main) {
                            Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        val newMood = MoodEntity(mood = moodForView(view.id).ordinal)
                        db.moodDao().insert(newMood)
                        moodForToday = newMood

                        launch(Main) {
                            Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    launch(Main) {
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

    private fun moodForView(id: Int): Mood {
        return when (id) {
            R.id.bad_mood -> Mood.BAD
            R.id.normal_mood -> Mood.NORMAL
            R.id.good_mood -> Mood.GOOD
            else -> Mood.NORMAL
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
