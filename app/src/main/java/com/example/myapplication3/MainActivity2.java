package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {


    private EditText nameBox;
    private DBHelper dbHelper2;
    //private Button delButton;
    private Button saveButton;
    private DBHelper sqlHelper;
    private SQLiteDatabase db;
    private Cursor userCursor;
    private long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nameBox = findViewById(R.id.name);
        //delButton = findViewById(R.id.deleteButton);
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
            userCursor = db.rawQuery("select * from " + DBHelper.TABLE_COUNTERPARTIES + " where " +
                    DBHelper.KEY_ID2 + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            //delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){
        dbHelper2 = new DBHelper(this);
        SQLiteDatabase database=dbHelper2.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_COUNTERPARTIES,null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID2);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_CO2);
            int numberIndex = cursor.getColumnIndex(DBHelper.KEY_DEB);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_CRED);


            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name - " + cursor.getString(nameIndex) + ", deb - " + cursor.getString(numberIndex)+ ", cred = " + cursor.getString(dateIndex)
                       );
            } while (cursor.moveToNext());
        }else
            Log.d("mLog","0 rows");

        cursor.close();

        ContentValues cv = new ContentValues();
        if
        (nameBox.getText().toString().equals(""))
        {
            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.show(getSupportFragmentManager(), "custom");
        }
        else
        {
            cv.put(DBHelper.KEY_CO2, nameBox.getText().toString());
            cv.put(DBHelper.KEY_DEB, "0");
            cv.put(DBHelper.KEY_CRED, "0");

            if (userId > 0) {
                db.update(DBHelper.TABLE_COUNTERPARTIES, cv, DBHelper.KEY_ID2 + "=" + userId, null);
            } else {
                db.insert(DBHelper.TABLE_COUNTERPARTIES, null, cv);
            }
            goHome();        }
    }
    /*
    public void delete(View view){
        db.delete(DBHelper.TABLE_COUNTERPARTIES, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }*/
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, CounterpartiesMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
