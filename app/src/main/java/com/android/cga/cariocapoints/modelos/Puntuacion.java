package com.android.cga.cariocapoints.modelos;

import android.content.ContentValues;

import java.util.UUID;

/**
 * Created by gutie on 11/01/2017.
 */

public class Puntuacion {


    private String _ID;

    private String IDPuntuacion;
    private String IDPartida;
    private String IDJugador;
    private String IDRonda;
    private String Puntos;

    //CONSTRUCTORES
    public Puntuacion(){
        this.IDPuntuacion = UUID.randomUUID().toString();
    }

    public Puntuacion(String idpartida,String idjugador,String idronda,String puntos){

        this.IDPuntuacion = UUID.randomUUID().toString();
        this.IDPartida = idpartida;
        this.IDJugador = idjugador;
        this.IDRonda = idronda;
        this.Puntos = puntos;

    }

    //GETTERS
    public String getIDPuntuacion() {
        return IDPuntuacion;
    }
    public String getIDPartida() {
        return IDPartida;
    }
    public String getIDJugador() {
        return IDJugador;
    }
    public String getIDRonda() {
        return IDRonda;
    }
    public String getPuntos() {
        return Puntos;
    }
    public String get_ID() {
        return _ID;
    }

    //SETTERS
    public void setIDPuntuacion(String IDPuntuacion) {  this.IDPuntuacion = IDPuntuacion; }
    public void setIDPartida(String IDPartida) { this.IDPartida = IDPartida; }
    public void setIDJugador(String IDJugador) { this.IDJugador = IDJugador; }
    public void setIDRonda(String IDRonda) { this.IDRonda = IDRonda; }
    public void setPuntos(String puntos) { Puntos = puntos; }
    public void set_ID(String _ID) {
        this._ID = _ID;
    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(PuntuacionEsquema.PuntuacionEntry.IDPUNTUACION, this.getIDPuntuacion());
        values.put(PuntuacionEsquema.PuntuacionEntry.IDPARTIDA, this.getIDPartida());
        values.put(PuntuacionEsquema.PuntuacionEntry.IDJUGADOR, this.getIDJugador());
        values.put(PuntuacionEsquema.PuntuacionEntry.IDRONDA, this.getIDRonda());
        values.put(PuntuacionEsquema.PuntuacionEntry.PUNTOS, this.getPuntos());
        return values;
    }
}
