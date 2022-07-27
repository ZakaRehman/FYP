package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class ViewPrescriptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescriptions);

        Button button = findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPrescriptions.this, Patient.class);
                startActivity(intent);
            }
        });

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String username = pref.getString("username", null);
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(ViewPrescriptions.this);
        List<PrescriptionModel> prescriptions = dataBaseOpenHelper.getPrescriptions(username);
        ListView listView = findViewById(R.id.lvPre);
        if (prescriptions != null){
            ArrayAdapter<PrescriptionModel> arrayAdapter = new ArrayAdapter<>(ViewPrescriptions.this, android.R.layout.simple_spinner_dropdown_item, prescriptions);
            listView.setAdapter(arrayAdapter);
        }
    }
}