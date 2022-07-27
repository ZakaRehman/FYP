package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class CreatePrescription extends AppCompatActivity {

    DataBaseOpenHelper dataBaseOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prescription);

        dataBaseOpenHelper = new DataBaseOpenHelper(CreatePrescription.this);
        List<String> usernames = dataBaseOpenHelper.getUsernames();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CreatePrescription.this, android.R.layout.simple_spinner_dropdown_item, usernames);
        Spinner spinner = findViewById(R.id.spPatient);
        spinner.setAdapter(arrayAdapter);

        Button button = findViewById(R.id.btnAddPre);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = findViewById(R.id.etPrescriptionName);
                EditText dose = findViewById(R.id.etDose);
                if (name.getText().toString().equals("Medicine name") || name.getText().toString().length() == 0){
                    Toast.makeText(CreatePrescription.this, "Medicine name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!name.getText().toString().matches("[A-Za-z]*")){
                    Toast.makeText(CreatePrescription.this, "Medicine name has to be letters only", Toast.LENGTH_SHORT).show();
                } else if (dose.getText().toString().equals("Dosage (mg)") || dose.getText().toString().length() == 0){
                    Toast.makeText(CreatePrescription.this, "Dosage cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!dose.getText().toString().matches("[0-9]*")){
                    Toast.makeText(CreatePrescription.this, "Dosage must only contain numbers", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                    String doc = pref.getString("username", null);
                    PrescriptionModel prescriptionModel = new PrescriptionModel(name.getText().toString(), dose.getText().toString(), spinner.getSelectedItem().toString(), doc);
                    boolean b = dataBaseOpenHelper.addPrescription(prescriptionModel);
                    if (b){
                        Toast.makeText(CreatePrescription.this, "Prescription created successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreatePrescription.this, "Prescription not created successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button button1 = findViewById(R.id.button8);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePrescription.this, Doctor.class);
                startActivity(intent);
            }
        });
    }
}