package com.example.p1121_dynamicactionbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.DeletedContacts
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.p1121_dynamicactionbar.databinding.ActivityMainBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic val MENU_ID = 1
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var fragment1: Fragment
    private lateinit var fragment2: Fragment
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fragment1 = Fragment1()
        fragment = fragment1
        fragment2 = Fragment2()

        with (binding) {
            switchAddDelete.setOnClickListener(::onClick)
            switchVisible.setOnClickListener(::onClick)
            btnFragment.setOnClickListener(::onClick)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu?.setGroupVisible(R.id.groupMenu, binding.switchVisible.isChecked)
        if (binding.switchAddDelete.isChecked) {
            menu?.add(0, MENU_ID, 0, R.string.menu_item1)
                ?.setIcon(android.R.drawable.ic_delete)
                ?.setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_ALWAYS or
                            MenuItem.SHOW_AS_ACTION_WITH_TEXT
                )
        } else menu?.removeItem(MENU_ID)

        return true
    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.switchAdd_Delete, R.id.switchVisible -> invalidateOptionsMenu() // перерисовка меню/ActionBar
            R.id.btnFragment -> {
                fragment = if (fragment == fragment1) fragment2 else fragment1
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit()
            }
        }
    }
}