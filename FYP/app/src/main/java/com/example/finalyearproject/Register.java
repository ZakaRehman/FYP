package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText fName;
    EditText lName;
    EditText email;
    EditText username;
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
        setContentView(R.layout.activity_register);

        Button button = findViewById(R.id.btnReg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = findViewById(R.id.etFirstName);
                lName = findViewById(R.id.etLastNsme);
                email = findViewById(R.id.etEmail);
                username = findViewById(R.id.etUsername);
                password = findViewById(R.id.etPassword);

                if (fName.getText().toString().contains("First Name") || fName.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "First Name should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!fName.getText().toString().matches("[A-Za-z]*")) {
                    Toast.makeText(Register.this, "First Name should only contain letters", Toast.LENGTH_SHORT).show();
                } else if (lName.getText().toString().contains("Last Name") || lName.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Last Name should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!lName.getText().toString().matches("[A-za-z]*")) {
                    Toast.makeText(Register.this, "Last Name should only contain letters", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().contains("Email") || email.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Email should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!emailValidator(email.getText().toString())) {
                    Toast.makeText(Register.this, "Email not valid", Toast.LENGTH_SHORT).show();
                } else if (username.getText().toString().contains("Username") || username.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Username should not be empty", Toast.LENGTH_SHORT).show();
                } else if (!username.getText().toString().matches("[A-Za-z0-9]*")) {
                    Toast.makeText(Register.this, "Special Characters not allowed in username", Toast.LENGTH_SHORT).show();
                } else if (username.getText().toString().length() <= 6) {
                    Toast.makeText(Register.this, "Username should be longer than 6 characters", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().contains("Password") || password.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Password should not be empty", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().length() <= 6) {
                    Toast.makeText(Register.this, "Password should be longer than 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    PatientModel patientModel = new PatientModel(fName.getText().toString(), lName.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString());
                    DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(Register.this);
                    boolean c = dataBaseOpenHelper.checkIfRegistered(username.getText().toString());
                    if (c) {
                        Toast.makeText(Register.this, "Username already taken", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean b = dataBaseOpenHelper.addPatient(patientModel);
                        if (!b) {
                            Toast.makeText(Register.this, "Register Failed Username already taken", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Register.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}