package com.example.p1101_dialogfragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.p1101_dialogfragment.databinding.Dialog1Binding

class Dialog1 : DialogFragment(), OnClickListener {

    private fun log(message: String) = Log.d("myLogs1", message)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dialog?.setTitle("Title!")

//        val view: View = inflater.inflate(R.layout.dialog1, container, false)
//
//        view.findViewById<Button>(R.id.btnYes).setOnClickListener(this)
//        view.findViewById<Button>(R.id.btnNo).setOnClickListener(this)
//        view.findViewById<Button>(R.id.btnMaybe).setOnClickListener(this)
//
//        return view

        val binding: Dialog1Binding = Dialog1Binding.inflate(layoutInflater)

        with(binding) {
            btnYes.setOnClickListener(this@Dialog1)
            btnNo.setOnClickListener(this@Dialog1)
            btnMaybe.setOnClickListener(this@Dialog1)
        }

        return binding.root
    }

    override fun onClick(view: View?) {
        log("Dialog 1: ${(view as Button).text}")
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        log("Dialog 1: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        log("Dialog 1: onCancel")
    }
}