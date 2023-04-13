package com.example.p1061_fragmentactivity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class Fragment2 : Fragment() {

    private val logTag = "myLogs"

    private lateinit var someEventListener: OnSomeEventListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            someEventListener = activity as OnSomeEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException("${activity?.toString()} must implement onSomeEventListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment2, container, false)
        val button: Button = view.findViewById(R.id.button)
        button.setOnClickListener {
            Log.d(logTag, "Button click in Fragment2")
            someEventListener.someEvent("Test text to Fragment1")
        }
        return view
    }

    interface OnSomeEventListener {
        fun someEvent(string: String)
    }
}