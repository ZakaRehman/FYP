package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Patient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        Button button = findViewById(R.id.btnLogout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prf = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Patient.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button button1 = findViewById(R.id.btnBook);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient.this, Book.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.btnMessage);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient.this, PatientMessage.class);
                startActivity(intent);
            }
        });

        Button button3 = findViewById(R.id.btnView);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient.this, PatientView.class);
                startActivity(intent);
            }
        });

        Button button4 = findViewById(R.id.btnPrescrip);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient.this, ViewPrescriptions.class);
                startActivity(intent);
            }
        });
    }
}