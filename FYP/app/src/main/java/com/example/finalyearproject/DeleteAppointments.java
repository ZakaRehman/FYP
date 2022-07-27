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

public class DeleteAppointments extends AppCompatActivity {

    DataBaseOpenHelper dataBaseOpenHelper;
    ListView lv;
    ArrayAdapter<AppointmentModel> arrayAdapter;
    List<AppointmentModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_appointments);

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteAppointments.this, ManageAppointments.class);
                startActivity(intent);
            }
        });

        dataBaseOpenHelper = new DataBaseOpenHelper(DeleteAppointments.this);
        lv = findViewById(R.id.lvDel);
        list = dataBaseOpenHelper.getAllAppointments();
        if (list != null){
            arrayAdapter = new ArrayAdapter<>(DeleteAppointments.this, android.R.layout.simple_spinner_dropdown_item, list);
            lv.setAdapter(arrayAdapter);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean b = dataBaseOpenHelper.deleteAppointment((AppointmentModel) parent.getItemAtPosition(position));
                if (b){
                    Toast.makeText(DeleteAppointments.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    list = dataBaseOpenHelper.getAllAppointments();
                    if (list != null){
                        arrayAdapter = new ArrayAdapter<>(DeleteAppointments.this, android.R.layout.simple_list_item_1, list);
                        lv.setAdapter(arrayAdapter);
                    }

                } else {
                    Toast.makeText(DeleteAppointments.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}