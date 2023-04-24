package com.example.p1211_listwidget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.example.p1211_listwidget.Widget.Companion.getTime
import com.example.p1211_listwidget.Widget.Companion.log

class MyFactory(
    private val context: Context,
    intent: Intent
) : RemoteViewsFactory {

    private lateinit var data: MutableList<String>
    private var widgetID: Int = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
    )

//    создание адаптера
    override fun onCreate() {
        log("MyFactory: onCreate")
        data = mutableListOf()
    }

//    onDataSetChanged – вызывается, когда поступил запрос на обновление данных в списке.
//    Т.е. в этом методе мы подготавливаем данные для списка.
//    Метод заточен под выполнение тяжелого долгого кода.
    override fun onDataSetChanged() {
    log("MyFactory: onDataSetChanged")
        data.clear()
        data.add(getTime())
        data.add(hashCode().toString())
        data.add(widgetID.toString())

        for (i in 3 until 15) data.add("Item $i")
    }

    override fun getCount() : Int = data.size

    //    getViewAt() - создание пунктов списка
    override fun getViewAt(position: Int) = RemoteViews(
        context.packageName,
        R.layout.item
    ).apply {
        log("MyFactory: getViewAt: $position")
        setTextViewText(R.id.tvItemText, data[position])
        val clickIntent = Intent()
        clickIntent.putExtra(Widget.ITEM_POSITION, position)
        setOnClickFillInIntent(R.id.tvItemText, clickIntent)
    }

//        getLoadingView() возвращает View, которое система будет показывать вместо пунктов списка,
//        пока они создаются.
//        Если ничего здесь не создавать, то система использует некое дефолтное View.
    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount() = 1

    override fun getItemId(positon: Int): Long = positon.toLong()

    override fun hasStableIds() = true

//    onDestroy – вызывается при удалении последнего списка, который использовал адаптер
//    (один адаптер может использоваться несколькими списками).
    override fun onDestroy() {}
}