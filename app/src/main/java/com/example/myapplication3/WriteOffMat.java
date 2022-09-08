package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.lang.reflect.Array;

import static com.example.myapplication3.DBHelper.KEY_DATE;
import static com.example.myapplication3.DBHelper.KEY_NAME;
import static com.example.myapplication3.DBHelper.KEY_PRICE;
import static com.example.myapplication3.DBHelper.KEY_SUM;

public class WriteOffMat extends AppCompatActivity {


    private ListView userList;
    private DBHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor userCursor;
    private SimpleCursorAdapter userAdapter;
   // private SimpleCursorAdapter userAdapter2;


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
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_MAT, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{KEY_DATE,KEY_NAME,KEY_SUM,KEY_PRICE};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, R.layout.four,
                userCursor, headers, new int[]{R.id.text_date,R.id.text_mail,R.id.text_sum,R.id.text_price}, 0);
        userList.setAdapter(userAdapter);


        //String[] info = {"дата","наименование","количество","стоимость"};
        //userAdapter = new SimpleCursorAdapter(this, R.layout.four,
          //      userCursor, info, new int[]{R.id.text_date,R.id.text_mail,R.id.text_sum,R.id.text_price}, 0);
        //userList.setAdapter(userAdapter);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.four, []{R.id.text_date,R.id.text_mail,R.id.text_sum,R.id.text_price}, info);
                //this, R.layout.four,
              //  userCursor, info, new int[]{R.id.text_date,R.id.text_mail,R.id.text_sum,R.id.text_price}, 0);
      //  userList.setAdapter(adapter);
    }

    // по нажатию на кнопку запускаем UserActivity для добавления данных
   // public void add(View view) {
   //     Intent intent = new Intent(this, SecondScreenWOM.class);
    //    startActivity(intent);
   // }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }



}