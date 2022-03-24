package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddMatetialsAndServices extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail;
    DBHelper dbHelper2;
    Spinner spinner;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
   // DBHelper databaseHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matetials_and_services);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName=(EditText) findViewById(R.id.etName);
        etEmail=(EditText) findViewById(R.id.etEmail);

         dbHelper2 = new DBHelper(this);

        //Spinner spinner = (Spinner)findViewById(R.id.spinner);
      // Spinner spinner = findViewById(R.id.spinner);
      //  String selected = spinner.getSelectedItem().toString();
       // Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();

       // dbHelper2 = DBHelper.getReadableDatabase();
       // Cursor cursor = dbHelper2.query("mytable", new String[]{"_id", "name"}, null, null, null, null, null);
      //  Spinner = (Spinner)findViewById(R.id.spinner);
       // SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, сursor, new String[] {"name"}, new int[] {android.R.id.text1});
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // mSpinner.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = dbHelper2.getReadableDatabase();
        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_CONTACTS3, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DBHelper.KEY_NAME2};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        spinner.setAdapter(userAdapter);
    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        SQLiteDatabase database=dbHelper2.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (view.getId()){
            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_NAME,name);
                contentValues.put(DBHelper.KEY_MAIL,email);

                database.insert(DBHelper.TABLE_CONTACTS,null,contentValues);

                break;
            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);

                if (cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name - " + cursor.getString(nameIndex) + ", email = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                }else
                    Log.d("mLog","0 rows");

                cursor.close();
                break;

            case R.id.btnClear:
database.delete(DBHelper.TABLE_CONTACTS,null,null);
                break;
        }
        dbHelper2.close();
    }
}