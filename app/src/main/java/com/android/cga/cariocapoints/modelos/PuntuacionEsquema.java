package com.android.cga.cariocapoints.modelos;

import android.provider.BaseColumns;

/**
 * Created by gutie on 11/01/2017.
 */

public class PuntuacionEsquema {
    public static abstract class PuntuacionEntry implements BaseColumns {
        public static final  String TABLE_NAME = "Puntuacion";

        public static final  String IDPUNTUACION = "IDPuntuacion";

        public static final  String IDPARTIDA = "IDPartida";

        public static final  String IDJUGADOR = "IDJugador";

        public static final  String IDRONDA = "IDRonda";

        public static final  String PUNTOS = "Puntos";


    }
}
