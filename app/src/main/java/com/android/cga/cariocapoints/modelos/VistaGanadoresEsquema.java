package com.android.cga.cariocapoints.modelos;

import android.provider.BaseColumns;

/**
 * Created by gutie on 11/01/2017.
 */

public class VistaGanadoresEsquema {
    public static abstract class VistaGanadoresEntry implements BaseColumns {
        public static final  String TABLE_NAME = "Ganadores Historia";

        public static final  String IDPARTIDA = "IDPartida";

        public static final  String FECHA = "Fecha";

        public static final  String NOMBREGANADOR = "NombreGanador";

        public static final  String PUNTOS = "Puntos";


    }
}
