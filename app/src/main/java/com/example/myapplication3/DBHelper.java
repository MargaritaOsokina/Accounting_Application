package com.example.myapplication3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DBHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_CONTACTS3 = "contacts3";


    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_NAME = "name";//наименование
    public static final String KEY_MAIL = "mail";//number
    public static final String KEY_SUM = "sum";
    public static final String KEY_PRICE = "price";
    public static final String KEY_NDS = "nds";
    public static final String KEY_ACCOUNT = "account";




    public static final String KEY_ID2 = "_id";
    public static final String KEY_NAME2 = "name2";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL("create table " + TABLE_CONTACTS3 + "(" + KEY_ID2
               + " integer primary key," + KEY_NAME2  + " text" + ")");

        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_MAIL + " text,"
                + KEY_SUM + " INTEGER NOT NULL DEFAULT 0, "
                + KEY_DATE + " INTEGER NOT NULL DEFAULT 0, "
                + KEY_PRICE + " INTEGER NOT NULL DEFAULT 3, "
                + KEY_NDS + " TEXT NOT NULL," +
                KEY_ACCOUNT + " TEXT NOT NULL" + ")");
//TEXT NOT NULL
      //  db.execSQL("create table contacts3 ("
       //         + "id integer primary key autoincrement, "
        //        + "name text "
        //        + ");");

        //db.execSQL("CREATE TABLE contacts2 (_id INTEGER PRIMARY KEY, name TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        db.execSQL("drop table if exists " + TABLE_CONTACTS3);
        onCreate(db);

    }
    public List<String> selectAll() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = this.db.query(TABLE_CONTACTS3, new String[] { "name" },
                null, null, null, null, "name desc");

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }
}