package com.example.p1201_clickwidget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.p1201_clickwidget.databinding.ConfigBinding

class ConfigActivity : AppCompatActivity() {

    private val binding: ConfigBinding by lazy {
        ConfigBinding.inflate(layoutInflater)
    }

    companion object {
        const val WIDGET_PREFERENCE: String = "WIDGET PREFERENCE"
        const val WIDGET_TIME_FORMAT: String = "WIDGET TIME FORMAT"
        const val WIDGET_COUNT: String = "WIDGET COUNT"
    }

    private var widgetID: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    private val resultIntent: Intent by lazy { Intent() }
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        извлекаем ID конфигурируемого виджета
        val intent: Intent = intent
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            widgetID = bundle.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, // key
                AppWidgetManager.INVALID_APPWIDGET_ID  // default value
            )
        }
//        и проверяем его корректность
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) finish()

//        формируем intent ответа
        resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
//        сразу устанавливаем отрицательный ответ (если будет нажата кнопка Назад)
        setResult(RESULT_CANCELED, resultIntent)

        setContentView(binding.root)

        binding.btnOk.setOnClickListener{ onClick() }

        sharedPreferences = getSharedPreferences(WIDGET_PREFERENCE, MODE_PRIVATE)
        with (sharedPreferences) {
            val timeFormat: String = getString(
                WIDGET_TIME_FORMAT + widgetID, // key
                "HH:mm:ss" // default
            ) ?: "HH:mm:ss" // if null

            binding.etFormat.setText(timeFormat)

            val count: Int = getInt(WIDGET_COUNT + widgetID, -1)
            if (count == -1)
                edit()
                .putInt(WIDGET_COUNT + widgetID, 0)
                .apply()
        }
    }

    private fun onClick() {
        sharedPreferences
            .edit() // Editor
            .putString(WIDGET_TIME_FORMAT + widgetID, binding.etFormat.text.toString())
            .apply()

        MyWidget.updateWidget(this, AppWidgetManager.getInstance(this), widgetID)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}