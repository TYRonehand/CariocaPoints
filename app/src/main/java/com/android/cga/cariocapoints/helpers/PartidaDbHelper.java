package com.android.cga.cariocapoints.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.cga.cariocapoints.modelos.Partida;
import com.android.cga.cariocapoints.modelos.PartidaEsquema.PartidaEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gutie on 11/01/2017.
 */

public class PartidaDbHelper  extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cariocapoints.db";


    /*Constructor*/
    public PartidaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(getReadableDatabase());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PartidaEntry.TABLE_NAME + " ("
                + PartidaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PartidaEntry.IDPARTIDA + " DATE NOT NULL,"
                + PartidaEntry.FECHA + " DATE NOT NULL, "
                + PartidaEntry.CREADOR + " TEXT NOT NULL, "
                + PartidaEntry.TERMINADO + " TEXT NOT NULL, "
                + PartidaEntry.JUGADORESRESPALDO + " TEXT NOT NULL, "
                + "UNIQUE (" + PartidaEntry.IDPARTIDA + "))");
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

    /*Insertar Información En La Base De Datos*/
    public long insertarPartida(Partida partida) {

        Log.d("DATABASE","Se comienza a insertar partida");

        SQLiteDatabase db = getWritableDatabase();

        return db.insert(
                PartidaEntry.TABLE_NAME,
                null,
                partida.toContentValues());

    }

    /*Actualizar Información En La Base De Datos*/
    public long actualizarPartida(Partida partida) {

        Log.d("DATABASE","Se comienza a actualizar jugador");

        SQLiteDatabase db = getWritableDatabase();

        // Which row to update, based on the title
        String selection = PartidaEntry.IDPARTIDA + " LIKE ?";
        String[] selectionArgs = {partida.getIDPartida()};

        return db.update(
                PartidaEntry.TABLE_NAME,
                partida.toContentValues(),
                selection,
                selectionArgs);


    }


    /*Eliminar jugador por nombre*/
    public int eliminarPartida(String idpartida) {

        Log.d("DATABASE", "Eliminando Partida");

        return getWritableDatabase().delete(
                PartidaEntry.TABLE_NAME,
                PartidaEntry.IDPARTIDA + " LIKE ?",
                new String[]{idpartida});
    }


    /*Leer Información De La Base De Datos*/

    //retorna todas las rondas guardadas
    public List<Partida> getPartida(){

        Log.d("DATABASE","Se consultan partidas");

        //lista con elementos de Retorno
        List<Partida> miPartida = new ArrayList<>();
        //recupera DB
        SQLiteDatabase db = getReadableDatabase();

        //ejecuta consulta
        Cursor cursor = db.query(
                PartidaEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );


        //cursor elementos
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            //new objeto
            Partida partida = new Partida();
            partida.set_ID(cursor.getString(cursor.getColumnIndex(PartidaEntry._ID)));
            partida.setIDPartida(cursor.getString(cursor.getColumnIndex(PartidaEntry.IDPARTIDA)));
            partida.setFecha(cursor.getString(cursor.getColumnIndex(PartidaEntry.FECHA)));
            partida.setCreator( cursor.getString(cursor.getColumnIndex(PartidaEntry.CREADOR)) );
            partida.setTerminado( cursor.getString(cursor.getColumnIndex(PartidaEntry.TERMINADO)) );
            partida.setJugadoresRespaldo( cursor.getString(cursor.getColumnIndex(PartidaEntry.JUGADORESRESPALDO)) );

            //agrega a la lista
            miPartida.add( partida );
            //siguiente elemento en
            cursor.moveToNext();
        }
        cursor.close();


        return miPartida;
    }
    //Retorna una ronda conocida
    public Partida getPartidaIndice(String idpartida){

        Log.d("DATABASE","Se consulta por una partida");

        //elemento de Retorno
        Partida miPartida = new Partida();

        //recupera DB
        SQLiteDatabase db = getReadableDatabase();

        //condiciones
        String columns[] = new String[]{ PartidaEntry._ID,PartidaEntry.IDPARTIDA, PartidaEntry.FECHA, PartidaEntry.CREADOR , PartidaEntry.TERMINADO , PartidaEntry.JUGADORESRESPALDO};
        String selection = PartidaEntry.IDPARTIDA + " LIKE ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{idpartida};

        //ejecuta consulta
        Cursor cursor = db.query(
                PartidaEntry.TABLE_NAME,  // Nombre de la tabla
                columns,  // Lista de Columnas a consultar
                selection,  // Columnas para la cláusula WHERE
                selectionArgs,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );

        //cursor a Partida --- Debiese ser 1
        cursor.moveToFirst();
        miPartida.set_ID(cursor.getString(cursor.getColumnIndex(PartidaEntry._ID)));
        miPartida.setIDPartida(cursor.getString(cursor.getColumnIndex(PartidaEntry.IDPARTIDA)));
        miPartida.setFecha(cursor.getString(cursor.getColumnIndex(PartidaEntry.FECHA)));
        miPartida.setCreator(cursor.getString(cursor.getColumnIndex(PartidaEntry.CREADOR)));
        miPartida.setTerminado(cursor.getString(cursor.getColumnIndex(PartidaEntry.TERMINADO)));
        miPartida.setJugadoresRespaldo(cursor.getString(cursor.getColumnIndex(PartidaEntry.JUGADORESRESPALDO)));
        cursor.close();


        return miPartida;
    }

}
