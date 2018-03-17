package com.example.feliziabernutz.moodtracking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.support.design.widget.Snackbar
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onClickListener = View.OnClickListener { v ->
            val message = when (v.id) {
                R.id.bad_mood -> "Bad mood"
                R.id.normal_mood -> "Normal mood"
                R.id.good_mood -> "Good mood"
                else -> "error"
            }
            Snackbar.make(v, R.string.saved, Snackbar.LENGTH_SHORT).show()
            finish()
        }

        findViewById<ImageButton>(R.id.bad_mood).setOnClickListener(onClickListener)
        findViewById<ImageButton>(R.id.normal_mood).setOnClickListener(onClickListener)
        findViewById<ImageButton>(R.id.good_mood).setOnClickListener(onClickListener)
    }
}
