package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.ContentValues;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class CounterpartiesMenu extends AppCompatActivity {


    ListView userList;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterparties_menu);

        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CounterpartiesMenu.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DBHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_CONTACTS3, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DBHelper.KEY_NAME2};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        userList.setAdapter(userAdapter);
    }

    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }



}
/*
    Button btn_counterparty_menu;
    Button btn_list_of_counterparties;
    Button btn_delete_counterparty;
    DBHelper dbHelper2;
    SQLiteDatabase db;
    SimpleCursorAdapter userAdapter;
    ListView lvData;
    Cursor userCursor;
    ListView userList;
    EditText header;
    long userId=0;
    EditText nameBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterparties_menu);
        header = findViewById(R.id.header);
        userList = findViewById(R.id.lvData);
        nameBox = findViewById(R.id.etCon2);

        btn_counterparty_menu = (Button) findViewById(R.id.add_counterparty_menu);
        btn_counterparty_menu.setOnClickListener(this);

        btn_list_of_counterparties = (Button) findViewById(R.id.list_of_counterparties);
        btn_list_of_counterparties.setOnClickListener(this);

        btn_delete_counterparty = (Button) findViewById(R.id.delete_counterparty);
        btn_delete_counterparty.setOnClickListener(this);

        dbHelper2 = new DBHelper(this);
        SQLiteDatabase database=dbHelper2.getWritableDatabase();
        // получаем курсор
        dbHelper2 = new DBHelper(getApplicationContext());

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CounterpartiesMenu.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

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
        }


    }
    @Override
    public void onResume() {
        super.onResume();// открываем подключение
        db = dbHelper2.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_CONTACTS3, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DBHelper.KEY_ID2,DBHelper.KEY_NAME2,};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        userList.setAdapter(userAdapter);
    }
   // @Override
   // public void onDestroy(){
    //    super.onDestroy();
        // Закрываем подключение и курсор
     ////   db.close();
      //  userCursor.close();
   // }



    @Override
    public void onClick(View v) {
        ContentValues contentValues = new ContentValues();
        String name = btn_counterparty_menu.getText().toString();
        SQLiteDatabase database2=dbHelper2.getWritableDatabase();


        switch (v.getId()) {
            case R.id.add_counterparty_menu:
                contentValues.put(DBHelper.KEY_NAME2,name);
                // contentValues.put(DBHelper.KEY_MAIL,email);
                database2.insert(DBHelper.TABLE_CONTACTS3,null,contentValues);

                break;
            case R.id.list_of_counterparties:
             //   Intent intent2 = new Intent(this, AddMatetialsAndServices.class);
              //  startActivity(intent2);
                break;
            case R.id.delete_counterparty:
              //  Intent intent3 = new Intent(this, WriteOffMat.class);
               // startActivity(intent3);
                break;

            default:
                break;
        }
    }
}*/