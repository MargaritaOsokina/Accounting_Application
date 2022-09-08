package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import static com.example.myapplication3.DBHelper.KEY_CRED;
import static com.example.myapplication3.DBHelper.KEY_DEB;
import static com.example.myapplication3.DBHelper.TABLE_COUNTERPARTIES;

public class OSV extends AppCompatActivity {

   private ListView userList;
    private DBHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor userCursor;

    SimpleCursorAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osv);

        userList = findViewById(R.id.list);
      //  userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           // @Override
         //   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          //      Intent intent = new Intent(getApplicationContext(), OSV.class);
          //      intent.putExtra("id", id);
           //     startActivity(intent);
           // }
       // });

        databaseHelper = new DBHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + TABLE_COUNTERPARTIES, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DBHelper.KEY_CO2, KEY_DEB, KEY_CRED};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, R.layout.three,
                userCursor, headers, new int[]{R.id.text_view_member_id, R.id.text_view_name, R.id.text_view_phone}, 0);
        userList.setAdapter(userAdapter);


    }


    // по нажатию на кнопку запускаем UserActivity для добавления данных
   // public void add(View view) {
   //     Intent intent = new Intent(this, OSV.class);
   //     startActivity(intent);
    //}

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }



}