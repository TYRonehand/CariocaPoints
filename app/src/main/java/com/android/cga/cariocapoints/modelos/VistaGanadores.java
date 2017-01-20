package com.android.cga.cariocapoints.modelos;

import android.content.ContentValues;

import java.util.Date;

/**
 * Created by gutie on 11/01/2017.
 */

public class VistaGanadores {



    private String IDPartida;
    private String Fecha;
    private String NombreGanador;
    private int Puntaje;

    public VistaGanadores(String id,String fecha,String nombreGanador, int puntaje){

        this.IDPartida=id;
        this.Fecha = fecha;
        this.NombreGanador=nombreGanador;
        this.Puntaje = puntaje;

    }

    //getters
    public String getIDPartida() {
        return IDPartida;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getNombreGanador() {
        return NombreGanador;
    }

    public int getPuntaje() {
        return Puntaje;
    }

    //setter
    public void setIDPartida(String IDPartida) {
        this.IDPartida = IDPartida;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public void setNombreGanador(String nombreGanador) {
        NombreGanador = nombreGanador;
    }

    public void setPuntaje(int puntaje) {
        Puntaje = puntaje;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(VistaGanadoresEsquema.VistaGanadoresEntry.IDPARTIDA, this.getIDPartida());
        values.put(VistaGanadoresEsquema.VistaGanadoresEntry.FECHA, this.getFecha());
        values.put(VistaGanadoresEsquema.VistaGanadoresEntry.NOMBREGANADOR, this.getNombreGanador());
        values.put(VistaGanadoresEsquema.VistaGanadoresEntry.PUNTOS, this.getPuntaje());
        return values;
    }
}
