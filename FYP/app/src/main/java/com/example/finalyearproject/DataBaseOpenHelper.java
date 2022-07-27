package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    String patientTable = "CREATE TABLE IF NOT EXISTS patients (fName VARCHAR, lName VARCHAR, Email VARCHAR, Username VARCHAR PRIMARY KEY, Password VARCHAR)";
    String doctorTable = "CREATE TABLE IF NOT EXISTS doctors (fName VARCHAR, lName VARCHAR, Email VARCHAR PRIMARY KEY, Password VARCHAR, Administrator TINYINT)";
    String appointmentTable = "CREATE TABLE IF NOT EXISTS appointments (AppointmentID INTEGER PRIMARY KEY AUTOINCREMENT, DateBooked DATE, TimeBooked TIME, BookedPatient VARCHAR, BookedDoctor VARCHAR, FOREIGN KEY(BookedPatient) REFERENCES patients(Username), FOREIGN KEY(BookedDoctor) REFERENCES doctors(Email))";
    String messageTable = "CREATE TABLE IF NOT EXISTS messages (MessageID INTEGER PRIMARY KEY AUTOINCREMENT, Message TEXT, Sender VARCHAR, Receiver VARCHAR)";
    String prescriptionTable = "CREATE TABLE IF NOT EXISTS prescriptions (ID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, dosage VARCHAR, Doctor VARCHAR, Patient VARCHAR, FOREIGN KEY (Patient) REFERENCES patients(Username), FOREIGN KEY (Doctor) REFERENCES doctors(Email))";

    public DataBaseOpenHelper(Context context){
        super(context, "final.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(patientTable);
        db.execSQL(doctorTable);
        db.execSQL(appointmentTable);
        db.execSQL(messageTable);
        db.execSQL(prescriptionTable);
        db.execSQL("INSERT INTO doctors (fName, lName, Email, Password, Administrator) VALUES ('Zaka', 'Rehman', 'zaka2008@live.co.uk', 'Admin123', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS patients");
        db.execSQL("DROP TABLE IF EXISTS doctors");
        db.execSQL("DROP TABLE IF EXISTS appointments");
        db.execSQL("DROP TABLE IF EXISTS messages");
        db.execSQL("DROP TABLE IF EXISTS prescriptions");
        onCreate(db);
    }

    public boolean checkIfRegistered(String Username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM patients WHERE Username = ?";
        String[] args = new String[1];
        args[0] = Username;
        Cursor c = db.rawQuery(query, args);
        if (c.moveToFirst()){
            c.close();
            db.close();
            return true;
        } else {
            c.close();
            db.close();
            return false;
        }
    }

    public boolean addPatient(PatientModel patientModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fName", patientModel.getFirstName());
        values.put("lName", patientModel.getLastName());
        values.put("Email", patientModel.getEmail());
        values.put("Username", patientModel.getUsername());
        values.put("Password", patientModel.getPassword());
        long patients = db.insert("patients", "", values);
        if (patients == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public boolean patientLogin(String Username, String Password){
        String query = "SELECT * FROM patients WHERE Username = ? AND Password = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = new String[2];
        args[0] = Username;
        args[1] = Password;
        Cursor c = db.rawQuery(query, args);
        if (c.moveToFirst()){
            c.close();
            db.close();
            return true;
        } else {
            c.close();
            db.close();
            return false;
        }
    }

    public int doctorLogin(String Email, String Password){
        String query = "SELECT * FROM doctors WHERE Email = ? AND Password = ?";
        String[] args = new String[2];
        args[0] = Email;
        args[1] = Password;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, args);
        if (c.moveToFirst()){
            int admin = c.getInt(4);
            c.close();
            db.close();
            return admin;
        } else {
            c.close();
            db.close();
            return -1;
        }
    }

    public List<String> getAllDoctors(){
        List<String> docName = new ArrayList<String>();
        String name;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT fName, lName FROM doctors WHERE Administrator = 0";
        Cursor c = db.rawQuery(query, null);
        if (c != null){
            while (c.moveToNext()){
                String firstName = c.getString(0);
                String lastName = c.getString(1);
                name = firstName + " " + lastName;
                docName.add(name);
            }
        }
        c.close();
        db.close();
        return docName;
    }

    public List<String> getAllDoctors1(){
        List<String> docName = new ArrayList<String>();
        String name;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT fName, lName FROM doctors";
        Cursor c = db.rawQuery(query, null);
        if (c != null){
            while (c.moveToNext()){
                String firstName = c.getString(0);
                String lastName = c.getString(1);
                name = firstName + " " + lastName;
                docName.add(name);
            }
        }
        c.close();
        db.close();
        return docName;
    }

    public String getEmailFromName(String name){
        String[] split = name.split("\\s+");
        String email = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Email FROM doctors WHERE fName = ? AND lName = ?";
        Cursor c = db.rawQuery(query, split);
        if (c.moveToFirst()){
            email = c.getString(0);
        }
        c.close();
        db.close();
        return email;
    }

    public boolean checkIfBooked(String date, String time, String email){
        String query = "SELECT * FROM appointments WHERE DateBooked = ? AND TimeBooked = ? AND BookedDoctor = ?";
        String[] args = new String[3];
        args[0] = date;
        args[1] = time;
        args[2] = email;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, args);
        if (c.moveToFirst()){
            c.close();
            db.close();
            return true;
        } else {
            c.close();
            db.close();
            return false;
        }
    }

    public boolean addAppointment(AppointmentModel appointmentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DateBooked", appointmentModel.getDateBooked());
        values.put("TimeBooked", appointmentModel.getTimeBooked());
        values.put("BookedPatient", appointmentModel.getBookedPatient());
        values.put("BookedDoctor", appointmentModel.getBookedDoctor());
        long appointments = db.insert("appointments", "", values);
        if (appointments == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public List<AppointmentModel>getPatientAppointments(String username){
        List<AppointmentModel>appointmentModels = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE BookedPatient = ?";
        String[] args = new String[1];
        args[0] = username;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, args);
        if (c.moveToFirst()){
            String date = c.getString(1);
            String time = c.getString(2);
            String bookedPatient = c.getString(3);
            String bookedDoctor = c.getString(4);
            AppointmentModel appointmentModel = new AppointmentModel(date, time, bookedPatient, bookedDoctor);
            appointmentModels.add(appointmentModel);

            while (c.moveToNext()){
                date = c.getString(1);
                time = c.getString(2);
                bookedPatient = c.getString(3);
                bookedDoctor = c.getString(4);
                appointmentModel = new AppointmentModel(date, time, bookedPatient, bookedDoctor);
                appointmentModels.add(appointmentModel);
            }
        } else{
            c.close();
            db.close();
            return null;
        }
        c.close();
        db.close();
        return appointmentModels;
    }

    public List<AppointmentModel>getDoctorAppointments(String email){
        List<AppointmentModel>appointmentModels = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE BookedDoctor = ?";
        String[] args = new String[1];
        args[0] = email;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, args);
        if (c.moveToFirst()){
            int id = c.getInt(0);
            String date = c.getString(1);
            String time = c.getString(2);
            String bookedPatient = c.getString(3);
            String bookedDoctor = c.getString(4);
            AppointmentModel appointmentModel = new AppointmentModel(date, time, bookedPatient, bookedDoctor);
            appointmentModels.add(appointmentModel);

            while (c.moveToNext()){
                id = c.getInt(0);
                date = c.getString(1);
                time = c.getString(2);
                bookedPatient = c.getString(3);
                bookedDoctor = c.getString(4);
                appointmentModel = new AppointmentModel(id, date, time, bookedPatient, bookedDoctor);
                appointmentModels.add(appointmentModel);
            }
        } else{
            c.close();
            db.close();
            return null;
        }
        c.close();
        db.close();
        return appointmentModels;
    }

    public boolean deleteAppointment(AppointmentModel appointmentModel){
        String where = "DateBooked = ? AND TimeBooked = ?";
        String[] args = new String[2];
        args[0] = appointmentModel.getDateBooked();
        args[1] = appointmentModel.getTimeBooked();
        SQLiteDatabase db = this.getWritableDatabase();
        int appointments = db.delete("appointments", where, args);
        if (appointments == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public List<String>getUsernames(){
        List<String>users = new ArrayList<>();
        String username;
        String query = "SELECT Username FROM patients";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null){
            while (c.moveToNext()){
                username = c.getString(0);
                users.add(username);
            }
        }
        c.close();
        db.close();
        return users;
    }

    public boolean deletePatient(String username){
        String where = "Username = ?";
        String[] args = new String[1];
        args[0] = username;
        SQLiteDatabase db = this.getWritableDatabase();
        int patients = db.delete("patients", where, args);
        db.close();
        if (patients == -1){
            return false;
        } else {
            return true;
        }
    }

    public List<PatientModel>getPatientFromUsername(String username){
        List<PatientModel>patientModels = new ArrayList<>();
        String query = "SELECT * FROM patients WHERE Username = ?";
        String[] args = new String[1];
        args[0] = username;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, args);
        if (c.moveToFirst()){
            String first = c.getString(0);
            String last = c.getString(1);
            String email = c.getString(2);
            String user = c.getString(3);
            String password = c.getString(4);
            PatientModel patientModel = new PatientModel(first, last, email, user, password);
            patientModels.add(patientModel);
            c.close();
            db.close();
            return patientModels;
        }
        c.close();
        db.close();
        return null;
    }

    public boolean updatePatient(PatientModel patientModel){
        ContentValues values = new ContentValues();
        values.put("fName", patientModel.getFirstName());
        values.put("lName", patientModel.getLastName());
        values.put("Email", patientModel.getEmail());
        values.put("Password", patientModel.getPassword());
        String[] args = new String[1];
        args[0] = patientModel.getUsername();
        SQLiteDatabase db = this.getWritableDatabase();
        int patients = db.update("patients", values, "Username = ?", args);
        db.close();
        if (patients == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addDoctor(DoctorModel doctorModel){
        ContentValues values = new ContentValues();
        values.put("fName", doctorModel.getFirstName());
        values.put("lName", doctorModel.getLastName());
        values.put("Email", doctorModel.getEmail());
        values.put("Password", doctorModel.getPassword());
        values.put("Administrator", doctorModel.getAdministrator());

        SQLiteDatabase db = this.getWritableDatabase();
        long doctors = db.insert("doctors", "", values);
        db.close();
        if (doctors == -1){
            return false;
        } else {
            return true;
        }
    }

    public List<String>getUnbookedSlots(List<String> strings, String date){
        List<String> bookedTime = new ArrayList<>();
        Set<String> a = new HashSet<>(strings);
        String[] arg = new String[1];
        arg[0] = date;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT TimeBooked FROM appointments WHERE DateBooked = ?", arg);
        if (c.moveToFirst()){
            String time = c.getString(0);
            bookedTime.add(time);
        } else{
            c.close();
            db.close();
            return strings;
        }
        c.close();
        db.close();
        Set<String> b = new HashSet<>(bookedTime);
        a.removeAll(b);
        List<String> unbookedSlot = new ArrayList<>(a);
        return unbookedSlot;
    }

    public List<String>getDoctorEmails(){
        List<String>emails = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Email FROM doctors WHERE Administrator = 0", null);
        if (c.moveToFirst()){
            String email = c.getString(0);
            emails.add(email);
            while (c.moveToNext()){
                String e = c.getString(0);
                emails.add(e);
            }
        }

        c.close();
        db.close();
        return emails;
    }

    public DoctorModel getDoctorFromEmail(String email){
        String[] doc = new String[1];
        doc[0] = email;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM doctors WHERE Email = ?", doc);
        if (c.moveToFirst()){
            String fName = c.getString(0);
            String lName = c.getString(1);
            String password = c.getString(3);
            int admin = c.getInt(4);
            DoctorModel doctorModel = new DoctorModel(fName, lName, email, password, admin);
            c.close();
            db.close();
            return doctorModel;
        }
        c.close();
        db.close();
        return null;
    }

    public boolean editDoctor(String email, String fName, String lName, String password){
        ContentValues values = new ContentValues();
        values.put("fName", fName);
        values.put("lName", lName);
        values.put("Password", password);
        String[] e = new String[1];
        e[0] = email;
        SQLiteDatabase db = this.getWritableDatabase();
        int doctors = db.update("doctors", values, "Email = ?", e);
        db.close();
        if (doctors == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteDoctor(String email){
        String[] e = new String[1];
        e[0] = email;
        SQLiteDatabase db = this.getWritableDatabase();
        int doctors = db.delete("doctors", "Email = ?", e);
        db.close();
        if (doctors == -1){
            return false;
        } else {
            return true;
        }
    }

    public List<AppointmentModel> getAllAppointments(){
        List<AppointmentModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM appointments", null);
        if (c.moveToFirst()){
            int taskID = c.getInt(0);
            String date = c.getString(1);
            String time = c.getString(2);
            String pat = c.getString(3);
            String doc = c.getString(4);
            AppointmentModel appointmentModel = new AppointmentModel(taskID, date, time, pat, doc);
            list.add(appointmentModel);
            while (c.moveToNext()){
                int taskID1 = c.getInt(0);
                String date1 = c.getString(1);
                String time1 = c.getString(2);
                String pat1 = c.getString(3);
                String doc1 = c.getString(4);
                AppointmentModel appointmentModel1 = new AppointmentModel(taskID1, date1, time1, pat1, doc1);
                list.add(appointmentModel1);
            }
        } else {
            c.close();
            db.close();
            return null;
        }

        c.close();
        db.close();
        return list;
    }

    public boolean addMessage(MessageModel messageModel){
        ContentValues values = new ContentValues();
        values.put("Message", messageModel.getMessage());
        values.put("Sender", messageModel.getSender());
        values.put("Receiver", messageModel.getReceiver());
        SQLiteDatabase db = this.getWritableDatabase();
        long messages = db.insert("messages", null, values);
        db.close();
        if (messages == -1){
            return false;
        } else{
            return true;
        }
    }

    public List<MessageModel> getPatientMessages(String username, String email){
        String message;
        String sent;
        String receive;
        MessageModel model;
        String[] arg = new String[4];
        arg[0] = username;
        arg[1] = email;
        arg[2] = username;
        arg[3] = email;
        List<MessageModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM messages WHERE (Sender = ? AND Receiver = ?) OR (Receiver = ? AND Sender = ?)", arg);
        if (c.moveToFirst()){
            message = c.getString(1);
            sent = c.getString(2);
            receive = c.getString(3);
            model = new MessageModel(message, sent, receive);
            list.add(model);
            while (c.moveToNext()){
                message = c.getString(1);
                sent = c.getString(2);
                receive = c.getString(3);
                model = new MessageModel(message, sent, receive);
                list.add(model);
            }
        }

        c.close();
        db.close();
        return list;
    }

    public boolean promoteDoctor(DoctorModel doctorModel){
        String[] args = new String[1];
        args[0] = doctorModel.getEmail();
        ContentValues values = new ContentValues();
        values.put("Administrator", 1);
        SQLiteDatabase db = this.getWritableDatabase();
        int doctors = db.update("doctors", values, "email = ?", args);
        db.close();
        if (doctors == -1){
            return false;
        } else{
            return true;
        }
    }

    public boolean demoteDoctor(DoctorModel doctorModel){
        String[] args = new String[1];
        args[0] = doctorModel.getEmail();
        ContentValues values = new ContentValues();
        values.put("Administrator", 0);
        SQLiteDatabase db = this.getWritableDatabase();
        int doctors = db.update("doctors", values, "email = ?", args);
        db.close();
        if (doctors == -1){
            return false;
        } else{
            return true;
        }
    }

    public boolean addPrescription(PrescriptionModel prescriptionModel){
        ContentValues values = new ContentValues();
        values.put("name", prescriptionModel.getName());
        values.put("dosage", prescriptionModel.getDose());
        values.put("Patient", prescriptionModel.getPatient());
        values.put("Doctor", prescriptionModel.getDoctor());
        SQLiteDatabase db = this.getWritableDatabase();
        long prescriptions = db.insert("prescriptions", "", values);
        db.close();
        if (prescriptions == -1){
            return false;
        } else {
            return true;
        }
    }

    public List<PrescriptionModel> getPrescriptions(String username){
        String[] args = new String[1];
        args[0] = username;
        String med;
        String dos;
        String pat;
        String doc;
        List<PrescriptionModel> pre = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM prescriptions WHERE Patient = ?", args);
        if (c.moveToFirst()){
            med = c.getString(1);
            dos = c.getString(2);
            pat = c.getString(3);
            doc = c.getString(4);
            PrescriptionModel prescriptionModel = new PrescriptionModel(med, dos, pat, doc);
            pre.add(prescriptionModel);
            while (c.moveToNext()){
                med = c.getString(1);
                dos = c.getString(2);
                pat = c.getString(3);
                doc = c.getString(4);
                PrescriptionModel prescriptionModel1 = new PrescriptionModel(med, dos, pat, doc);
                pre.add(prescriptionModel1);
            }
        }
        c.close();
        db.close();
        return pre;
    }
}
