package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    EditText etName, etEmail,mDisplayDate, editTextPrice, editTextSum;
    DBHelper dbHelper2;
    Spinner spinner;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    CalendarView calender;
    EditText eTxt;
   // DBHelper databaseHelper;
   private int sNDS = 1;
    private int sSch = 3;
    SQLiteDatabase db;
    private Spinner spinnerNDS;
    private Spinner spinnerSch;
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
        editTextPrice=(EditText) findViewById(R.id.editTextPrice);
        editTextSum=(EditText) findViewById(R.id.editTextSum);

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
        spinnerNDS= (Spinner) findViewById(R.id.spinner2);
        spinnerSch= (Spinner) findViewById(R.id.spinnerSch);

        setupSpinner();
        setupSpinnerSch();
    }
    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerNDS.setAdapter(genderSpinnerAdapter);
        spinnerNDS.setSelection(1);

        spinnerNDS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.without_NDS))) {
                        sNDS = 0; // Без НДС
                    } else if (selection.equals(getString(R.string.twenty_percent))) {
                        sNDS = 1; // 20%
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sNDS = 1; // Unknown
            }
        });
    }
    private void setupSpinnerSch() {

        ArrayAdapter SchSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_account_options, android.R.layout.simple_spinner_item);

        SchSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerSch.setAdapter(SchSpinnerAdapter);
        spinnerSch.setSelection(3);

        spinnerSch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.materials))) {
                        sSch = 0; // Материалы
                    } else if (selection.equals(getString(R.string.spare_parts))) {
                        sSch = 1; // Запасные части
                    }else if (selection.equals(getString(R.string.coveralls))) {
                        sSch = 2; // Спецодежда
                    }else if (selection.equals(getString(R.string.inventory))) {
                        sSch = 3; // Инвентарь и хозяйственные принадлежности
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sSch = 3; // Unknown
            }
        });
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
        String date = mDisplayDate.getText().toString();
       // String counter = mDisplayDate.getText().toString();//???
        String sum = editTextSum.getText().toString();
        String price = editTextPrice.getText().toString();
       // String nds = textNDS.getText().toString();
      //  String account = sSch.getText().toString();


        SQLiteDatabase database=dbHelper2.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (view.getId()){
            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_NAME,name);
                contentValues.put(DBHelper.KEY_MAIL,email);
                contentValues.put(DBHelper.KEY_DATE, date);
                contentValues.put(DBHelper.KEY_SUM,sum);
                contentValues.put(DBHelper.KEY_PRICE,price);
                contentValues.put(DBHelper.KEY_NDS,sNDS);
                contentValues.put(DBHelper.KEY_ACCOUNT,sSch);


                database.insert(DBHelper.TABLE_CONTACTS,null,contentValues);

                break;
            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);

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
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name - " + cursor.getString(nameIndex) + ", email = " + cursor.getString(emailIndex)+
                              " date-"+  cursor.getString(dateIndex)+" sum-"+cursor.getString(sumIndex)+cursor.getString(priceIndex)+" nds-"+cursor.getString(ndsIndex)
                               +" sch-" +cursor.getString(schIndex));
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