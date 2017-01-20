package com.android.cga.cariocapoints.clases;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.android.cga.cariocapoints.R;

/**
 * Created by gutie on 15/01/2017.
 */

public class MisAlertas {

    private static AlertDialog.Builder aviso;

    public MisAlertas(){}

    public static void Show(Context context, String titulo, String mensaje){


        Log.d("MODAL","se carga modal");


        aviso = new AlertDialog.Builder(context);

        aviso.setTitle(titulo);
        aviso.setMessage(mensaje);
        aviso.setIcon(R.drawable.ic_menu_share);

        aviso.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                Log.d("MODAL","alertdialog acepta");

            }
        }).show();

    }




}
