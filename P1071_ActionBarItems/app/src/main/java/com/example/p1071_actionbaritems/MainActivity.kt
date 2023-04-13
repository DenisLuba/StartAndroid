package com.example.p1071_actionbaritems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with (supportActionBar!!) {
//        Скрыть ActionBar
//            hide()

            title = "My New Title"
            subtitle = "Subtitle"

//            Установить стрелку "Назад" в левом верхнем углу ActionBar
//            включить кнопку "Домой"
//            setDisplayHomeAsUpEnabled(true)
//            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
        }


//        val home = findViewById<Button>(android.R.id.home)
//                home.setOnClickListener {
//            findViewById<TextView>(R.id.textView).text = "Go home"
//        }
    }

//    Создаем меню в ActionBar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

//    Обработка нажатия на элементы меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}