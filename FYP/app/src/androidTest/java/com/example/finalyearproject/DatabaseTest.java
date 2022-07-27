package com.example.finalyearproject;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    @Test
    public void addPatientTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalyearproject", appContext.getPackageName());
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(appContext);
        PatientModel patientModel = new PatientModel("Zaka", "Rehman", "zrehma12@bradford.ac.uk", "TestUSer", "password");
        boolean b = dataBaseOpenHelper.addPatient(patientModel);
        assertEquals("add patient method failed", true, b);
        boolean zaka12 = dataBaseOpenHelper.checkIfRegistered("TestUSer");
        assertEquals("check if registered doesn't work on already registered", true, zaka12);
    }

    @Test
    public void addDoctorTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalyearproject", appContext.getPackageName());
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(appContext);
        DoctorModel doctorModel = new DoctorModel("dan", "smith", "dsmith@test.com", "password123", 0);
        boolean b = dataBaseOpenHelper.addDoctor(doctorModel);
        assertEquals("add doctor failed", true, b);
    }

    @Test
    public void addAppointmentTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalyearproject", appContext.getPackageName());
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(appContext);
        AppointmentModel appointmentModel = new AppointmentModel("2021-02-08", "10:30", "TestUSer", "zaka2008@live.co.uk");
        boolean b = dataBaseOpenHelper.addAppointment(appointmentModel);
        assertEquals("add appointment failed", true, b);
    }

    @Test
    public void addMessageTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalyearproject", appContext.getPackageName());
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(appContext);
        MessageModel messageModel = new MessageModel("test message", "TestUSer", "dsmith@test.com");
        boolean b = dataBaseOpenHelper.addMessage(messageModel);
        assertEquals("add message failed", true, b);
    }

    @Test
    public void editPatientTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalyearproject", appContext.getPackageName());
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(appContext);
        PatientModel patientModel = new PatientModel("test", "test", "test@test.com", "TestUSer", "password123");
        boolean b = dataBaseOpenHelper.updatePatient(patientModel);
        assertEquals("edit patient failed", true, b);
    }

    @Test
    public void editDoctorTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalyearproject", appContext.getPackageName());
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(appContext);
        boolean b = dataBaseOpenHelper.editDoctor("dsmith@test.com", "test", "test", "testing123");
        assertEquals("edit doctor failed", true, b);
    }

    @Test
    public void deletePatientTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalyearproject", appContext.getPackageName());
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(appContext);
        boolean testUSer = dataBaseOpenHelper.deletePatient("TestUSer");
        assertEquals("delete patient failed", true, testUSer);
    }


    @Test
    public void deleteDoctorTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalyearproject", appContext.getPackageName());
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(appContext);
        boolean b = dataBaseOpenHelper.deleteDoctor("dsmith@test.com");
        assertEquals("delete doctor failed", true, b);
    }


    @Test
    public void deleteAppointmentTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalyearproject", appContext.getPackageName());
        DataBaseOpenHelper dataBaseOpenHelper = new DataBaseOpenHelper(appContext);
        AppointmentModel appointmentModel = new AppointmentModel("2021-02-08", "10:30", "TestUSer", "zaka2008@live.co.uk");
        boolean b = dataBaseOpenHelper.deleteAppointment(appointmentModel);
        assertEquals("Delete appointment failed", true, b);
    }



}