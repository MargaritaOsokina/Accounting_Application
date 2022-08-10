package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class OSV extends AppCompatActivity {


    ListView userList;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_off_mat);

        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SecondScreenWOM.class);
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
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_CONTACTS, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DBHelper.KEY_NAME};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        userList.setAdapter(userAdapter);
        int nameColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NAME);
        String currentName = userCursor.getString(nameColumnIndex);

      // nameBox.setText(currentName+"\n"+currentCity+"\n"+currentMail+"\n"+
        //        currentSum+"\n"+currentDate+"\n"+currentPrice+"\n"+currentNDS+"\n"+currentAccount);
    }

    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view) {
        Intent intent = new Intent(this, SecondScreenWOM.class);
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