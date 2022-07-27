package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditPatient extends AppCompatActivity {

    Spinner username;
    EditText first;
    EditText last;
    EditText email;
    EditText password;

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient);
        DataBaseOpenHelper db = new DataBaseOpenHelper(EditPatient.this);
        List<String> usernames = db.getUsernames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EditPatient.this, android.R.layout.simple_spinner_dropdown_item, usernames);
        username = findViewById(R.id.spUsername);
        username.setAdapter(adapter);
        username.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(EditPatient.this);
                first = findViewById(R.id.etFirstName2);
                last = findViewById(R.id.etLastNsme2);
                email = findViewById(R.id.etEmail2);
                password = findViewById(R.id.etPassword2);
                List<PatientModel> patientFromUsername = dataBaseOpenHelper.getPatientFromUsername((String) parent.getItemAtPosition(position));
                first.setText(patientFromUsername.get(0).getFirstName());
                last.setText(patientFromUsername.get(0).getLastName());
                email.setText(patientFromUsername.get(0).getEmail());
                password.setText(patientFromUsername.get(0).getPassword());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                first = findViewById(R.id.etFirstName2);
                last = findViewById(R.id.etLastNsme2);
                email = findViewById(R.id.etEmail2);
                password = findViewById(R.id.etPassword2);
                first.getText().clear();
                last.getText().clear();
                email.getText().clear();
                password.getText().clear();
            }
        });

        Button button = findViewById(R.id.btnReg2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = findViewById(R.id.etFirstName2);
                last = findViewById(R.id.etLastNsme2);
                email = findViewById(R.id.etEmail2);
                password = findViewById(R.id.etPassword2);

                if (first.getText().toString().contains("First Name") || first.getText().toString().isEmpty()) {
                    Toast.makeText(EditPatient.this, "First Name should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!first.getText().toString().matches("[A-Za-z]*")) {
                    Toast.makeText(EditPatient.this, "First Name should only contain letters", Toast.LENGTH_SHORT).show();
                } else if (last.getText().toString().contains("Last Name") || last.getText().toString().isEmpty()) {
                    Toast.makeText(EditPatient.this, "Last Name should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!last.getText().toString().matches("[A-za-z]*")) {
                    Toast.makeText(EditPatient.this, "Last Name should only contain letters", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().contains("Email") || email.getText().toString().isEmpty()) {
                    Toast.makeText(EditPatient.this, "Email should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!emailValidator(email.getText().toString())) {
                    Toast.makeText(EditPatient.this, "Email not valid", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().contains("Password") || password.getText().toString().isEmpty()) {
                    Toast.makeText(EditPatient.this, "Password should not be empty", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().length() <= 6) {
                    Toast.makeText(EditPatient.this, "Password should be longer than 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    PatientModel patientModel = new PatientModel(first.getText().toString(), last.getText().toString(), email.getText().toString(), (String) username.getSelectedItem(), password.getText().toString());
                    DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(EditPatient.this);
                    boolean b = dataBaseOpenHelper.updatePatient(patientModel);
                    if (!b) {
                        Toast.makeText(EditPatient.this, "Edit failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditPatient.this, "Edit Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button button1 = findViewById(R.id.btnM);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditPatient.this, ManagePatients.class);
                startActivity(intent);
            }
        });
    }
}