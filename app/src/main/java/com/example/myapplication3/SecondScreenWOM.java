package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import static com.example.myapplication3.DBHelper.TABLE_CONTACTS;

public class SecondScreenWOM extends AppCompatActivity {


    TextView nameBox;
    EditText yearBox;
    Button delButton;
    Button saveButton;
    Dialog dialog;
    DBHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    SimpleCursorAdapter userAdapter;
    ListView userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen_wom);


        nameBox = findViewById(R.id.name);
        //yearBox = findViewById(R.id.year);
        delButton = findViewById(R.id.deleteButton);

        sqlHelper = new DBHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
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
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
            /*
            //получаем данные из бд в виде курсора
            userCursor =  db.rawQuery("select * from "+ DBHelper.TABLE_CONTACTS, null);
            // определяем, какие столбцы из курсора будут выводиться в ListView
            String[] headers = new String[] {DBHelper.KEY_ID, DBHelper.KEY_NAME ,DBHelper.KEY_MAIL , DBHelper.KEY_CO ,DBHelper.KEY_SUM ,DBHelper.KEY_DATE,
                    DBHelper.KEY_PRICE , DBHelper.KEY_NDS ,DBHelper.KEY_ACCOUNT};
            // создаем адаптер, передаем в него курсор
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                    userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2,android.R.id.text3}, 0);
            nameBox.setText("Найдено элементов: " +  userCursor.getCount());
            userList.setAdapter(userAdapter);
            // получаем элемент по id из бд*/
            /*
            userCursor = db.rawQuery("select * from " + DBHelper.TABLE_CONTACTS + " where " +
                    DBHelper.KEY_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            //yearBox.setText(String.valueOf(userCursor.getInt(2)));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);*/
        }
    }


    public void delete(View view){
        db.delete(TABLE_CONTACTS, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, WriteOffMat.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }



}