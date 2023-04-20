package com.example.p1151_multiplescreen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import kotlin.ClassCastException

class TitlesFragment : ListFragment() {

//    Activity должна будет имплементировать этот интерфейс
//    и реализовать метод
    interface OnItemClickListener {
        fun itemClick(_position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = activity as OnItemClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement onItemClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.headers)
            )
    }

    override fun onListItemClick(listView: ListView, view: View, position: Int, id: Long) {
        super.onListItemClick(listView, view, position, id)
        listener.itemClick(position)
    }
}