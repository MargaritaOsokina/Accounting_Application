package com.example.myapplication3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    Button btnAdd, btnRead, btnClear;
    EditText etCon;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etCon=(EditText) findViewById(R.id.etCon);
        //etEmail=(EditText) findViewById(R.id.etEmail);

        dbHelper = new DBHelper(this);
    }


    @Override
    public void onClick(View v2) {
        String name = etCon.getText().toString();
        //String email = etEmail.getText().toString();

        SQLiteDatabase database2=dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v2.getId()){
            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_NAME2,name);
               // contentValues.put(DBHelper.KEY_MAIL,email);
                database2.insert(DBHelper.TABLE_CONTACTS3,null,contentValues);

                break;
            case R.id.btnRead:
                Cursor cursor = database2.query(DBHelper.TABLE_CONTACTS3,null,null,null,null,null,null);

                if (cursor.moveToFirst()){
                    int idIndex2 = cursor.getColumnIndex(DBHelper.KEY_ID2);
                    int nameIndex2 = cursor.getColumnIndex(DBHelper.KEY_NAME2);
                   // int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    do {
                        Log.d("mLog", "ID2 = " + cursor.getInt(idIndex2) + ", name2 - " + cursor.getString(nameIndex2));
                    } while (cursor.moveToNext());
                }else
                    Log.d("mLog","0 rows");

                cursor.close();
                break;

            case R.id.btnClear:
                database2.delete(DBHelper.TABLE_CONTACTS3,null,null);
                break;
        }
        dbHelper.close();

    }
}