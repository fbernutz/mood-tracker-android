package de.feliziabernutz.android.moodtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MoodAdapter(private val list: List<MoodEntity>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var cv = convertView
        if (cv == null) {
            cv = LayoutInflater.from(parent?.context).inflate(R.layout.row_mood, parent, false)
        }

        val mood = list[position]
        val imageView = cv?.findViewById<ImageView>(R.id.imageView)
        imageView?.setImageResource(
                when (mood.mood) {
                    Mood.BAD.ordinal -> R.drawable.ic_mood_bad_black_24dp
                    Mood.NORMAL.ordinal -> R.drawable.ic_face_black_24dp
                    Mood.GOOD.ordinal -> R.drawable.ic_mood_black_24dp
                    else -> R.drawable.ic_mood_bad_black_24dp
        })

        val text = cv?.findViewById<TextView>(R.id.textView)
        text?.text = mood.date

        return cv
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }
}
