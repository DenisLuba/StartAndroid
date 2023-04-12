package com.example.p1041_fragmentlifecycle

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class Fragment2 : Fragment() {

    private fun log(message: String) = Log.d("myLogs2", message)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log("Fragment2 onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("Fragment2 onCreate")
    }

//    аналог метода setContentView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("Fragment2 onCreateView")
    try{
        return inflater.inflate(R.layout.fragment2, null)
    } catch (e : java.lang.Exception) {
        log("Fragment2 onCreateView Exeption ${e.printStackTrace()}")
        throw e
    }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        log("Fragment2 onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        log("Fragment2 onStart")
    }

    override fun onResume() {
        super.onResume()
        log("Fragment2 onResume")
    }

    override fun onPause() {
        super.onPause()
        log("Fragment2 onPause")
    }

    override fun onStop() {
        super.onStop()
        log("Fragment2 onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("Fragment2 onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("Fragment2 onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        log("Fragment2 onDetach")
    }
}