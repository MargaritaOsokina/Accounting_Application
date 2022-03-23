package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener{


    Button btn_add_count;
    Button btn_add_mat_and_serv;
    Button btn_write_off_of_materials;
    Button btn_osv;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add_count = (Button) findViewById(R.id.add_counterparty);
        btn_add_count.setOnClickListener(this);

        btn_add_mat_and_serv = (Button) findViewById(R.id.add_materials_and_services);
        btn_add_mat_and_serv.setOnClickListener(this);

        btn_write_off_of_materials = (Button) findViewById(R.id.write_off_of_materials);
        btn_write_off_of_materials.setOnClickListener(this);

        btn_osv = (Button) findViewById(R.id.osv);
        btn_osv.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_counterparty:
                Intent intent = new Intent(this, CounterpartiesMenu.class);
                startActivity(intent);
                break;
            case R.id.add_materials_and_services:
                Intent intent2 = new Intent(this, AddMatetialsAndServices.class);
                startActivity(intent2);
                break;
            case R.id.write_off_of_materials:
                Intent intent3 = new Intent(this, WriteOffMat.class);
                startActivity(intent3);
                break;
            case R.id.osv:
                Intent intent4 = new Intent(this, OSV.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }
}