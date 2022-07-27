package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText login;
    EditText password;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.btnSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = findViewById(R.id.etLogin);
                password = findViewById(R.id.etPass);

                if (!login.getText().toString().contains("@")){
                    DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(Login.this);
                    boolean b = dataBaseOpenHelper.patientLogin(login.getText().toString(), password.getText().toString());
                    if (!b){
                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        pref = getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", login.getText().toString());
                        editor.commit();
                        Intent intent = new Intent(Login.this, Patient.class);
                        startActivity(intent);
                    }
                } else {
                    DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(Login.this);
                    int b = dataBaseOpenHelper.doctorLogin(login.getText().toString(), password.getText().toString());
                    if (b == -1){
                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    } else if (b == 0){
                        pref = getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", login.getText().toString());
                        editor.commit();
                        Intent intent = new Intent(Login.this, Doctor.class);
                        startActivity(intent);
                    } else if (b == 1){
                        pref = getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username", login.getText().toString());
                        editor.commit();
                        Intent intent = new Intent(Login.this, Admin.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}