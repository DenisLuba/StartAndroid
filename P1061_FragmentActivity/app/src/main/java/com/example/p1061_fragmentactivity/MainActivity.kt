package com.example.p1061_fragmentactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.p1061_fragmentactivity.databinding.ActivityMainBinding
import com.example.p1061_fragmentactivity.Fragment2.OnSomeEventListener

class MainActivity : AppCompatActivity(), OnSomeEventListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.btnFind.setOnClickListener { onClick() }

        val fragment2 = Fragment2()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment2, fragment2)
        fragmentTransaction.commit()
    }

    private fun onClick() {
        val text = "Access to Fragment%d from Activity"

        val fragment1: Fragment1 = supportFragmentManager.findFragmentById(R.id.fragment1) as Fragment1
        (fragment1.view?.findViewById(R.id.textView) as TextView).text = text.format(1)

        val fragment2: Fragment2 = supportFragmentManager.findFragmentById(R.id.fragment2) as Fragment2
        (fragment2.view?.findViewById(R.id.textView) as TextView).text = text.format(2)
    }

    override fun someEvent(string: String) {
        val text = "Text from Fragment 2: $string"

        val fragment1: Fragment1 = supportFragmentManager.findFragmentById(R.id.fragment1) as Fragment1
        (fragment1.view?.findViewById(R.id.textView) as TextView).text = text
    }
}