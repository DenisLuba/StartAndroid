package com.example.p1211_listwidget

import android.content.Intent
import android.widget.RemoteViewsService
import com.example.p1211_listwidget.Widget.Companion.log

class MyService : RemoteViewsService() {
//        создает адаптер и возвращает этот созданный адаптер системе
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory = MyFactory(
        applicationContext,
        intent
    ).apply { log("MyService: onGetViewFactory") }
}