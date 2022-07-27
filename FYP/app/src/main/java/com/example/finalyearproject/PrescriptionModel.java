package com.example.finalyearproject;

public class PrescriptionModel {

    private String name;
    private String dose;
    private String patient;
    private String doctor;

    public PrescriptionModel(String name, String dose, String patient, String doctor){
        this.name = name;
        this.dose = dose;
        this.patient = patient;
        this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    public String getDose() {
        return dose;
    }

    public String getPatient() {
        return patient;
    }

    public String getDoctor() {
        return doctor;
    }

    @Override
    public String toString() {
        return name + "\n" + dose + "\n" + patient + "\n" + doctor;
    }
}
