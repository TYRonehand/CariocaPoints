package com.android.cga.cariocapoints.modelos;

import android.provider.BaseColumns;

/**
 * Created by gutie on 11/01/2017.
 */

public class PartidaEsquema {
    public static abstract class PartidaEntry implements BaseColumns{
        public static final  String TABLE_NAME = "Partida";

        public static final  String IDPARTIDA = "IDPartida";

        public static final  String FECHA = "Fecha";

        public static final  String CREADOR = "Creador";

        public static final  String TERMINADO = "Terminado";

        public static final  String JUGADORESRESPALDO = "JugadoresRespaldo";




    }
}
