package com.example.p1021_touch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView

class MainActivity : AppCompatActivity(), OnTouchListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.setOnTouchListener(this)
        setContentView(textView)
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        val x = event?.x ?: .0
        val y = event?.y ?: .0
        var result = ""

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> result = "Down: $x, $y" // нажатие
            MotionEvent.ACTION_MOVE -> result = "Move: $x, $y" // движение
            MotionEvent.ACTION_UP, // отпускание
            MotionEvent.ACTION_CANCEL -> result = "Up: $x, $y"
        }
        (view as TextView).text = result
        return true
    }
}