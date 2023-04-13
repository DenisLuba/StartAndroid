package com.example.p1061_fragmentactivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class Fragment1 : Fragment() {

    private val logTag = "myLogs"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment1, container, false)
        val button: Button = view.findViewById(R.id.button)
        button.setOnClickListener {
            Log.d(logTag, "Button click in Fragment1")
            val text = "Access from Fragment1"
            activity?.findViewById<Button>(R.id.btnFind)?.text = text
        }
        return view
    }
}