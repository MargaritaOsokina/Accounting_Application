package com.example.myapplication3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 14;
    public static final String DATABASE_ACCOUNTING = "accountingDb";
    public static final String TABLE_MAT = "materialsAndServices";
    public static final String TABLE_COUNTERPARTIES = "allCounterparties";


    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_CO = "counterparties";

    public static final String KEY_NAME = "name";//наименование
    public static final String KEY_NUMBER = "number";//number
    public static final String KEY_SUM = "sum";
    public static final String KEY_PRICE = "price";
    public static final String KEY_NDS = "nds";
    public static final String KEY_ACCOUNT = "account";




    public static final String KEY_ID2 = "_id";
    public static final String KEY_CO2 = "co2";
    public static final String KEY_DEB = "debit";
    public static final String KEY_CRED = "credit";

    //private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_ACCOUNTING, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       db.execSQL("create table " + TABLE_COUNTERPARTIES + "(" + KEY_ID2
               + " integer primary key," + KEY_CO2 + " text," +KEY_DEB  + " text," +KEY_CRED  + " text" + ")");

        db.execSQL("create table " + TABLE_MAT + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_NUMBER + " text,"+ KEY_CO + " text,"
                + KEY_SUM + " INTEGER NOT NULL DEFAULT 0, "
                + KEY_DATE + " INTEGER NOT NULL DEFAULT 0, "
                + KEY_PRICE + " INTEGER NOT NULL DEFAULT 3, "
                + KEY_NDS + " TEXT NOT NULL," +
                KEY_ACCOUNT + " TEXT NOT NULL" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_MAT);
        db.execSQL("drop table if exists " + TABLE_COUNTERPARTIES);
        onCreate(db);

    }
    /*
    public List<String> selectAll() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = this.db.query(TABLE_COUNTERPARTIES, new String[] { "name" },
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
    }*/
}