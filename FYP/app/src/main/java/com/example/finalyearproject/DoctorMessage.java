package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

public class DoctorMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_message);

        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(DoctorMessage.this);
        List<String> usernames = dataBaseOpenHelper.getUsernames();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(DoctorMessage.this, android.R.layout.simple_spinner_dropdown_item, usernames);
        ListView spinner = findViewById(R.id.spUsers);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                String email = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(DoctorMessage.this, Messaging.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorMessage.this, Doctor.class);
                startActivity(intent);
            }
        });
    }
}