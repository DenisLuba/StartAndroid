package com.example.p1211_listwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import java.util.Date
import java.util.Locale


class Widget : AppWidgetProvider() {

    companion object {

        const val ACTION_ON_CLICK = "com.example.p1211_listwidget.itemonclick"
        const val ITEM_POSITION = "item_position"

        @JvmStatic fun getTime(): String =
            SimpleDateFormat("HH:mm:ss", Locale.getDefault()).run {
                timeZone = TimeZone.getTimeZone("Europe/Moscow")
                format(Date())
            }

        @JvmStatic fun log(message: String) = Log.d("myLogs${this::class.simpleName}", message)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (context == null || intent == null) return
        log("Widget: onReceive")
        when (intent.action) {
            AppWidgetManager.ACTION_APPWIDGET_UPDATE -> {
                val widgetID: Int = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
                val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(context)
                updateWidget(context, appWidgetManager, widgetID)
            }
            ACTION_ON_CLICK -> {
                log("Widget: onReceive. item click")
                val itemPosition: Int = intent.getIntExtra(ITEM_POSITION, -1)
                if (itemPosition != -1)
                    Toast.makeText(context, "Clicked on the item $itemPosition", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
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
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        remoteViews.setPendingIntentTemplate(R.id.listView, listClickPendingIntent)
    }
}