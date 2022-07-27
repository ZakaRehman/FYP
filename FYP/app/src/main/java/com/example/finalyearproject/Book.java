 package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Book extends AppCompatActivity {

    Spinner spinner;
    ListView doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        EditText date = findViewById(R.id.etDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                final int[] month = {currentDate.get(Calendar.MONTH)};
                int day = currentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(Book.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        int newMonth = month[0] = month[0] + 1;
                        LocalDate selected = LocalDate.of(mYear, monthOfYear , dayOfMonth);
                        LocalDate current = LocalDate.of(year, newMonth, day);
                        if (selected.isBefore(current)){
                            Toast.makeText(Book.this, "Date cannot be before current date", Toast.LENGTH_SHORT).show();
                        } else {
                            if (monthOfYear < 10){
                                String settingDate = mYear + "-0" + monthOfYear + "-" + dayOfMonth;
                                date.setText(settingDate);
                            } else if (dayOfMonth < 10){
                                String settingDate = mYear + "-" + monthOfYear + "-0" + dayOfMonth;
                                date.setText(settingDate);
                            } else {
                                String settingDate = mYear + "-" + monthOfYear + "-" + dayOfMonth;
                                date.setText(settingDate);
                            }
                        }
                    }
                }, year, month[0], day);
                datePicker.show();
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Book.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.times));
        spinner = findViewById(R.id.spTime);
        spinner.setAdapter(arrayAdapter);

        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(Book.this);
        List<String> allDoctors = dataBaseOpenHelper.getAllDoctors();
        ArrayAdapter<String> doc = new ArrayAdapter<>(Book.this, android.R.layout.simple_list_item_1, allDoctors);
        doctor = findViewById(R.id.lvDoctor);
        doctor.setAdapter(doc);
        doctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = dataBaseOpenHelper.getEmailFromName((String) parent.getItemAtPosition(position));
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                String username = pref.getString("username", null);
                boolean b1 = dataBaseOpenHelper.checkIfBooked(date.getText().toString(), spinner.getSelectedItem().toString(), email);
                if (b1){
                    Toast.makeText(Book.this, "Time already booked with this doctor", Toast.LENGTH_SHORT).show();
                } else {
                        AppointmentModel appointmentModel = new AppointmentModel(date.getText().toString(), spinner.getSelectedItem().toString(), username, email);
                        boolean c = dataBaseOpenHelper.addAppointment(appointmentModel);
                        if (c){
                            Toast.makeText(Book.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                            ArrayAdapter<String> update = new ArrayAdapter<>(Book.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.times));
                            spinner.setAdapter(update);
                        } else {
                            Toast.makeText(Book.this, "Booking failed", Toast.LENGTH_SHORT).show();
                        }
                    }


            }
        });

        Button button = findViewById(R.id.btnPat);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book.this, Patient.class);
                startActivity(intent);
            }
        });
    }
}