package com.aprendec.model;

public class Nominas {
    private int id;
    private String dni;
    private double sueldoBase;
    private double sueldo;

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public double getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(double sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return "Nomina [id=" + id + ", dni=" + dni + ", sueldoBase=" + sueldoBase + ", sueldo=" + sueldo + "]";
    }
}
