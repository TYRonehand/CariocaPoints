package com.android.cga.cariocapoints.modelos;

import android.provider.BaseColumns;

/**
 * Created by gutie on 11/01/2017.
 */

public class RondasEsquema {
    public static abstract class RondasEntry implements BaseColumns {
        public static final  String TABLE_NAME = "Rondas";

        public static final  String IDRONDA = "IDRonda";

        public static final  String NOMBRERONDA = "NombreRonda";


    }
}
