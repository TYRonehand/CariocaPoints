package com.android.cga.cariocapoints.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.cga.cariocapoints.modelos.JugadorEsquema.JugadorEntry;
import com.android.cga.cariocapoints.modelos.Partida;
import com.android.cga.cariocapoints.modelos.PartidaEsquema.PartidaEntry;
import com.android.cga.cariocapoints.modelos.PuntuacionEsquema.PuntuacionEntry;
import com.android.cga.cariocapoints.modelos.VistaGanadoresEsquema.VistaGanadoresEntry;

/**
 * Created by gutie on 11/01/2017.
 */

public class VistaGanadoresDbHelper   extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cariocapoints.db";


    /*Constructor*/
    public VistaGanadoresDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(getReadableDatabase());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE VIEW IF NOT EXISTS " + VistaGanadoresEntry.TABLE_NAME + " AS (" +
                " SELECT " + PartidaEntry.IDPARTIDA
                +","+PartidaEntry.FECHA
                +","+JugadorEntry.NOMBRE
                +",SUM("+PuntuacionEntry.PUNTOS+")"
                +" FROM "+ PartidaEntry.TABLE_NAME+" pa, "+ PuntuacionEntry.TABLE_NAME+" pu, "+ JugadorEntry.TABLE_NAME+" ju"
                +" WHERE pa."+ PartidaEntry.IDPARTIDA+" = pu."+ PuntuacionEntry.IDPARTIDA
                +" AND pu."+ PuntuacionEntry.IDJUGADOR+" = ju."+ JugadorEntry.IDJUGADOR
                +" GROUP BY "+ PartidaEntry.IDPARTIDA
                +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // create new tables
        onCreate(db);
    }

    // closing database
    public void onCloseDb() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}