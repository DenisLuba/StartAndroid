package com.example.p1181_customwidget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.p1181_customwidget.databinding.ConfigBinding

class ConfigActivity : AppCompatActivity() {

    private val binding: ConfigBinding by lazy {
        ConfigBinding.inflate(layoutInflater)
    }

    private var widgetID: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    private val resultIntent: Intent by lazy { intent }

    companion object {
        @JvmStatic fun log(message: String) = Log.d("myLogs", message)

        const val WIDGET_PREFERENCE = "widget_preference"
        const val WIDGET_TEXT = "widget_text"
        const val WIDGET_COLOR = "widget_color"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate Activity config")

//        извлекаем ID конфигурируемого виджета
        val intent = intent
        val extras: Bundle? = intent.extras
        if (extras != null)
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

//        и проверяем его корректность
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID)
            finish()

//        формируем intent ответа
        resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)

//        отрицательный ответ
        setResult(RESULT_CANCELED, resultIntent)

        setContentView(binding.root)

        binding.btnOk.setOnClickListener { onClick() }
    }

    private fun onClick() {
        val color: Int = when (binding.rgColor.checkedRadioButtonId) {
            R.id.radioMelon -> resources.getColor(R.color.melon_color)
            R.id.radioPaleDogwood -> resources.getColor(R.color.pale_dogwood_color)
            R.id.radioSilver -> resources.getColor(R.color.silver_color)
            else -> resources.getColor(R.color.cambridge_blue_color)
        }

//        Записываем значения с экрана в Preferences
        val sharedPreferences: SharedPreferences = getSharedPreferences(WIDGET_PREFERENCE, MODE_PRIVATE)
        val editor: Editor = sharedPreferences.edit()

        editor.putString("$WIDGET_TEXT$widgetID", binding.etText.text.toString())
        editor.putInt("$WIDGET_COLOR$widgetID", color)
        editor.apply()

//        положительный ответ
        setResult(RESULT_OK, resultIntent)

        val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(this)
        MyWidget.updateWidget(this, appWidgetManager, sharedPreferences, widgetID)

        log("finish Activity config $widgetID")
        finish()
    }
}