package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class OSV extends AppCompatActivity {
    DBHelper dbHelper2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osv);
        dbHelper2 = new DBHelper(this);
        SQLiteDatabase database=dbHelper2.getWritableDatabase();
        String[] projection = {
                DBHelper.KEY_NAME };
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);

        TextView displayTextView = (TextView) findViewById(R.id.textView1);

        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int sumIndex = cursor.getColumnIndex(DBHelper.KEY_SUM);
            int priceIndex = cursor.getColumnIndex(DBHelper.KEY_PRICE);
            int ndsIndex = cursor.getColumnIndex(DBHelper.KEY_NDS);
            int schIndex = cursor.getColumnIndex(DBHelper.KEY_ACCOUNT);

            do {

                int currentID = cursor.getColumnIndex(DBHelper.KEY_ID);
                int currentName = cursor.getColumnIndex(DBHelper.KEY_NAME);
                int currentCity = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                int currentGender = cursor.getColumnIndex(DBHelper.KEY_DATE);
                int currentAge = cursor.getColumnIndex(DBHelper.KEY_SUM);
                int wqw = cursor.getColumnIndex(DBHelper.KEY_PRICE);
                int ee = cursor.getColumnIndex(DBHelper.KEY_NDS);
                int ss = cursor.getColumnIndex(DBHelper.KEY_ACCOUNT);
                // Выводим значения каждого столбца
                displayTextView.append(("ID = " + cursor.getInt(idIndex) + ",\n name - " + cursor.getString(nameIndex) + ",\n email = " + cursor.getString(emailIndex)+
                        " \n date-"+  cursor.getString(dateIndex)+" \n sum-"+cursor.getString(sumIndex)+cursor.getString(priceIndex)+"\n nds-"+cursor.getString(ndsIndex)
                        +" \n sch-" +cursor.getString(schIndex)+"\n\n"));
            } while (cursor.moveToNext());
        }else
            Log.d("mLog","0 rows");

        cursor.close();
    }

}