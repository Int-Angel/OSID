package com.example.osid.POJOs;

public class User {
    int id;
    int edad;
    float peso;
    float basal;
    boolean gender;
    String nombre, primerApellido, segundoApellido;

    public User(){

    }

    public User(int edad, float peso, float basal, boolean gender, String nombre, String primerApellido, String segundoApellido) {
        this.edad = edad;
        this.peso = peso;
        this.basal = basal;
        this.gender = gender;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }

    public User(int id, String nombre, String primerApellido, String segundoApellido, int edad, float peso, float basal, boolean gender) {
        this.id = id;
        this.edad = edad;
        this.peso = peso;
        this.basal = basal;
        this.gender = gender;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getBasal() {
        return basal;
    }

    public void setBasal(float basal) {
        this.basal = basal;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }
}
