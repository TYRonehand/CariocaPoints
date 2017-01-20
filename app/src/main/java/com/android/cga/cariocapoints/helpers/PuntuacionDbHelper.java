package com.android.cga.cariocapoints.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.cga.cariocapoints.modelos.PartidaEsquema;
import com.android.cga.cariocapoints.modelos.Puntuacion;
import com.android.cga.cariocapoints.modelos.PuntuacionEsquema.PuntuacionEntry;
import com.android.cga.cariocapoints.modelos.RondasEsquema;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gutie on 11/01/2017.
 */

public class PuntuacionDbHelper   extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cariocapoints.db";


    /*Constructor*/
    public PuntuacionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(getReadableDatabase());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PuntuacionEntry.TABLE_NAME + " ("
                + PuntuacionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PuntuacionEntry.IDPUNTUACION + " TEXT NOT NULL, "
                + PuntuacionEntry.IDJUGADOR + " TEXT NOT NULL, "
                + PuntuacionEntry.IDPARTIDA + " TEXT NOT NULL, "
                + PuntuacionEntry.IDRONDA + " TEXT NOT NULL, "
                + PuntuacionEntry.PUNTOS + " TEXT NOT NULL, "
                + "UNIQUE (" + PuntuacionEntry.IDPUNTUACION + "))");
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
    public long insertarPuntuacion(Puntuacion puntuacion) {

        SQLiteDatabase db = getWritableDatabase();

        return db.insert(
                PuntuacionEntry.TABLE_NAME,
                null,
                puntuacion.toContentValues());

    }

    /*Actualizar Información En La Base De Datos*/
    public long actualizarPuntuacion(Puntuacion puntuacion) {

        SQLiteDatabase db = getWritableDatabase();

        // Which row to update, based on the title
        String selection = PuntuacionEntry.IDPUNTUACION + " LIKE ?";
        String[] selectionArgs = { PuntuacionEntry.IDPUNTUACION, PuntuacionEntry.IDJUGADOR, PuntuacionEntry.IDPARTIDA, PuntuacionEntry.IDRONDA, PuntuacionEntry.PUNTOS };

        return db.update(
                PuntuacionEntry.TABLE_NAME,
                puntuacion.toContentValues(),
                selection,
                selectionArgs);


    }

    /*Leer Información De La Base De Datos*/

    //retorna todas las Puntuacion guardadas
    public List<Puntuacion> getPuntuacion(){

        //lista con elementos de Retorno
        List<Puntuacion> miPuntuacion = new ArrayList<>();

        //recupera DB
        SQLiteDatabase db = getReadableDatabase();

        //ejecuta consulta
        Cursor cursor = db.query(
                PuntuacionEntry.TABLE_NAME,  // Nombre de la tabla
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
            Puntuacion puntuacion = new Puntuacion();

            puntuacion.setIDPuntuacion( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDPUNTUACION)) );
            puntuacion.setIDJugador( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDJUGADOR)) );
            puntuacion.setIDPartida( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDPARTIDA)) );
            puntuacion.setIDRonda( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDRONDA)) );
            puntuacion.setPuntos( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.PUNTOS)) );

            //agrega a la lista
            miPuntuacion.add( puntuacion );
            //siguiente elemento en
            cursor.moveToNext();
        }
        cursor.close();

        return miPuntuacion;
    }


    //Retorna una Puntuacion conocida
    public Puntuacion getPuntuacionIndice(String idpuntuacion){

        //elemento de Retorno
        Puntuacion miPuntuacion = new Puntuacion();

        //recupera DB
        SQLiteDatabase db = getReadableDatabase();

        //condiciones
        String columns[] = new String[]{PuntuacionEntry.IDPUNTUACION, PuntuacionEntry.IDJUGADOR, PuntuacionEntry.IDPARTIDA, PuntuacionEntry.IDRONDA, PuntuacionEntry.PUNTOS};
        String selection = PuntuacionEntry.IDPUNTUACION + " LIKE ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{idpuntuacion};

        //ejecuta consulta
        Cursor cursor = db.query(
                PuntuacionEntry.TABLE_NAME,  // Nombre de la tabla
                columns,  // Lista de Columnas a consultar
                selection,  // Columnas para la cláusula WHERE
                selectionArgs,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );

        //cursor a Puntuacion --- Debiese ser 1
        cursor.moveToFirst();
        miPuntuacion.set_ID( cursor.getString(cursor.getColumnIndex(PuntuacionEntry._ID)) );
        miPuntuacion.setIDPuntuacion(cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDPUNTUACION)));
        miPuntuacion.setIDJugador(cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDJUGADOR)));
        miPuntuacion.setIDPartida(cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDPARTIDA)));
        miPuntuacion.setIDRonda(cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDRONDA)));
        miPuntuacion.setPuntos(cursor.getString(cursor.getColumnIndex(PuntuacionEntry.PUNTOS)));
        cursor.close();


        return miPuntuacion;
    }



    public List<Puntuacion> getPuntuacionPartida(String _IDpartida){

        //lista con elementos de Retorno
        List<Puntuacion> miPuntuacion = new ArrayList<>();

        //recupera DB
        SQLiteDatabase db = getReadableDatabase();

        //condiciones
        String columns[] = new String[]{PuntuacionEntry._ID, PuntuacionEntry.IDPUNTUACION, PuntuacionEntry.IDJUGADOR, PuntuacionEntry.IDPARTIDA, PuntuacionEntry.IDRONDA, PuntuacionEntry.PUNTOS};
        String selection = PuntuacionEntry.IDPARTIDA + " LIKE ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{_IDpartida};

        //ejecuta consulta
        Cursor cursor = db.query(
                PuntuacionEntry.TABLE_NAME,  // Nombre de la tabla
                columns,  // Lista de Columnas a consultar
                selection,  // Columnas para la cláusula WHERE
                selectionArgs,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );

        //cursor elementos
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            //new objeto
            Puntuacion puntuacion = new Puntuacion();

            puntuacion.set_ID( cursor.getString(cursor.getColumnIndex(PuntuacionEntry._ID)) );
            puntuacion.setIDPuntuacion( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDPUNTUACION)) );
            puntuacion.setIDJugador( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDJUGADOR)) );
            puntuacion.setIDPartida( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDPARTIDA)) );
            puntuacion.setIDRonda( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.IDRONDA)) );
            puntuacion.setPuntos( cursor.getString(cursor.getColumnIndex(PuntuacionEntry.PUNTOS)) );

            //agrega a la lista
            miPuntuacion.add( puntuacion );
            //siguiente elemento en
            cursor.moveToNext();
        }
        cursor.close();

        return miPuntuacion;


    }

}
