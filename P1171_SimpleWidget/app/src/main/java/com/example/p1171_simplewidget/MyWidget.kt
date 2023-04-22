package com.example.p1171_simplewidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.util.Log
import java.util.Arrays

class MyWidget : AppWidgetProvider() {

    private fun log(message: String) = Log.d("myLogs", message)

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        log("onAppWidgetOptionsChanged $appWidgetId")
    }

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
        log("onUpdate ${Arrays.toString(appWidgetIds)}")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        log("onDeleted ${Arrays.toString(appWidgetIds)}")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        log("onDisabled")
    }
}