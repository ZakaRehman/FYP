package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class PatientView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view);

        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(PatientView.this);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        try {
            List<AppointmentModel> username = dataBaseOpenHelper.getPatientAppointments(pref.getString("username", null));
            if (username != null){
                ArrayAdapter<AppointmentModel>arrayAdapter = new ArrayAdapter<AppointmentModel>(PatientView.this, android.R.layout.simple_list_item_1, username);
                ListView lv = findViewById(R.id.lvAppointments);
                lv.setAdapter(arrayAdapter);
            }
        } catch (NullPointerException e){
            Toast.makeText(PatientView.this, "There are no appointments to view", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PatientView.this, Patient.class);
            startActivity(intent);
        }

        Button button = findViewById(R.id.btnPage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientView.this, Patient.class);
                startActivity(intent);
            }
        });
    }
}