package com.example.p1201_clickwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.log

class MyWidget : AppWidgetProvider() {

    companion object {
        const val ACTION_CHANGE = "com.example.p1201_clickwidget.change_count"
        const val ACTION_UPDATE = "com.example.p1201_clickwidget.update"

        @JvmStatic fun updateTime(sharedPreferences: SharedPreferences, remoteViews: RemoteViews, widgetID: Int) {
//        Читаем формат времени и определяем текущее время
            val timeFormat: String = sharedPreferences.getString(
                ConfigActivity.WIDGET_TIME_FORMAT + widgetID,
                null
            ) ?: return

            val simpleDateFormat = SimpleDateFormat(timeFormat, Locale.US)
            val currentTime: String = simpleDateFormat.format(Date())

//            Читаем счетчик
            val count: String = sharedPreferences.getInt(
                ConfigActivity.WIDGET_COUNT + widgetID,
                0
            ).toString()

//            Помещаем данные в текстовые поля
            remoteViews.setTextViewText(R.id.tvTime, currentTime)
            remoteViews.setTextViewText(R.id.tvCount, count)
        }

//            Конфигурационный экран (первая зона)
        @JvmStatic fun configureRemoteViews(
            remoteViews: RemoteViews,
            context: Context,
            widgetID: Int
        ) {
            val configIntent = Intent(context, ConfigActivity::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_CONFIGURE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                widgetID,
                configIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            remoteViews.setOnClickPendingIntent(R.id.tvPressConfig, pendingIntent)
        }

//        Обновление виджета (вторая зона)
        @JvmStatic fun updateRemoteViews(
            remoteViews: RemoteViews,
            context: Context,
            widgetID: Int
        ) {

    Log.d("myLogs2", "updateRemoteViews, widgetID = $widgetID")
            val updateIntent = Intent(context, MyWidget::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                widgetID,
                updateIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            remoteViews.setOnClickPendingIntent(R.id.tvPressUpdate, pendingIntent)
        }

        @JvmStatic fun countRemoteViews(
            remoteViews: RemoteViews,
            context: Context,
            widgetID: Int
        ) {
            val countIntent = Intent(context, MyWidget::class.java).apply {
                action = ACTION_CHANGE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                widgetID,
                countIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            remoteViews.setOnClickPendingIntent(R.id.tvPressCount, pendingIntent)
        }

        @JvmStatic fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, widgetID: Int) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREFERENCE,
                Context.MODE_PRIVATE
            )

            val remoteViews = RemoteViews(context.packageName, R.layout.widget)

            updateTime(sharedPreferences, remoteViews, widgetID)
            configureRemoteViews(remoteViews, context, widgetID)
            updateRemoteViews(remoteViews, context, widgetID)
            countRemoteViews(remoteViews, context, widgetID)
//            Обновляем виджет
            appWidgetManager.updateAppWidget(widgetID, remoteViews)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
//            извлекаем ID экземпляра
        var widgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
        val bundle: Bundle? = intent?.extras
        if (bundle != null) {
            widgetId = bundle.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

//        Проверяем, что это intent от нажатия на третью зону
        if (intent?.action.equals(ACTION_CHANGE, ignoreCase = true)) {

            if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
//                Читаем значение счетчика, увеличиваем на 1 и записываем
                val sharedPreferences: SharedPreferences? = context.getSharedPreferences(
                    ConfigActivity.WIDGET_PREFERENCE,
                    Context.MODE_PRIVATE
                )
                var count: Int =
                    sharedPreferences?.getInt(ConfigActivity.WIDGET_COUNT + widgetId, 0) ?: 0
                sharedPreferences
                    ?.edit()
                    ?.putInt(ConfigActivity.WIDGET_COUNT + widgetId, ++count)
                    ?.apply()
            }
        }
        if (intent?.action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE, ignoreCase = true)) {

            Log.d("myLogs", widgetId.toString())

            if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {



                val sharedPreferences: SharedPreferences? = context.getSharedPreferences(
                    ConfigActivity.WIDGET_PREFERENCE,
                    Context.MODE_PRIVATE
                )
                val timeFormat: String =
                    sharedPreferences?.getString(ConfigActivity.WIDGET_TIME_FORMAT + widgetId, "HH:mm:ss") ?: "HH:mm:ss"
                sharedPreferences
                    ?.edit()
                    ?.putString(ConfigActivity.WIDGET_TIME_FORMAT + widgetId, timeFormat)
                    ?.apply()

                Log.d("myLogs", "onReceive update, timeFormat = $timeFormat")
            }
        }
//                Обновляем виджет
        updateWidget(context, AppWidgetManager.getInstance(context), widgetId)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
//        обновляем все экземпляры
        if (appWidgetIds != null && context != null && appWidgetManager != null) {
            for (i in appWidgetIds)
                updateWidget(context, appWidgetManager, i)
        }
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
//        Удаляем Preferences
        val editor: Editor? = context?.getSharedPreferences(
            ConfigActivity.WIDGET_PREFERENCE,
            Context.MODE_PRIVATE
        )?.edit()

        if (appWidgetIds != null && editor != null) {
            for(widgetID in appWidgetIds) {
                editor.remove(ConfigActivity.WIDGET_TIME_FORMAT + widgetID)
                editor.remove(ConfigActivity.WIDGET_COUNT + widgetID)
            }
            editor.apply()
        }
    }


}