package com.android.cga.cariocapoints.modelos;

import android.content.ContentValues;

import java.util.Date;
import java.util.UUID;

/**
 * Created by gutie on 11/01/2017.
 */

public class Rondas {

    private String _ID;

    private String IDRonda;
    private String Nombre;

    //CONSTRUCTORES
    public Rondas(){
        this.IDRonda = UUID.randomUUID().toString();
    }
    public Rondas(String nombre){

        this.IDRonda = UUID.randomUUID().toString();
        this.Nombre = nombre;

    }
    //GETTERS
    public String getIDRonda() {
        return IDRonda;
    }
    public String getNombre() {
        return Nombre;
    }
    public String get_ID() {
        return _ID;
    }
    //SETTERS
    public void setIDRonda(String IDRonda) {
        this.IDRonda = IDRonda;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(RondasEsquema.RondasEntry.IDRONDA, this.IDRonda);
        values.put(RondasEsquema.RondasEntry.NOMBRERONDA, this.Nombre);
        return values;
    }




}
