package com.android.cga.cariocapoints.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.cga.cariocapoints.modelos.Jugador;
import com.android.cga.cariocapoints.modelos.JugadorEsquema.JugadorEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gutie on 11/01/2017.
 */

public class JugadorDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cariocapoints.db";



    /*Constructor*/
    public JugadorDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        onCreate(getReadableDatabase());
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + JugadorEntry.TABLE_NAME + " ("
                + JugadorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + JugadorEntry.IDJUGADOR + " TEXT NOT NULL, "
                + JugadorEntry.NOMBRE + " TEXT NOT NULL UNIQUE, "
                + "UNIQUE (" + JugadorEntry.IDJUGADOR + "))");
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
    public long insertarJugador(Jugador jugador) {

        Log.d("DATABASE","Se comienza a insertar jugador");

        SQLiteDatabase db = getWritableDatabase();

        return db.insert(
                JugadorEntry.TABLE_NAME,
                null,
                jugador.toContentValues());

    }

    /*Actualizar Información En La Base De Datos*/
    public long actualizarJugador(Jugador jugador) {

        Log.d("DATABASE","Se comienza a actualizar jugador");

        SQLiteDatabase db = getWritableDatabase();

        // Which row to update, based on the title
        String selection = JugadorEntry.IDJUGADOR + " LIKE ?";
        String[] selectionArgs = { JugadorEntry.IDJUGADOR, JugadorEntry.NOMBRE };

        return db.update(
                JugadorEntry.TABLE_NAME,
                jugador.toContentValues(),
                selection,
                selectionArgs);

    }


    /*Eliminar jugador por nombre*/
    public int eliminarJugador(String nombreJugador) {

        Log.d("DATABASE", "Eliminando Partida");

        return getWritableDatabase().delete(
                JugadorEntry.TABLE_NAME,
                JugadorEntry.NOMBRE + " LIKE ?",
                new String[]{nombreJugador});
    }

    /*Leer Información De La Base De Datos*/

    //retorna todas las jugadores guardadas
    public List<Jugador>  getJugadores(){

        Log.d("DATABASE","Se comienza a consultar jugadores");

        //lista con elementos de Retorno
        List<Jugador> miJugador = new ArrayList<>();

        try {
            //recupera DB
            SQLiteDatabase db = getReadableDatabase();

            //ejecuta consulta
            Cursor cursor = db.query(
                    JugadorEntry.TABLE_NAME,  // Nombre de la tabla
                    null,  // Lista de Columnas a consultar
                    null,  // Columnas para la cláusula WHERE
                    null,  // Valores a comparar con las columnas del WHERE
                    null,  // Agrupar con GROUP BY
                    null,  // Condición HAVING para GROUP BY
                    null  // Cláusula ORDER BY
            );


            //cursor elementos
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                //new objeto
                Jugador jugador = new Jugador();

                jugador.setIDJugador(cursor.getString(cursor.getColumnIndex(JugadorEntry.IDJUGADOR)));
                jugador.setNombreJugador(cursor.getString(cursor.getColumnIndex(JugadorEntry.NOMBRE)));
                jugador.set_ID(cursor.getString(cursor.getColumnIndex(JugadorEntry._ID)));

                //agrega a la lista
                miJugador.add(jugador);
                //siguiente elemento en
                cursor.moveToNext();
            }
            cursor.close();
        }catch (SQLiteException exsql){
            Log.d("DATABASE","No se encuentra la tabla "+JugadorEntry.TABLE_NAME);
        }
        return miJugador;

    }



    //Retorna una ronda conocida
    public Jugador getJugadorIndice(String ID){

        Log.d("DATABASE","Se consulta por un jugador");

        //elemento de Retorno
        Jugador miJugador = new Jugador();

        //recupera DB
        SQLiteDatabase db = getReadableDatabase();

        //condiciones
        String columns[] = new String[]{JugadorEntry._ID, JugadorEntry.IDJUGADOR, JugadorEntry.NOMBRE};
        String selection = JugadorEntry.IDJUGADOR + " LIKE ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{ID};

        //ejecuta consulta
        Cursor cursor = db.query(
                JugadorEntry.TABLE_NAME,  // Nombre de la tabla
                columns,  // Lista de Columnas a consultar
                selection,  // Columnas para la cláusula WHERE
                selectionArgs,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );

        //cursor a Ronda --- Debiese ser 1
        cursor.moveToFirst();
        miJugador.setIDJugador(cursor.getString(cursor.getColumnIndex(JugadorEntry.IDJUGADOR)));
        miJugador.setNombreJugador(cursor.getString(cursor.getColumnIndex(JugadorEntry.NOMBRE)));
        miJugador.set_ID(cursor.getString(cursor.getColumnIndex(JugadorEntry._ID)));
        cursor.close();


        return miJugador;
    }
}
