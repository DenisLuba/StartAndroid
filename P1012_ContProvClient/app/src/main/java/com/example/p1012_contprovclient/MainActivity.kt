package com.example.p1012_contprovclient

import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SimpleCursorAdapter
import androidx.core.content.contentValuesOf
import com.example.p1012_contprovclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private fun log(message: String) = Log.d("myLogs", message)

    private lateinit var binding: ActivityMainBinding

    companion object {
        val CONTACT_URI = Uri.parse("content://com.example.p1011_contentprovider/contacts")
        const val CONTACT_NAME = "name"
        const val CONTACT_EMAIL = "email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val cursor = contentResolver.query(CONTACT_URI, null, null, null, null)
        startManagingCursor(cursor)

        val from = arrayOf("name", "email")
        val to = arrayOf( android.R.id.text1, android.R.id.text2 ).toIntArray()

        val adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to)

        with (binding) {
            listViewContacts.adapter = adapter
            btnInsert.setOnClickListener { onClickInsert() }
            btnUpdate.setOnClickListener { onClickUpdate() }
            btnDelete.setOnClickListener { onClickDelete() }
            btnError.setOnClickListener { onClickError() }
        }

    }

    private fun onClickInsert() {
        val contentValues = ContentValues()
        contentValues.put(CONTACT_NAME, "name 4")
        contentValues.put(CONTACT_EMAIL, "email 4")
        val uri: Uri? = contentResolver.insert(CONTACT_URI, contentValues)
        log("Insert, result Uri : $uri")
    }

    private fun onClickUpdate() {
        val contentValues = ContentValues()
        contentValues.put(CONTACT_NAME, "name 5")
        contentValues.put(CONTACT_EMAIL, "email 5")
        val uri = ContentUris.withAppendedId(CONTACT_URI, 2)
        val count: Int = contentResolver.update(uri, contentValues, null, null)
        log("update, count = $count")
    }

    private fun onClickDelete() {
        val uri = ContentUris.withAppendedId(CONTACT_URI, 3)
        val count: Int = contentResolver.delete(uri, null, null)
        log("delete, count = $count")
    }

    private fun onClickError() {
        val uri = Uri.parse("content://com.example.p1011_contentprovider/phones")
        try {
            val cursor = contentResolver.query(uri, null, null, null, null)
        } catch (e: java.lang.Exception) {
            log("Error: ${e.javaClass}, ${e.message}")
        }
    }
}