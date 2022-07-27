package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDoctor extends AppCompatActivity {

    EditText first;
    EditText last;
    EditText email;
    EditText pass;

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
        setContentView(R.layout.activity_add_doctor);

        Button button = findViewById(R.id.btnManage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDoctor.this, ManageDoctors.class);
                startActivity(intent);
            }
        });

        Button button1 = findViewById(R.id.btnAdd);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = findViewById(R.id.etFirst);
                last = findViewById(R.id.etLast);
                email = findViewById(R.id.etEmail1);
                pass = findViewById(R.id.etPass1);

                if (first.getText().toString().equals("First Name") || first.getText().toString().isEmpty()){
                    Toast.makeText(AddDoctor.this, "First Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!first.getText().toString().matches("[A-za-z]*")){
                    Toast.makeText(AddDoctor.this, "First Name should only be letters", Toast.LENGTH_SHORT).show();
                } else if (last.getText().toString().equals("Last Name") || last.getText().toString().isEmpty()){
                    Toast.makeText(AddDoctor.this, "Last Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!last.getText().toString().matches("[A-za-z]*")){
                    Toast.makeText(AddDoctor.this, "Last Name should only be letters", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().equals("Email") || email.getText().toString().isEmpty()){
                    Toast.makeText(AddDoctor.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!emailValidator(email.getText().toString())){
                    Toast.makeText(AddDoctor.this, "Not a valid email", Toast.LENGTH_SHORT).show();
                } else if (pass.getText().toString().equals("Password") || pass.getText().toString().isEmpty()){
                    Toast.makeText(AddDoctor.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (pass.getText().toString().length() <= 6){
                    Toast.makeText(AddDoctor.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    DoctorModel doctorModel = new DoctorModel(first.getText().toString(), last.getText().toString(), email.getText().toString(), pass.getText().toString(), 0);
                    DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(AddDoctor.this);
                    boolean b = dataBaseOpenHelper.addDoctor(doctorModel);
                    if (b){
                        Toast.makeText(AddDoctor.this, "Doctor added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddDoctor.this, "Doctor not added successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}