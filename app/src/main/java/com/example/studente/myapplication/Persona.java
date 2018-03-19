package com.example.studente.myapplication;

import java.io.Serializable;

/**
 * Created by studente on 06/03/2018.
 */

public class Persona implements Serializable {
    private String nome,cognome,telefono;

    public Persona(String nome,String cognome,String telefono ){
        this.nome=nome;
        this.cognome=cognome;
        this.telefono=telefono;

    }

    public Persona(){

    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
