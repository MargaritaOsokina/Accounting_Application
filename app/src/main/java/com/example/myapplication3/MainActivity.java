package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener{


    private Button btnAddCount;
    private Button btnAddMatAndServ;
    private Button btnWriteOffOfMaterials;
    private Button btnOsv;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddCount = (Button) findViewById(R.id.add_counterparty);
        btnAddCount.setOnClickListener(this);

        btnAddMatAndServ = (Button) findViewById(R.id.add_materials_and_services);
        btnAddMatAndServ.setOnClickListener(this);

        btnWriteOffOfMaterials = (Button) findViewById(R.id.write_off_of_materials);
        btnWriteOffOfMaterials.setOnClickListener(this);

        btnOsv = (Button) findViewById(R.id.osv);
        btnOsv.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_counterparty:
                Intent intentCou = new Intent(this, CounterpartiesMenu.class);
                startActivity(intentCou);
                break;
            case R.id.add_materials_and_services:
                Intent intentMatAndServ = new Intent(this, AddMaterialsAndServices.class);
                startActivity(intentMatAndServ);
                break;
            case R.id.write_off_of_materials:
                Intent intentWriteOffMat = new Intent(this, WriteOffMat.class);
                startActivity(intentWriteOffMat);
                break;
            case R.id.osv:
                Intent intentOSV = new Intent(this, OSV.class);
                startActivity(intentOSV);
                break;
            default:
                break;
        }
    }
}