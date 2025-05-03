package com.raf.javafxapp.Model;

public class Psychotherapist {
    private String fullName;
    private String email;
    private String phone;
    private String specialization;
    private String biography;

    public Psychotherapist(String fullName, String email, String phone, String specialization, String biography) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.biography = biography;
    }

    // Getteri
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getSpecialization() { return specialization; }
    public String getBiography() { return biography; }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
