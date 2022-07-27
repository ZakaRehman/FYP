package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class DoctorView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view);

        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(DoctorView.this);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        try {
            List<AppointmentModel> username = dataBaseOpenHelper.getDoctorAppointments(pref.getString("username", null));
            if (username != null){
                ArrayAdapter<AppointmentModel>arrayAdapter = new ArrayAdapter<>(DoctorView.this, android.R.layout.simple_list_item_1, username);
                ListView list = findViewById(R.id.view);
                list.setAdapter(arrayAdapter);
            }
        } catch (NullPointerException e){
            Toast.makeText(DoctorView.this, "there are no appointments to view", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DoctorView.this, Doctor.class);
            startActivity(intent);
        }

        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorView.this, Doctor.class);
                startActivity(intent);
            }
        });
    }
}