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

public class DeletePatient extends AppCompatActivity {

    DataBaseOpenHelper dataBaseOpenHelper;
    List<String> usernames;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_patient);
        dataBaseOpenHelper = new DataBaseOpenHelper(DeletePatient.this);
        usernames = dataBaseOpenHelper.getUsernames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeletePatient.this, android.R.layout.simple_spinner_dropdown_item, usernames);
        spinner = findViewById(R.id.spUsers);
        spinner.setAdapter(adapter);

        Button button = findViewById(R.id.btnMP);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeletePatient.this, ManagePatients.class);
                startActivity(intent);
            }
        });

        Button button1 = findViewById(R.id.btnDeletePat);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = dataBaseOpenHelper.deletePatient(spinner.getSelectedItem().toString());
                if (b){
                    Toast.makeText(DeletePatient.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    usernames = dataBaseOpenHelper.getUsernames();
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(DeletePatient.this, android.R.layout.simple_spinner_dropdown_item, usernames);
                    spinner = findViewById(R.id.spUsers);
                    spinner.setAdapter(adapter1);
                } else{
                    Toast.makeText(DeletePatient.this, "Delete Not Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}