package com.example.p1132_actionmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.AbsListView.MultiChoiceModeListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.view.ActionMode
import com.example.p1132_actionmode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private fun log(message: String) = Log.d("myLogs", message)

    private val data: Array<String> = arrayOf("one", "two", "three", "four", "five")

    private var actionMode: ActionMode? = null

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_activated_1,
            data
        )
        with (binding.lvActionMode) {
            setAdapter(adapter)
            choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
            setMultiChoiceModeListener(object: MultiChoiceModeListener {

                override fun onCreateActionMode(
                    mode: android.view.ActionMode?,
                    menu: Menu?
                ): Boolean {
                    mode?.menuInflater?.inflate(R.menu.context, menu)
                    return true
                }

                override fun onPrepareActionMode(
                    mode: android.view.ActionMode?,
                    menu: Menu?
                ): Boolean {
                    return false
                }

                override fun onActionItemClicked(
                    mode: android.view.ActionMode?,
                    item: MenuItem?
                ): Boolean {
                    mode?.finish()
                    return false
                }

                override fun onDestroyActionMode(mode: android.view.ActionMode?) {}

                override fun onItemCheckedStateChanged(
                    mode: android.view.ActionMode?,
                    position: Int,
                    id: Long,
                    checked: Boolean
                ) {
                    log("position = $position, checked = $checked")
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}