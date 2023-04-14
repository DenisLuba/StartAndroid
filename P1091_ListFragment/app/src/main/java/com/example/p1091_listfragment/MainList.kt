package com.example.p1091_listfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.ListFragment

class MainList : ListFragment() {

    private val data: Array<String> = arrayOf("one", "two", "three", "four")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// получаем ListView фрагмента
        val listView: ListView = listView
// устанавливаем режим выбора пунктов списка
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
// Создаем адаптер, используя массив
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(), // возвращает контекст этого фрагмента, если он есть
            android.R.layout.simple_list_item_single_choice,
            data
        )
        listAdapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    //    обрабатываем выбранные пункты
    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        Log.d("myLogs", "Item $position is selected")
        Toast.makeText(requireContext(), "position = $position, id = $id", Toast.LENGTH_LONG).show()
    }
}