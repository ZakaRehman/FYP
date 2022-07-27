package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class DeleteAppointment extends AppCompatActivity {

    ArrayAdapter<AppointmentModel> arrayAdapter;
    DataBaseOpenHelper dataBaseOpenHelper;
    List<AppointmentModel> username;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_appointment);

        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteAppointment.this, Doctor.class);
                startActivity(intent);
            }
        });

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        dataBaseOpenHelper = new DataBaseOpenHelper(DeleteAppointment.this);
            try {
                username = dataBaseOpenHelper.getDoctorAppointments(pref.getString("username", null));
                if (username != null){
                    arrayAdapter = new ArrayAdapter<>(DeleteAppointment.this, android.R.layout.simple_list_item_1, username);
                    listView = findViewById(R.id.doc);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            boolean b = dataBaseOpenHelper.deleteAppointment((AppointmentModel) parent.getItemAtPosition(position));
                            if (b){
                                Toast.makeText(DeleteAppointment.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                                username = dataBaseOpenHelper.getDoctorAppointments(pref.getString("username", null));
                                if (username != null){
                                    arrayAdapter = new ArrayAdapter<>(DeleteAppointment.this, android.R.layout.simple_list_item_1, username);
                                    listView = findViewById(R.id.doc);
                                    listView.setAdapter(arrayAdapter);
                                }

                            } else {
                                Toast.makeText(DeleteAppointment.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (NullPointerException e){
                Toast.makeText(DeleteAppointment.this, "there are no appointments to delete", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DeleteAppointment.this, Doctor.class);
                startActivity(intent);
            }
        }

    }