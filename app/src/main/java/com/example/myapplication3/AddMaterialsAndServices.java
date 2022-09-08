package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

import static com.example.myapplication3.DBHelper.KEY_CO;
import static com.example.myapplication3.DBHelper.KEY_DEB;
import static com.example.myapplication3.DBHelper.KEY_ID;
import static com.example.myapplication3.DBHelper.KEY_NAME;
import static com.example.myapplication3.DBHelper.KEY_CO2;
import static com.example.myapplication3.DBHelper.TABLE_COUNTERPARTIES;

public class AddMaterialsAndServices extends AppCompatActivity implements View.OnClickListener {

   private Button btnAdd;
    private EditText etName, etNumber,mDisplayDate, editTextPrice, editTextSum;
    private  DBHelper dbHelper2;
    private  Cursor userCursor;
    private  SimpleCursorAdapter userAdapter;
    private  Cursor   itemCu;
    private  String itemSch ="";
    private  String itemNDS ="";
    private  String itemK="";
    private  SQLiteDatabase db;
    private Spinner spinnerNDS;
    private Spinner spinnerSch;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matetials_and_services);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);



        etName=(EditText) findViewById(R.id.etName);
        etNumber =(EditText) findViewById(R.id.etEmail);
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

                DatePickerDialog dialog = new DatePickerDialog(AddMaterialsAndServices.this,
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

    public void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerNDS.setAdapter(genderSpinnerAdapter);
        spinnerNDS.setSelection(1);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                itemNDS = (String)parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinnerNDS.setOnItemSelectedListener(itemSelectedListener);
    }
    public void setupSpinnerSch() {

        ArrayAdapter SchSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_account_options, android.R.layout.simple_spinner_item);

        SchSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerSch.setAdapter(SchSpinnerAdapter);
        spinnerSch.setSelection(3);
       // TextView selection = findViewById(R.id.textCou);

            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Получаем выбранный объект
                     itemSch = (String)parent.getItemAtPosition(position);
                  //  selection.setText(itemSch);

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
        // открываем подключение
        db = dbHelper2.getReadableDatabase();
        Spinner spinner = (Spinner)findViewById(R.id.spinner);///здесь

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + TABLE_COUNTERPARTIES, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{KEY_CO2};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        spinner.setAdapter(userAdapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @SuppressLint("Range")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                itemCu = (Cursor) parent.getItemAtPosition(position);
                 itemK = itemCu.getString(itemCu.getColumnIndex(KEY_CO2));

            }

           // @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    public void onClick(View view) {

        double NDSprice;

        String name = etName.getText().toString();
        String number = etNumber.getText().toString();
        String date = mDisplayDate.getText().toString();
        String sum = editTextSum.getText().toString();
        String price = editTextPrice.getText().toString();
if (Objects.equals(itemNDS,"20%")){
     NDSprice = (Integer.valueOf(price))*1.2;
}else NDSprice = Integer.valueOf(price);



        SQLiteDatabase database=dbHelper2.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch (view.getId()) {

            case R.id.btnAdd:
                if((etName.getText().toString().isEmpty())||(etNumber.getText().toString().isEmpty())
                        ||(mDisplayDate.getText().toString().isEmpty())||(editTextSum.getText().toString().isEmpty())
                        ||(editTextPrice.getText().toString().isEmpty())||(itemK.isEmpty()))
                {
                    Toast.makeText(AddMaterialsAndServices.this, "Заполните все поля", Toast.LENGTH_SHORT).show();

                }else {
                    contentValues.put(KEY_NAME, name);
                    contentValues.put(KEY_CO, String.valueOf(itemK));
                    contentValues.put(DBHelper.KEY_NUMBER, number);
                    contentValues.put(DBHelper.KEY_DATE, date);
                    contentValues.put(DBHelper.KEY_SUM, sum);
                    contentValues.put(DBHelper.KEY_PRICE, NDSprice);
                    contentValues.put(DBHelper.KEY_NDS, itemSch);
                    contentValues.put(DBHelper.KEY_ACCOUNT, itemNDS);


                    database.insert(DBHelper.TABLE_MAT, null, contentValues);
                    userCursor = db.rawQuery("select * from " + TABLE_COUNTERPARTIES + " where " +
                            KEY_CO2 + "=?", new String[]{itemK});
                    userCursor.moveToFirst();


                    int debColumnIndex = userCursor.getColumnIndex(KEY_DEB);
                    String currentDeb = userCursor.getString(debColumnIndex);
                    ContentValues cv = new ContentValues();

                        cv.put("debit", Double.valueOf(currentDeb) + (Double.valueOf(sum) * NDSprice));

                    // обновляем по TABLE_CONTACTS3
                    db.update("allCounterparties", cv, "co2 = ?",
                            new String[]{itemK});
                    goHome();
                }
                break;
/*
            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_MAT,null,null,null,null,null,null);

                if (cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(KEY_ID);
                    int nameIndex = cursor.getColumnIndex(KEY_NAME);
                    int numberIndex = cursor.getColumnIndex(DBHelper.KEY_NUMBER);
                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                    int sumIndex = cursor.getColumnIndex(DBHelper.KEY_SUM);
                    int priceIndex = cursor.getColumnIndex(DBHelper.KEY_PRICE);
                    int ndsIndex = cursor.getColumnIndex(DBHelper.KEY_NDS);
                    int schIndex = cursor.getColumnIndex(DBHelper.KEY_ACCOUNT);
                    int counterparties = cursor.getColumnIndex(KEY_CO);

                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name - " + cursor.getString(nameIndex) + ", counterparties - " + cursor.getString(counterparties)+ ", number = " + cursor.getString(numberIndex)+
                              " date-"+  cursor.getString(dateIndex)+" sum-"+cursor.getString(sumIndex)+" price-"+cursor.getString(priceIndex)+" nds-"+cursor.getString(ndsIndex)
                               +" sch-" +cursor.getString(schIndex));
                    } while (cursor.moveToNext());
                }else
                    Log.d("mLog","0 rows");

                cursor.close();
                break;

            case R.id.btnClear:
database.delete(DBHelper.TABLE_MAT,null,null);
                break;*/
        }
        dbHelper2.close();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}