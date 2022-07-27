package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_appointments);

        Button button = findViewById(R.id.btnAdmin1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAppointments.this, Admin.class);
                startActivity(intent);
            }
        });

        Button button1 = findViewById(R.id.btnAddApp);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAppointments.this, AddAppointment.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.btnDelApp);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAppointments.this, DeleteAppointments.class);
                startActivity(intent);
            }
        });
        
    }
}