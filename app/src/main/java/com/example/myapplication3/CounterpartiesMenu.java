package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;


public class CounterpartiesMenu extends AppCompatActivity {


    private ListView counterpartiesList;
    private DBHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor userCursor;
    private SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterparties_menu);

        counterpartiesList = findViewById(R.id.list);


        databaseHelper = new DBHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_COUNTERPARTIES, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DBHelper.KEY_CO2};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        counterpartiesList.setAdapter(userAdapter);
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
