package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddMatetialsAndServices extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead, btnClear;
    EditText etName, etEmail,mDisplayDate;
    DBHelper dbHelper2;
    Spinner spinner;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    CalendarView calender;
    EditText eTxt;
   // DBHelper databaseHelper;
    SQLiteDatabase db;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

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

        mDisplayDate = (EditText) findViewById(R.id.editDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddMatetialsAndServices.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);

            }
        };
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