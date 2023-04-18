package com.example.p1101_dialogfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.p1101_dialogfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var dialog1: DialogFragment
    private lateinit var dialog2: DialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dialog1 = Dialog1()
        dialog2 = Dialog2()

        with (binding) {
            btnDialog1.setOnClickListener(::onClick)
            btnDialog2.setOnClickListener{ onClick(btnDialog2) }
        }
    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.btnDialog1 -> dialog1.show(supportFragmentManager, "dialog1 tag")
            R.id.btnDialog2 -> dialog2.show(supportFragmentManager, "dialog2 tag")
        }
    }
}