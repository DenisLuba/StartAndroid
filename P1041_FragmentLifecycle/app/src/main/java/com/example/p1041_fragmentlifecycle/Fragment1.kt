package com.example.p1041_fragmentlifecycle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class Fragment1 : Fragment() {

    private fun log(message: String) = Log.d("myLogs1", message)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log("Fragment1 onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("Fragment1 onCreate")
    }

//    аналог метода setContentView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("Fragment1 onCreateView")
        try{
            return inflater.inflate(R.layout.fragment1, null)
        } catch (e : java.lang.Exception) {
            log("Fragment1 onCreateView Exeption ${e.printStackTrace()}")
            throw e
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        log("Fragment1 onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        log("Fragment1 onStart")
    }

    override fun onResume() {
        super.onResume()
        log("Fragment1 onResume")
    }

    override fun onPause() {
        super.onPause()
        log("Fragment1 onPause")
    }

    override fun onStop() {
        super.onStop()
        log("Fragment1 onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("Fragment1 onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("Fragment1 onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        log("Fragment1 onDetach")
    }
}