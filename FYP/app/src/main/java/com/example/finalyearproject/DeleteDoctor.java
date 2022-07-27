package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class DeleteDoctor extends AppCompatActivity {

    DataBaseOpenHelper dataBaseOpenHelper;
    List<String> doctorEmails;
    ArrayAdapter<String> arrayAdapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_doctor);

        Button button = findViewById(R.id.btnManage1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteDoctor.this, ManageDoctors.class);
                startActivity(intent);
            }
        });

        dataBaseOpenHelper = new DataBaseOpenHelper(DeleteDoctor.this);
        lv = findViewById(R.id.lvDelete);
        doctorEmails = dataBaseOpenHelper.getDoctorEmails();
        if (doctorEmails != null){
            arrayAdapter = new ArrayAdapter<>(DeleteDoctor.this, android.R.layout.simple_spinner_dropdown_item, doctorEmails);
            lv.setAdapter(arrayAdapter);
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean b = dataBaseOpenHelper.deleteDoctor((String) parent.getItemAtPosition(position));
                if (b){
                    Toast.makeText(DeleteDoctor.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    doctorEmails = dataBaseOpenHelper.getDoctorEmails();
                    if (doctorEmails != null){
                        arrayAdapter = new ArrayAdapter<>(DeleteDoctor.this, android.R.layout.simple_spinner_dropdown_item, doctorEmails);
                        lv.setAdapter(arrayAdapter);
                    }
                } else{
                    Toast.makeText(DeleteDoctor.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}