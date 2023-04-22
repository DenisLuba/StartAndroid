package com.example.p1181_customwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.widget.RemoteViews
import com.example.p1181_customwidget.ConfigActivity.Companion.log
import java.util.Arrays

class MyWidget : AppWidgetProvider() {

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        log("onEnabled")
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        if (context == null || appWidgetIds == null) return

        log("onUpdate ${Arrays.toString(appWidgetIds)}")

        val sharedPreferences: SharedPreferences? = context.getSharedPreferences(
            ConfigActivity.WIDGET_PREFERENCE, Context.MODE_PRIVATE
        )

        if (sharedPreferences == null || appWidgetManager == null) return

        for (id in appWidgetIds) {
            updateWidget(context, appWidgetManager, sharedPreferences, id)
        }
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        if (context == null || appWidgetIds == null) return

        log("onDeleted ${Arrays.toString(appWidgetIds)}")

//        Удаляем Preferences
        val editor: Editor? = context.getSharedPreferences(
            ConfigActivity.WIDGET_PREFERENCE, Context.MODE_PRIVATE
        )?.edit()

        if (editor != null) {
            for (id in appWidgetIds) {
                editor.remove("${ConfigActivity.WIDGET_TEXT}$id")
                editor.remove("${ConfigActivity.WIDGET_COLOR}$id")
            }
        }
        editor?.apply()
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        log("onDisabled")
    }

    companion object {
        @JvmStatic fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, sharedPreferences: SharedPreferences, id: Int) {
            log("updateWidget $id")

//            Читаем параметры Preferences
            val widgetText: String = sharedPreferences.getString("${ConfigActivity.WIDGET_TEXT}$id", null) ?: return
            val widgetColor: Int = sharedPreferences.getInt("${ConfigActivity.WIDGET_COLOR}$id", 0)

//            астраиваем внешний вид виджета
            val remoteViews = RemoteViews(context.packageName, R.layout.widget)
            remoteViews.setTextViewText(R.id.textView, widgetText)
            remoteViews.setInt(R.id.textView, "setBackgroundColor", widgetColor)

//            Обновляем виджет
            appWidgetManager.updateAppWidget(id, remoteViews)
        }
    }
}