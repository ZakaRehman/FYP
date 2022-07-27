package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class EditDoctor extends AppCompatActivity {

    EditText fName;
    EditText lName;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor);
        Spinner spEdit = findViewById(R.id.spinner);
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(EditDoctor.this);
        List<String> doctorEmails = dataBaseOpenHelper.getDoctorEmails();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(EditDoctor.this, android.R.layout.select_dialog_singlechoice, doctorEmails);
        spEdit.setAdapter(arrayAdapter);
        spEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DoctorModel doctorFromEmail = dataBaseOpenHelper.getDoctorFromEmail((String) parent.getItemAtPosition(position));
                fName = findViewById(R.id.etFName);
                lName = findViewById(R.id.etLName);
                password = findViewById(R.id.etPasswordEdit);
                fName.setText(doctorFromEmail.getFirstName());
                lName.setText(doctorFromEmail.getLastName());
                password.setText(doctorFromEmail.getPassword());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                fName = findViewById(R.id.etFName);
                lName = findViewById(R.id.etLName);
                password = findViewById(R.id.etPasswordEdit);
                fName.getText().clear();
                lName.getText().clear();
                password.getText().clear();
            }
        });

        Button button = findViewById(R.id.btnEdit);
        Button button1 = findViewById(R.id.btnMan);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditDoctor.this, ManageDoctors.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = findViewById(R.id.etFName);
                lName = findViewById(R.id.etLName);
                password = findViewById(R.id.etPasswordEdit);

                if (fName.getText().toString().equals("First Name") || fName.getText().toString().length() == 0){
                    Toast.makeText(EditDoctor.this, "First name should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!fName.getText().toString().matches("[A-za-z]*")){
                    Toast.makeText(EditDoctor.this, "First name should only have letters", Toast.LENGTH_SHORT).show();
                } else if (lName.getText().toString().equals("Last Name") || lName.getText().toString().length() == 0){
                    Toast.makeText(EditDoctor.this, "Last name should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!lName.getText().toString().matches("[A-za-z]*")){
                    Toast.makeText(EditDoctor.this, "Last name should only have letters", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals("Password") || password.getText().toString().length() == 0){
                    Toast.makeText(EditDoctor.this, "Password should not be empty", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().length() < 6){
                    Toast.makeText(EditDoctor.this, "Password should be greater than 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    String first = fName.getText().toString().toLowerCase();
                    String cap = first.substring(0, 1).toUpperCase() + first.substring(1);
                    String last = lName.getText().toString().toLowerCase();
                    String cap1 = last.substring(0, 1).toUpperCase() + last.substring(1);
                    boolean b = dataBaseOpenHelper.editDoctor((String) spEdit.getSelectedItem(), cap, cap1, password.getText().toString());
                    if (b){
                        Toast.makeText(EditDoctor.this, "Edit Successful", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(EditDoctor.this, "Edit Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}