package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AddAppointment extends AppCompatActivity {


    public List<String>createTimeSlots(Boolean isCurrentDay){
        List<String> timeSlot = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        if (!isCurrentDay){
            calendar.set(Calendar.HOUR_OF_DAY, 8);
        }
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        for (int i = 0; i < 23; i++){
            String day1 = sdf.format(calendar.getTime());
            timeSlot.add(day1);
            calendar.add(Calendar.MINUTE, 30);
        }
        return timeSlot;
    }

    public List<String>getTimeSlots(){
        EditText date = findViewById(R.id.editTextDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date db = new Date();
        Boolean isCurrentDay;
        if (date.getText().toString().equals(sdf.format(db))){
            isCurrentDay = true;
        } else {
            isCurrentDay = false;
        }
        List<String> timeSlots = createTimeSlots(isCurrentDay);
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(AddAppointment.this);
        List<String> unbookedSlots = dataBaseOpenHelper.getUnbookedSlots(timeSlots, date.getText().toString());
        return unbookedSlots;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        EditText date = findViewById(R.id.editTextDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                final int[] month = {currentDate.get(Calendar.MONTH)};
                int day = currentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(AddAppointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        int newMonth = month[0] = month[0] + 1;
                        LocalDate selected = LocalDate.of(mYear, monthOfYear , dayOfMonth);
                        LocalDate current = LocalDate.of(year, newMonth, day);
                        if (selected.isBefore(current)){
                            Toast.makeText(AddAppointment.this, "Date cannot be before current date", Toast.LENGTH_SHORT).show();
                        } else {
                            if (monthOfYear < 10){
                                if (dayOfMonth < 10){
                                    String settingDate = mYear + "-0" + monthOfYear + "-0" + dayOfMonth;
                                    date.setText(settingDate);
                                }
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

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAppointment.this, ManageAppointments.class);
                startActivity(intent);
            }
        });

        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(AddAppointment.this);
        List<String> usernames = dataBaseOpenHelper.getUsernames();
        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(AddAppointment.this, android.R.layout.simple_spinner_dropdown_item, usernames);
        List<String> doctorEmails = dataBaseOpenHelper.getDoctorEmails();
        ArrayAdapter<String> docAdapter = new ArrayAdapter<>(AddAppointment.this, android.R.layout.simple_spinner_dropdown_item, doctorEmails);
        List<String> timeSlots = getTimeSlots();
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(AddAppointment.this, android.R.layout.simple_spinner_dropdown_item, timeSlots);
        Spinner u = findViewById(R.id.spUsername);
        u.setAdapter(userAdapter);
        Spinner e = findViewById(R.id.spEmail);
        e.setAdapter(docAdapter);
        Spinner t = findViewById(R.id.spAppTime);
        t.setAdapter(timeAdapter);

        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner u = findViewById(R.id.spUsername);
                Spinner e = findViewById(R.id.spEmail);
                Spinner t = findViewById(R.id.spAppTime);
                EditText date = findViewById(R.id.editTextDate);
                DataBaseOpenHelper db = new DataBaseOpenHelper(AddAppointment.this);
                boolean b = db.checkIfBooked(date.getText().toString(), t.getSelectedItem().toString(), e.getSelectedItem().toString());
                if (b){
                    Toast.makeText(AddAppointment.this, "Appointment already booked", Toast.LENGTH_SHORT).show();
                } else {
                    AppointmentModel appointmentModel = new AppointmentModel(date.getText().toString(), t.getSelectedItem().toString(), u.getSelectedItem().toString(), e.getSelectedItem().toString());
                    boolean b1 = db.addAppointment(appointmentModel);
                    if (b1){
                        Toast.makeText(AddAppointment.this, "Appointment booked", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddAppointment.this, "Appointment booking failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}