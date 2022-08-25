package com.example.myapplication3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {


    EditText nameBox;
    DBHelper dbHelper2;
    EditText yearBox;
    Button delButton;
    Button saveButton;
    Dialog dialog;
    DBHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // Найдите элемент TextView внутри вашей разметки
        // и установите ему соответствующий текст
       // TextView text = (TextView) dialog.findViewById(R.id.);
       // text.setText("Текст в диалоговом окне. Вы любите котов?");


        nameBox = findViewById(R.id.name);
        //yearBox = findViewById(R.id.year);
        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DBHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DBHelper.TABLE_CONTACTS3 + " where " +
                    DBHelper.KEY_ID2 + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            //yearBox.setText(String.valueOf(userCursor.getInt(2)));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){
        dbHelper2 = new DBHelper(this);
        SQLiteDatabase database=dbHelper2.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //database.insert(DBHelper.TABLE_CONTACTS3,null,contentValues);

        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS3,null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID2);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME2);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_DEB);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_CRED);


            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name - " + cursor.getString(nameIndex) + ", deb - " + cursor.getString(emailIndex)+ ", cred = " + cursor.getString(dateIndex)
                       );
            } while (cursor.moveToNext());
        }else
            Log.d("mLog","0 rows");

        cursor.close();

        ContentValues cv = new ContentValues();
        if//(nameBox.getText().toString()!=("[a-zA-Z]"))
        (nameBox.getText().toString().equals(""))
        {
            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.show(getSupportFragmentManager(), "custom");
        }
        else
        {
            cv.put(DBHelper.KEY_NAME2, nameBox.getText().toString());
            cv.put(DBHelper.KEY_DEB, "0");
            cv.put(DBHelper.KEY_CRED, "0");
            // cv.put(DatabaseHelper.COLUMN_YEAR, Integer.parseInt(yearBox.getText().toString()));

            if (userId > 0) {
                db.update(DBHelper.TABLE_CONTACTS3, cv, DBHelper.KEY_ID2 + "=" + userId, null);
            } else {
                db.insert(DBHelper.TABLE_CONTACTS3, null, cv);
            }
            goHome();        }

    }
    public void delete(View view){
        db.delete(DBHelper.TABLE_CONTACTS3, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }



}
/*
    Button btnAdd;//, btnRead, btnClear;
    EditText etCon;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

      //  btnRead = (Button) findViewById(R.id.btnRead);
      //  btnRead.setOnClickListener(this);

       // btnClear = (Button) findViewById(R.id.btnClear);
       // btnClear.setOnClickListener(this);

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
} */