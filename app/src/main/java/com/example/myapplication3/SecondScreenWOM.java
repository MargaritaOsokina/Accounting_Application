package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.myapplication3.DBHelper.KEY_CRED;
import static com.example.myapplication3.DBHelper.TABLE_MAT;
import static com.example.myapplication3.DBHelper.TABLE_COUNTERPARTIES;

public class SecondScreenWOM extends AppCompatActivity {


    TextView nameBox;
    Button delButton;
    DBHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    Cursor userCursorMat;
    Cursor userCursorCou;

    long userId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen_wom);


        nameBox = findViewById(R.id.name);
        delButton = findViewById(R.id.deleteButton);

        sqlHelper = new DBHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        // если 0, то добавление
        if (userId > 0) {
            userCursor = db.rawQuery("select * from " + TABLE_MAT + " where " +
                    DBHelper.KEY_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            int nameColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NAME);
            int counterpartyColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_CO);
            int numberColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NUMBER);
            int sumColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_SUM);
            int dateColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_DATE);
            int priceColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_PRICE);
            int ndsColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_NDS);
            int accountColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_ACCOUNT);

            String currentName = userCursor.getString(nameColumnIndex);
            String currentCounterparty = userCursor.getString(counterpartyColumnIndex);
            String currentNumber = userCursor.getString(numberColumnIndex);
            String currentSum = userCursor.getString(sumColumnIndex);
            String currentDate = userCursor.getString(dateColumnIndex);
            String currentPrice = userCursor.getString(priceColumnIndex);
            String currentNDS = userCursor.getString(ndsColumnIndex);
            String currentAccount = userCursor.getString(accountColumnIndex);

            nameBox.setText("Дата: "+currentDate+"\nКонтрагент: "+currentCounterparty+"\nНомер: "+currentNumber+
                    "\nНаименование: "+currentName+ "\nКоличество: "+currentSum+"\nЦена (тариф) за единицу измерения: "+currentPrice+
                    "\nНДС: "+ currentNDS+"\nСчет: "+currentAccount);


            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }


    public void delete(View view){

        userCursorMat = db.rawQuery("select * from " + TABLE_MAT + " where " +
                DBHelper.KEY_ID + "=?", new String[]{String.valueOf(userId)});
        userCursorMat.moveToFirst();
        int counterpartyColumnIndex = userCursor.getColumnIndex(DBHelper.KEY_CO);
        int sumColumnIndex = userCursorMat.getColumnIndex(DBHelper.KEY_SUM);
        int priceColumnIndex = userCursorMat.getColumnIndex(DBHelper.KEY_PRICE);

        String currentCounterparty = userCursorMat.getString(counterpartyColumnIndex);
        String currentSum = userCursorMat.getString(sumColumnIndex);
        String currentPrice = userCursorMat.getString(priceColumnIndex);


        userCursorCou = db.rawQuery("select * from " + TABLE_COUNTERPARTIES + " where " +
                DBHelper.KEY_CO2 + "=?", new String[]{currentCounterparty});
        userCursorCou.moveToFirst();

        int credColumnIndex = userCursorCou.getColumnIndex(KEY_CRED);
        String currentCred = userCursorCou.getString(credColumnIndex);

        // подготовим значения для обновления
        ContentValues cv = new ContentValues();
        cv.put("credit", Integer.valueOf(currentCred)+(Integer.valueOf(currentSum)*Integer.valueOf(currentPrice)));

        // обновляем по TABLE_CONTACTS3
        db.update("allCounterparties", cv, "co2 = ?",
                new String[] { currentCounterparty });
        db.delete(TABLE_MAT, "_id = ?", new String[]{String.valueOf(userId)});


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