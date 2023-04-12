package com.example.p1051_fragmentdynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.Fragment
import com.example.p1051_fragmentdynamic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val btnAddId = R.id.btnAdd
    private val btnRemoveId = R.id.btnRemove
    private val btnReplaceId = R.id.btnReplace

    private lateinit var fragment1: Fragment
    private lateinit var fragment2: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        fragment1 = Fragment1()
        fragment2 = Fragment2()

        with (binding) {
            btnAdd.setOnClickListener { onClick(btnAdd) }
            btnRemove.setOnClickListener { onClick(btnRemove) }
            btnReplace.setOnClickListener { onClick(btnReplace) }
        }
    }

    private fun onClick(view: View) {

        val fragmentTransaction = fragmentManager.beginTransaction()

        when (view.id) {
            btnAddId -> fragmentTransaction.add(R.id.fragmentContainer, fragment1)
            btnRemoveId -> fragmentTransaction.remove(fragment1)
            btnReplaceId -> fragmentTransaction.replace(R.id.fragmentContainer, fragment2)
        }
        if (binding.switchStack.isChecked) fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}