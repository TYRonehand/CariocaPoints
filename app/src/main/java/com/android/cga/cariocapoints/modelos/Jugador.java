package com.android.cga.cariocapoints.modelos;

import android.content.ContentValues;

import java.util.Date;
import java.util.UUID;

/**
 * Created by gutie on 11/01/2017.
 */

public class Jugador {


    private String _ID;

    private String IDJugador;
    private String NombreJugador;

    //CONSTRUCTORES
    public Jugador(){
        this.IDJugador = UUID.randomUUID().toString();}

    public Jugador(String nombre){

        this.IDJugador = UUID.randomUUID().toString();
        this.NombreJugador = nombre;

    }




    //GETTERS
    public String getIDJugador() {
        return IDJugador;
    }
    public String getNombreJugador() {
        return NombreJugador;
    }
    public String get_ID() {
        return _ID;
    }

    //SETTERS
    public void setIDJugador(String IDJugador) { this.IDJugador = IDJugador; }
    public void setNombreJugador(String nombreJugador) {
        NombreJugador = nombreJugador;
    }
    public void set_ID(String _ID) {
        this._ID = _ID;
    }






    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(JugadorEsquema.JugadorEntry.IDJUGADOR, this.getIDJugador());
        values.put(JugadorEsquema.JugadorEntry.NOMBRE, this.getNombreJugador());
        return values;
    }
}
