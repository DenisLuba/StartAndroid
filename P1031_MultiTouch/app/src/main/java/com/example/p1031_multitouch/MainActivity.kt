package com.example.p1031_multitouch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView

class MainActivity : AppCompatActivity(), OnTouchListener {

    private var inTouch: Boolean = false
    private var pointerDownIndex: Int = -1
    private var pointerUpIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.textSize = 30F
        textView.setOnTouchListener(this)
        setContentView(textView)
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
//        событие
        val actionMask: Int = event?.actionMasked ?: 0
//        индекс касания
        val actionIndex: Int = event?.actionIndex ?: 0
//        число касаний
        val pointerCount: Int = event?.pointerCount ?: 0

        val stringBuilder = StringBuilder()

        when (actionMask) {
//            DOWN
            MotionEvent.ACTION_DOWN -> { // первое касание
                inTouch = true
                pointerDownIndex = actionIndex +1
            }
            MotionEvent.ACTION_POINTER_DOWN -> pointerDownIndex = actionIndex + 1 // последующие касания

//            UP
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> { // прерывание последнего касания
                inTouch = false
                pointerUpIndex = actionIndex + 1
                stringBuilder.setLength(0)
            }
            MotionEvent.ACTION_POINTER_UP -> pointerUpIndex = actionIndex + 1 // прерывания касаний

//            MOVE
            MotionEvent.ACTION_MOVE -> { // движение
                stringBuilder.setLength(0)

                for (i in 0..9) {

                    if (i < pointerCount) {
                        stringBuilder.append("Index = $i\n")
                        stringBuilder
                            .append("ID = ${event?.getPointerId(i)}")
                            .append(", X = ${event?.x}")
                            .append(", Y = ${event?.y}")
                            .append("\r\n")
                    }
                    else break
                }
            }
        }
        var result = "DOWN: $pointerDownIndex\nUP: $pointerUpIndex\n"
        if (inTouch) result += "pointerCount = $pointerCount\n$stringBuilder"

        (view as TextView).text = result
        return true
    }
}