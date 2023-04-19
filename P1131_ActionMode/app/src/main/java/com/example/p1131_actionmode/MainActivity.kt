package com.example.p1131_actionmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.ActionMode
import com.example.p1131_actionmode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic private fun log(message: String) = Log.d("myLogs", message)
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnActionMode.setOnClickListener{
            if (actionMode == null) actionMode = startSupportActionMode(callback)
            else (actionMode as ActionMode).finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private val callback: ActionMode.Callback = object: ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.context, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            log("item ${item?.title}")
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            log("destroy")
            actionMode = null
        }
    }
}