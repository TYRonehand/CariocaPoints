package com.android.cga.cariocapoints.modelos;

import android.content.ContentValues;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by gutie on 11/01/2017.
 */

public class Partida {

    //clase de estados
    public static class PartidaEstado {

        public static String STATE_OPEN = "abierto";
        public static String STATE_CLOSED = "cerrado";
        public static String DELIMITATOR_INFO_PLAYER = "//";
        public static String DELIMITATOR_ID_NAME =";";

    }


    //variables
    private String _ID;

    private String IDPartida;
    private String Fecha;
    private String Creator;
    private String Terminado ;
    private String jugadoresRespaldo;

    //CONSTRUCTORES
    public Partida(){
        this.IDPartida = UUID.randomUUID().toString();
        this.setTerminado(PartidaEstado.STATE_OPEN);
    }

    public Partida( String creator, String fecha, String jugadoresrespaldo){
        this.IDPartida = UUID.randomUUID().toString();
        this.Fecha = fecha;
        this.Creator = creator;
        this.setTerminado(PartidaEstado.STATE_OPEN);
        this.jugadoresRespaldo = jugadoresrespaldo;
    }

    //GETTERS
    public String getCreator() {
        return Creator;
    }
    public String getFecha() {
        return Fecha;
    }
    public String getIDPartida() {
        return IDPartida;
    }
    public String getTerminado() { return Terminado; }
    public String getJugadoresRespaldo() {  return jugadoresRespaldo; }
    public String get_ID() {
        return _ID;
    }

    //SETTERS
    public void setIDPartida(String IDPartida) { this.IDPartida = IDPartida; }
    public void setFecha(String fecha) { Fecha = fecha; }
    public void setCreator(String creator) { Creator = creator; }
    public void setTerminado(String cerrada) { this.Terminado = cerrada; }
    public void setJugadoresRespaldo(String jugadoresRespaldo) {  this.jugadoresRespaldo = jugadoresRespaldo; }
    public void set_ID(String _ID) {
        this._ID = _ID;
    }


    //variables a  content values
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(PartidaEsquema.PartidaEntry.IDPARTIDA, this.getIDPartida());
        values.put(PartidaEsquema.PartidaEntry.FECHA, this.getFecha());
        values.put(PartidaEsquema.PartidaEntry.CREADOR, this.getCreator());
        values.put(PartidaEsquema.PartidaEntry.TERMINADO, this.getTerminado());
        values.put(PartidaEsquema.PartidaEntry.JUGADORESRESPALDO, this.getJugadoresRespaldo());
        return values;
    }

}
