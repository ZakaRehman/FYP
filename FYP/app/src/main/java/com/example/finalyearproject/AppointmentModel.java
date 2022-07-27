package com.example.finalyearproject;

public class AppointmentModel {
    private int id;
    private String dateBooked;
    private String timeBooked;
    private String bookedPatient;
    private String bookedDoctor;

    public AppointmentModel(int id, String dateBooked, String timeBooked, String bookedPatient, String bookedDoctor) {
        setId(id);
        setDateBooked(dateBooked);
        setTimeBooked(timeBooked);
        setBookedPatient(bookedPatient);
        setBookedDoctor(bookedDoctor);
    }

    public AppointmentModel(String dateBooked, String timeBooked, String bookedPatient, String bookedDoctor) {
        setDateBooked(dateBooked);
        setTimeBooked(timeBooked);
        setBookedPatient(bookedPatient);
        setBookedDoctor(bookedDoctor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(String dateBooked) {
        this.dateBooked = dateBooked;
    }

    public String getTimeBooked() {
        return timeBooked;
    }

    public void setTimeBooked(String timeBooked) {
        this.timeBooked = timeBooked;
    }

    public String getBookedPatient() {
        return bookedPatient;
    }

    public void setBookedPatient(String bookedPatient) {
        this.bookedPatient = bookedPatient;
    }

    public String getBookedDoctor() {
        return bookedDoctor;
    }

    public void setBookedDoctor(String bookedDoctor) {
        this.bookedDoctor = bookedDoctor;
    }

    public String toString(){
        String date = getDateBooked();
        String time = getTimeBooked();
        String pat = getBookedPatient();
        String doc = getBookedDoctor();
        String result = date + "\n" + time + "\n" + pat + "\n" + doc;
        return result;
    }
}
