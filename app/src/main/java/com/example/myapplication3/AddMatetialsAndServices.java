package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    Cursor   itemCu;
    int noteID;
    String item ="";
    String itemSch="";
    String itemK="";
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

        TextView selection = findViewById(R.id.textCou);

        //spinnerSch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                itemSch = (String)parent.getItemAtPosition(position);
               // selection.setText(itemSch);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinnerNDS.setOnItemSelectedListener(itemSelectedListener);
    }
    private void setupSpinnerSch() {

        ArrayAdapter SchSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_account_options, android.R.layout.simple_spinner_item);

        SchSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerSch.setAdapter(SchSpinnerAdapter);
        spinnerSch.setSelection(3);
        TextView selection = findViewById(R.id.textCou);

        //spinnerSch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Получаем выбранный объект
                     item = (String)parent.getItemAtPosition(position);
                    selection.setText(item);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };

        spinnerSch.setOnItemSelectedListener(itemSelectedListener);
    }









    @Override
    public void onResume() {
        super.onResume();
        TextView selection = findViewById(R.id.textCou);
        // открываем подключение
        db = dbHelper2.getReadableDatabase();
        Spinner spinner = (Spinner)findViewById(R.id.spinner);///здесь

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DBHelper.TABLE_CONTACTS3, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DBHelper.KEY_NAME2};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        spinner.setAdapter(userAdapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @SuppressLint("Range")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
               // itemK = (String) parent.getItemAtPosition(position);
               // selection.setText(itemK);
                itemCu = (Cursor) parent.getItemAtPosition(position);
                 itemK = itemCu.getString(itemCu.getColumnIndex(DBHelper.KEY_NAME2));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinner.setOnItemSelectedListener(itemSelectedListener);
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
                Spinner mySpinner=(Spinner) findViewById(R.id.spinner);
                String text = mySpinner.getSelectedItem().toString();

                contentValues.put(DBHelper.KEY_NAME,name);
                contentValues.put(DBHelper.KEY_CO, String.valueOf(itemK));

                contentValues.put(DBHelper.KEY_MAIL,email);
                contentValues.put(DBHelper.KEY_DATE, date);
                contentValues.put(DBHelper.KEY_SUM,sum);
                contentValues.put(DBHelper.KEY_PRICE,price);
                contentValues.put(DBHelper.KEY_NDS,item);
                contentValues.put(DBHelper.KEY_ACCOUNT,itemSch);


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
                    int co = cursor.getColumnIndex(DBHelper.KEY_CO);

                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name - " + cursor.getString(nameIndex) + ", co - " + cursor.getString(co)+ ", email = " + cursor.getString(emailIndex)+
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