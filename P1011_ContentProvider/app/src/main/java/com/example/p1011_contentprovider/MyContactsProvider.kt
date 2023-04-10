package com.example.p1011_contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.text.TextUtils
import android.util.Log

class MyContactsProvider : ContentProvider() {

    private fun log(message: String) = Log.d("myLogs", message)

    //    константы для БД
    companion object {
        //    БД
        private const val DB_NAME = "myDB"
        private const val DB_VERSION = 1
        //    таблица
        private const val CONTACT_TABLE = "myContacts"
        //    поля
        private const val CONTACT_ID = "_id"
        private const val CONTACT_NAME = "name"
        private const val CONTACT_EMAIL = "email"
        //    скрипт создания таблицы
        private const val CREATE_TABLE = "CREATE TABLE $CONTACT_TABLE(" +
                "$CONTACT_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$CONTACT_NAME TEXT," +
                "$CONTACT_EMAIL TEXT);"
        //    URI
//    Authority
        private const val AUTHORITY = "com.example.p1011_contentprovider"
        //    path
        private const val CONTACT_PATH = "contacts"
        //    общий URI
        private val CONTACT_CONTENT_URI = Uri.parse("content://$AUTHORITY/$CONTACT_PATH")
        //    типы данных
//    набор строк
        const val CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$CONTACT_PATH"
        //    одна строка
        const val CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
        //    UriMatcher
//    общий URI
        const val URI_CONTACTS = 1
        //    URI с указанным ID
        const val URI_CONTACTS_ID = 2
        //    описание и создание UriMatcher
        private val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        init {
            uriMatcher.addURI(AUTHORITY, CONTACT_PATH, URI_CONTACTS)
            uriMatcher.addURI(AUTHORITY, "$CONTACT_PATH/#", URI_CONTACTS_ID)
        }
    }

    private lateinit var dbHelper: DBHelper
    private lateinit var database: SQLiteDatabase

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        log("delete, $uri")
        var select: String? = null
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> log("URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id: String = uri.lastPathSegment!!
                log("URI_CONTACTS_ID, $id")
                select = if (TextUtils.isEmpty(selection)) "$CONTACT_ID = $id"
                else "$selection AND $CONTACT_ID = $id"
            }
            else -> throw java.lang.IllegalArgumentException("Wrong URI: $uri")
        }
        database = dbHelper.writableDatabase
        val count: Int = database.delete(CONTACT_TABLE, select, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? {
        log("getType, $uri")
        return when (uriMatcher.match(uri)) {
            URI_CONTACTS -> CONTACT_CONTENT_TYPE
            URI_CONTACTS_ID -> CONTACT_CONTENT_ITEM_TYPE
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        log("insert, $uri")
        if (uriMatcher.match(uri) != URI_CONTACTS) throw java.lang.IllegalArgumentException("Wrong URI: $uri")

        database = dbHelper.writableDatabase
        val rowID = database.insert(CONTACT_TABLE, null, values)
        val resultUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, rowID)
//        уведомляем ContentResolver, что данные по адресу resultUri изменились
        context!!.contentResolver.notifyChange(resultUri, null)
        return resultUri
    }

    override fun onCreate(): Boolean {
        log("onCreate")
        dbHelper = DBHelper(context!!)
        return true
    }

    //    чтение
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        log("query, $uri")
        var sort: String? = sortOrder
        var select: String? = null
//        проверяем URI
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> { // общий URI
                log("URI_CONTACTS")
//            если сортировка не указана, ставим свою - по имени
                if (TextUtils.isEmpty(sortOrder)) sort = "$CONTACT_NAME ASC"
            }
            URI_CONTACTS_ID -> { // URI с ID
                val id: String = uri.lastPathSegment!!
                log("URI_CONTACTS_ID, $id")
//            добавляем ID к условию выборки
                select = if (TextUtils.isEmpty(selection)) "CONTACT_ID = $id"
                else "$selection AND CONTACT_ID = $id"
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        database = dbHelper.writableDatabase
        val cursor = database.query(CONTACT_TABLE, projection, select, selectionArgs, null, null, sort)
//    просим ContentResolver уведомлять этот курсор об изменениях данных в CONTACT_CONTENT_URI
        cursor.setNotificationUri(context!!.contentResolver, CONTACT_CONTENT_URI)
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        log("update, $uri")
        var select: String? = null
        when (uriMatcher.match(uri)) {
            URI_CONTACTS -> log("URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id: String = uri.lastPathSegment!!
                log("URI_CONTACTS_ID, $id")
                select = if (TextUtils.isEmpty(selection)) "$CONTACT_ID = $id"
                else "$selection AND $CONTACT_ID = $id"
            }
            else -> throw java.lang.IllegalArgumentException("Wrong URI: $uri")
        }
        database = dbHelper.writableDatabase
        val count: Int = database.update(CONTACT_TABLE, values, select, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        override fun onCreate(database: SQLiteDatabase?) {
            database?.execSQL(CREATE_TABLE)
            val contentValues = ContentValues()
            for (i in 1..3) {
                contentValues.put(CONTACT_NAME, "name $i")
                contentValues.put(CONTACT_EMAIL, "email $i")
                database?.insert(CONTACT_TABLE, null, contentValues)
            }
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            TODO("Not yet implemented")
        }
    }
}