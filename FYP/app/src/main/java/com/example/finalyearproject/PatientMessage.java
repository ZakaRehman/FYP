package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

public class PatientMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_message);

        Button button = findViewById(R.id.btnP);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientMessage.this, Patient.class);
                startActivity(intent);
            }
        });

        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(PatientMessage.this);
        List<String> allDoctors = dataBaseOpenHelper.getAllDoctors();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PatientMessage.this, android.R.layout.simple_list_item_1, allDoctors);
        ListView view = findViewById(R.id.lvDoctors);
        view.setAdapter(arrayAdapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = dataBaseOpenHelper.getEmailFromName((String) parent.getItemAtPosition(position));
                Intent intent = new Intent(PatientMessage.this, Messaging.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
}