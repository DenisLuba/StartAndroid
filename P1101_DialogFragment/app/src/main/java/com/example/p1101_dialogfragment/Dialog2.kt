package com.example.p1101_dialogfragment

import android.app.AlertDialog.Builder
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.content.DialogInterface.OnClickListener
import androidx.fragment.app.DialogFragment

class Dialog2 : DialogFragment(), OnClickListener {

    private fun log(message: String) = Log.d("myLogs2", message)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Builder(activity) // new AlertDialog.Builder(getActivity()) in Java
            .setTitle("Title!")
            .setMessage(R.string.message_text)
            .setPositiveButton(R.string.yes, this)
            .setNegativeButton(R.string.no, this)
            .setNeutralButton(R.string.maybe, this)
            .create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val i: Int = when (which) {
            Dialog.BUTTON_POSITIVE -> R.string.yes
            Dialog.BUTTON_NEGATIVE -> R.string.no
            Dialog.BUTTON_NEUTRAL -> R.string.maybe
            else -> -1
        }
        log("Dialog 2: ${resources.getString(i)}")
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        log("Dialog 2: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        log("Dialog 2: onCancel")
    }
}