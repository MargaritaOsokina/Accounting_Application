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

import static com.example.myapplication3.DBHelper.KEY_DEB;
import static com.example.myapplication3.DBHelper.TABLE_CONTACTS;
import static com.example.myapplication3.DBHelper.TABLE_CONTACTS3;

public class OSV extends AppCompatActivity {

    TextView nameBox;
    ListView userList;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    Cursor userCursorC;
    DBHelper sqlHelper;

    SimpleCursorAdapter userAdapter;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osv);

        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), OSV.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DBHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_CONTACTS3, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DBHelper.KEY_NAME2,KEY_DEB};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1,android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);
            // скрываем кнопку удаления
       // nameBox = findViewById(R.id.name);
        //yearBox = findViewById(R.id.year);

      //  sqlHelper = new DBHelper(this);
       // db = sqlHelper.getWritableDatabase();

       // Bundle extras = getIntent().getExtras();
       // if (extras != null) {
        //    userId = extras.getLong("id");
       // }
       // userId=1;

         //   userCursor = db.rawQuery("select * from " + TABLE_CONTACTS + " where " +
          //          DBHelper.KEY_NAME + "=?", new String[]{String.valueOf("mn")});

          //  userCursor.moveToFirst();
            // nameBox.setText(userCursor.getString(1));
          /*  int nameColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NAME);
            int counterpartiesColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_CO);
            int mailColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_MAIL);
            int sumColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_SUM);
            int dateColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_DATE);
            int priceColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_PRICE);
            int ndsColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NDS);
            int accountColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_ACCOUNT);

            String currentName = userCursor.getString(nameColumnIndex);
            String currentCity = userCursor.getString(counterpartiesColumnIndex);
            String currentMail = userCursor.getString(mailColumnIndex);
            String currentSum = userCursor.getString(sumColumnIndex);
            String currentDate = userCursor.getString(dateColumnIndex);
            String currentPrice = userCursor.getString(priceColumnIndex);
            String currentNDS = userCursor.getString(ndsColumnIndex);
            String currentAccount = userCursor.getString(accountColumnIndex);
        String[] headers = new String[]{DBHelper.KEY_NAME};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        userList.setAdapter(userAdapter);
        //nameBox.setText(currentName);

            //yearBox.setText(String.valueOf(userCursor.getInt(2)));
            userCursor.close();



        // если 0, то добавление
        /*
        userId=1;
        if (userId > 0) {
            userCursor = db.rawQuery("select * from " + TABLE_CONTACTS + " where " +
                    DBHelper.KEY_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            // nameBox.setText(userCursor.getString(1));
            int nameColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NAME);
            int counterpartiesColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_CO);
            int mailColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_MAIL);
            int sumColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_SUM);
            int dateColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_DATE);
            int priceColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_PRICE);
            int ndsColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NDS);
            int accountColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_ACCOUNT);

            String currentName = userCursor.getString(nameColumnIndex);
            String currentCity = userCursor.getString(counterpartiesColumnIndex);
            String currentMail = userCursor.getString(mailColumnIndex);
            String currentSum = userCursor.getString(sumColumnIndex);
            String currentDate = userCursor.getString(dateColumnIndex);
            String currentPrice = userCursor.getString(priceColumnIndex);
            String currentNDS = userCursor.getString(ndsColumnIndex);
            String currentAccount = userCursor.getString(accountColumnIndex);

            nameBox.setText(currentName+"\n"+currentCity+"\n"+currentMail+"\n"+
                    currentSum+"\n"+currentDate+"\n"+currentPrice+"\n"+currentNDS+"\n"+currentAccount);

            //yearBox.setText(String.valueOf(userCursor.getInt(2)));
            userCursor.close();
        }*/
        /*
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_CONTACTS, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DBHelper.KEY_CO,DBHelper.KEY_PRICE};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1,android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);*/
        /*
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        nameBox = findViewById(R.id.name);
        // если 0, то добавление
        if (userId > 0) {
            userCursor = db.rawQuery("select * from " + TABLE_CONTACTS + " where " +
                    DBHelper.KEY_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();

            userCursorC = db.rawQuery("select * from " + TABLE_CONTACTS3 + " where " +
                    DBHelper.KEY_ID + "=?", new String[]{String.valueOf(userId)});
            userCursorC.moveToFirst();
            int nameColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NAME);
            int nameColumnIndexC = userCursor.getColumnIndex(DBHelper.KEY_NAME2);

            String currentNameC = userCursorC.getString(nameColumnIndex);
            String currentName = userCursor.getString(nameColumnIndexC);

            if(currentNameC==currentName){
                nameBox.setText(currentName);

            }else  nameBox.setText(currentName);
            userCursor.close();
            userCursorC.close();
        }
       // int nameColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NAME);
        //String currentName = userCursor.getString(nameColumnIndex);

      // nameBox.setText(currentName+"\n"+currentCity+"\n"+currentMail+"\n"+
        //        currentSum+"\n"+currentDate+"\n"+currentPrice+"\n"+currentNDS+"\n"+currentAccount);
    */}

    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view) {
        Intent intent = new Intent(this, OSV.class);
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