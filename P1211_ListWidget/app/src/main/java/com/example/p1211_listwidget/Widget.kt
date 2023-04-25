package com.example.p1211_listwidget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.appwidget.AppWidgetProviderInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import java.util.Date
import java.util.Locale


class Widget : AppWidgetProvider() {

    companion object {

        const val ACTION_ON_CLICK = "com.example.p1211_listwidget.itemonclick"
        const val ITEM_POSITION = "item_position"
        const val UPDATE_ALL_WIDGETS = "update_all_widgets"

        @JvmStatic fun getTime(): String =
            SimpleDateFormat("HH:mm:ss", Locale.getDefault()).run {
                timeZone = TimeZone.getTimeZone("Europe/Moscow")
                format(Date())
            }

        @JvmStatic fun log(message: String) = Log.d("myLogs${this::class.simpleName}", message)
    }

//      вызывается в ответ на широковещательное сообщение AppWidgetManager#ACTION_APPWIDGET_ENABLED
//      при создании экземпляра виджета
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        log("Widget: onEnabled")
        val intent = Intent(context, Widget::class.java).apply {
            action = UPDATE_ALL_WIDGETS
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager: AlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pendingIntent)
    }
//      вызывается в ответ на широковещательное сообщение AppWidgetManager#ACTION_APPWIDGET_DISABLED
//      при удалении последнего экземпляра виджета
    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        log("Widget: onDisabled")
        val intent = Intent(context, Widget::class.java).apply {
            action = UPDATE_ALL_WIDGETS
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager: AlarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

//    при изменении размера виджета
    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)

        if (newOptions == null || context == null) return
        val minWidth: Int = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 0)
        val maxWidth: Int = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, 0)
        val minHeight: Int = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 0)
        val maxHeight: Int = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, 0)

        val widgetHostCategory: Int = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY, 0)

        Toast.makeText(context,
            """
            Width : $minWidth - $maxWidth
            Height : $minHeight - $maxHeight
            Category - ${
                when (widgetHostCategory) {
                    AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD -> "Keyguard"
                    AppWidgetProviderInfo.WIDGET_CATEGORY_HOME_SCREEN -> "Home Screen"
                    else -> "none"
                }
            }
            """.trimIndent(),
            Toast.LENGTH_LONG)
            .show()
    }

//    Метод приемника (ресивера)
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (context == null || intent == null) return
        val action: String? = intent.action
        log("Widget: onReceive. ACTION = $action")

        when (action) {
            AppWidgetManager.ACTION_APPWIDGET_UPDATE -> {
                val widgetID: Int = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
//                получение AppWidgetManager с помощью Context
                val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(context)
                updateWidget(context, appWidgetManager, widgetID)
            }
            ACTION_ON_CLICK -> {
                val itemPosition: Int = intent.getIntExtra(ITEM_POSITION, -1)
                if (itemPosition != -1)
                    Toast.makeText(context, "Clicked on the item $itemPosition", Toast.LENGTH_SHORT).show()
            }
            UPDATE_ALL_WIDGETS -> {
                val thisAppWidget = ComponentName(
                    context.packageName,
                    this::class.java.name
                )
                val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(context)
                val widgetIDs: IntArray = appWidgetManager.getAppWidgetIds(thisAppWidget)
                for (widgetID in widgetIDs) {
                    updateWidget(context, appWidgetManager, widgetID)
                }
            }
        }
    }

//    при создании и при обновлении (хз, почему-то не срабатывает при обновлении)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        log("Widget: onUpdate")
        for (i in appWidgetIds) {
            updateWidget(context, appWidgetManager, i)
        }
    }

    private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, widgetID: Int) {
        log("Widget: updateWidget")
        val remoteViews = RemoteViews(context.packageName, R.layout.widget)

        updateTextView(remoteViews, context, widgetID)
        setList(remoteViews, context, widgetID)
        setListClick(remoteViews, context)

        appWidgetManager.updateAppWidget(widgetID, remoteViews)
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetID, R.id.listView)
    }

    private fun updateTextView(remoteViews: RemoteViews, context: Context, widgetID: Int) {

        remoteViews.setTextViewText(R.id.tvUpdate, getTime())

        val updateIntent = Intent(context, Widget::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
        }
        val updatePending = PendingIntent.getBroadcast(
            context,
            widgetID,
            updateIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        remoteViews.setOnClickPendingIntent(R.id.tvUpdate, updatePending)
    }

    private fun setList(remoteViews: RemoteViews, context: Context, widgetID: Int) {

        val adapter = Intent(context, MyService::class.java)
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
        val data: Uri = Uri.parse(adapter.toUri(Intent.URI_INTENT_SCHEME))
        log("Widget: setList. Uri = $data")
        adapter.data = data
        remoteViews.setRemoteAdapter(R.id.listView, adapter)
    }

    private fun setListClick(remoteViews: RemoteViews, context: Context) {
        val listClickIntent = Intent(context, Widget::class.java)
        listClickIntent.action = ACTION_ON_CLICK
        val listClickPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            listClickIntent,
//            флаг должен быть MUTABLE,
//            чтобы его одного можно было применять к разным интентам в разных
//            пунктах в списке
            PendingIntent.FLAG_MUTABLE
        )
        remoteViews.setPendingIntentTemplate(R.id.listView, listClickPendingIntent)
    }
}