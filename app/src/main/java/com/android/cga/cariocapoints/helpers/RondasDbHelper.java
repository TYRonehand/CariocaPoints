package com.android.cga.cariocapoints.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.android.cga.cariocapoints.modelos.Rondas;
import com.android.cga.cariocapoints.modelos.RondasEsquema;
import com.android.cga.cariocapoints.modelos.RondasEsquema.RondasEntry;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gutie on 11/01/2017.
 */

public class RondasDbHelper  extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cariocapoints.db";



    /*Constructor*/
    public RondasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("STATE","Se inicio el RondasDbHelper");
        onCreate(getReadableDatabase());
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RondasEntry.TABLE_NAME + " ("
                + RondasEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RondasEntry.IDRONDA + " TEXT NOT NULL, "
                + RondasEntry.NOMBRERONDA + " TEXT NOT NULL UNIQUE, " +
                "UNIQUE (" + RondasEntry.IDRONDA +"))");

        Log.d("STATE","Se crea la Base de DATOS rondas");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RondasEntry.TABLE_NAME);
        onCreate(db);
    }

    // closing database
    public void onCloseDb() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /*Crear rondas de prueba*/
    public void LoadDataRondas() {
        Log.d("STATE","INSERTANDO datos prueba");

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + RondasEntry.TABLE_NAME);
        onCreate(db);
        try {


            insertRonda(new Rondas("2 TRIOS"));
            insertRonda(new Rondas("1 TRIO + 1 ESCALA"));
            insertRonda(new Rondas("2 ESCALAS"));
            insertRonda(new Rondas("3 TRIOS"));
            insertRonda(new Rondas("2 TRIOS + 1 ESCALA"));
            insertRonda(new Rondas("2 ESCALAS + 1 TRIO"));
            insertRonda(new Rondas("4 TRIOS"));
            insertRonda(new Rondas("3 ESCALAS"));
            insertRonda(new Rondas("ESCALA SUCIA"));
            insertRonda(new Rondas("ESCALA REAL"));

            Log.d("STATE", "Se Insertaron datos prueba");
        }catch (SQLiteException ex){

            Log.d("STATE","Fallo la inserccion: "+ex.toString());

        }
    }

    /*Insertar Información En La Base De Datos*/
    public long insertRonda(Rondas ronda) {

        SQLiteDatabase db = getWritableDatabase();

        return db.insert(
                        RondasEntry.TABLE_NAME,
                        null,
                        ronda.toContentValues());

    }

    /*Actualizar Información En La Base De Datos*/
    public long updateRonda(Rondas ronda) {

        SQLiteDatabase db = getWritableDatabase();

        // Which row to update, based on the title
        String selection = RondasEntry.IDRONDA + " LIKE ?";
        String[] selectionArgs = { RondasEntry.NOMBRERONDA };

        return db.update(
                RondasEntry.TABLE_NAME,
                ronda.toContentValues(),
                selection,
                selectionArgs);


    }

    /*Leer Información De La Base De Datos*/

    //retorna todas las rondas guardadas
    public List<Rondas> getRondas(){

        //lista con elementos de Retorno
        List<Rondas> miRonda = new ArrayList<>();
        //recupera DB
        SQLiteDatabase db = getReadableDatabase();

        //ejecuta consulta
        Cursor cursor = db.query(
                RondasEntry.TABLE_NAME,  // Nombre de la tabla
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
            Rondas ronda = new Rondas();
            ronda.set_ID(cursor.getString(cursor.getColumnIndex(RondasEntry._ID)));
            ronda.setIDRonda(cursor.getString(cursor.getColumnIndex(RondasEntry.IDRONDA)));
            ronda.setNombre(cursor.getString(cursor.getColumnIndex(RondasEsquema.RondasEntry.NOMBRERONDA)));
            //agrega a la lista
            miRonda.add( ronda );
            //siguiente elemento en
            cursor.moveToNext();
        }
        cursor.close();


        return miRonda;
    }

    //Retorna una ronda conocida
    public Rondas getRondaIndice(String ID){

        //elemento de Retorno
        Rondas miRonda = new Rondas();

        //recupera DB
        SQLiteDatabase db = getReadableDatabase();

        //condiciones
        String columns[] = new String[]{RondasEntry._ID, RondasEntry.IDRONDA, RondasEntry.NOMBRERONDA};
        String selection = RondasEntry.IDRONDA + " LIKE ?"; // WHERE id LIKE ?
        String selectionArgs[] = new String[]{ID};

        //ejecuta consulta
        Cursor cursor = db.query(
                RondasEntry.TABLE_NAME,  // Nombre de la tabla
                columns,  // Lista de Columnas a consultar
                selection,  // Columnas para la cláusula WHERE
                selectionArgs,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );

        //cursor a Ronda --- Debiese ser 1
        cursor.moveToFirst();
        miRonda.setIDRonda(cursor.getString(cursor.getColumnIndex(RondasEntry._ID)));
        miRonda.setIDRonda(cursor.getString(cursor.getColumnIndex(RondasEntry.IDRONDA)));
        miRonda.setNombre(cursor.getString(cursor.getColumnIndex(RondasEntry.NOMBRERONDA)));
        cursor.close();


        return miRonda;
    }


}
