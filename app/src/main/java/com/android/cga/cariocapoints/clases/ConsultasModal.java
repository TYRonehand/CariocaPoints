package com.android.cga.cariocapoints.clases;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.MessagePattern;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.cga.cariocapoints.R;
import com.android.cga.cariocapoints.helpers.PuntuacionDbHelper;
import com.android.cga.cariocapoints.modelos.Puntuacion;

/**
 * Created by gutie on 14/01/2017.
 */

public class ConsultasModal {

    private Puntuacion puntosJuego;
    private AlertDialog.Builder aviso;

    public ConsultasModal(final Activity context, String nombreJugador, final String IDRonda , final String IDPartida, final String IDJugador){


        Log.d("MODAL","se carga modal");

        puntosJuego = new Puntuacion();

        aviso = new AlertDialog.Builder(context);

        aviso.setTitle("Puntos Ronda");
        aviso.setMessage("Ingrese Puntos de "+nombreJugador);

        final EditText puntosTXT = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        puntosTXT.setLayoutParams(lp);
        puntosTXT.setInputType(InputType.TYPE_CLASS_NUMBER);
        aviso.setView(puntosTXT);
        aviso.setIcon(R.drawable.ic_menu_share);

        aviso.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Log.d("MODAL","alertdialog selecciona agregar puntos");

                        if(puntosTXT.getText().toString().length()>0) {

                            String puntos = puntosTXT.getText().toString();

                            puntosJuego.setIDRonda(IDRonda);
                            puntosJuego.setIDPartida(IDPartida);
                            puntosJuego.setIDJugador(IDJugador);
                            puntosJuego.setPuntos(puntos);

                            //puntuacion insertar
                            PuntuacionDbHelper puntuacionDbHelper = new PuntuacionDbHelper(context);
                            puntuacionDbHelper.insertarPuntuacion(puntosJuego);
                            puntuacionDbHelper.onCloseDb();


                        }else{
                            Log.d("MODAL", "puntos no ingresados");
                            Toast.makeText(context,"Ingrese puntos",Toast.LENGTH_LONG).show();
                        }

                    }
                });

        aviso.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d("MODAL","alertdialog se cancela");
                    }
                });
    }

    public void MostrarAviso(){
        aviso.show();
    }

    //getter
    public  Puntuacion getPuntosJuego() {
        return puntosJuego;
    }

    //setter
    public void setPuntosJuego(Puntuacion puntosJuego) {
        this.puntosJuego = puntosJuego;
    }



}
