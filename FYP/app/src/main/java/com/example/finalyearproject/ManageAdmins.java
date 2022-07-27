package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class ManageAdmins extends AppCompatActivity {

    Spinner spinner;
    DataBaseOpenHelper dataBaseOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admins);

        Button button = findViewById(R.id.btnPromote);
        Button button1 = findViewById(R.id.btnDemote);
        Button button2 = findViewById(R.id.btnAd);

        dataBaseOpenHelper = new DataBaseOpenHelper(ManageAdmins.this);
        List<String> allDoctors1 = dataBaseOpenHelper.getAllDoctors1();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ManageAdmins.this, android.R.layout.simple_spinner_dropdown_item, allDoctors1);
        spinner = findViewById(R.id.spAllDoctors);
        spinner.setAdapter(arrayAdapter);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAdmins.this, Admin.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorFromName = dataBaseOpenHelper.getEmailFromName(spinner.getSelectedItem().toString());
                DoctorModel doctorFromEmail = dataBaseOpenHelper.getDoctorFromEmail(doctorFromName);
                if (doctorFromEmail.getAdministrator() == 1){
                    Toast.makeText(ManageAdmins.this, "This user is already an administrator", Toast.LENGTH_SHORT).show();
                } else{
                    boolean b = dataBaseOpenHelper.promoteDoctor(doctorFromEmail);
                    if (b){
                        Toast.makeText(ManageAdmins.this, "Promotion Successful", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(ManageAdmins.this, "Promotion Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorFromName = dataBaseOpenHelper.getEmailFromName(spinner.getSelectedItem().toString());
                DoctorModel doctorFromEmail = dataBaseOpenHelper.getDoctorFromEmail(doctorFromName);
                if (doctorFromEmail.getEmail().equals("zaka2008@live.co.uk")){
                    Toast.makeText(ManageAdmins.this, "This user cannot be demoted", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (doctorFromEmail.getAdministrator() == 0){
                    Toast.makeText(ManageAdmins.this, "This user cannot be demoted any further", Toast.LENGTH_SHORT).show();
                } else{
                    boolean b = dataBaseOpenHelper.demoteDoctor(doctorFromEmail);
                    if (b){
                        Toast.makeText(ManageAdmins.this, "Demotion Successful", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(ManageAdmins.this, "Demotion Failed", Toast.LENGTH_SHORT).show();
                    }
                } 
            }
        });
    }
}