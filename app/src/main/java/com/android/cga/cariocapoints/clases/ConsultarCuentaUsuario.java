package com.android.cga.cariocapoints.clases;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by gutie on 14/01/2017.
 */

public class ConsultarCuentaUsuario  {


    private Owner owner;

    public ConsultarCuentaUsuario(Context context) {
        //guardamos contexto

        String Correo = "";
        String Nombre = "Usuario";
        String Telefono = "";
        String Id = "";


        Correo = getCorreoPrimario(context);
        Telefono = getNumeroCelular(context);

        owner = new Owner(Id,Nombre,Correo,Telefono);


    }



    public static String getCorreoPrimario(Context context) {
        String desconocido="desconocido";
        try {
            AccountManager accountManager = AccountManager.get(context);
            if (accountManager == null){

                Log.d("ACCOUNT","No existen cuentas registradas");
                return desconocido;
            }
            Account[] accounts = accountManager.getAccounts();
            Pattern emailPattern = Patterns.EMAIL_ADDRESS;
            for (Account account : accounts) {
                // make sure account.name is an email address before adding to the list
                if (emailPattern.matcher(account.name).matches()) {
                    Log.d("ACCOUNT","EXITO consultar usuario principal");
                    return account.name;
                }
            }
            Log.d("ACCOUNT","Ningun correo valido");
            return desconocido;
        } catch (SecurityException e) {
            // exception will occur if app doesn't have GET_ACCOUNTS permission
            Log.d("ACCOUNT","Sin permisos de cuenta");
            return desconocido;
        }
    }


    private String getNumeroCelular(Context context) {
        try {
            Log.d("ACCOUNT","EXITO al consultar telefono");
            TelephonyManager tMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            return  tMgr.getLine1Number();

        }catch (Exception exception){
            Log.d("ACCOUNT","ERROR al consultar telefono");
        }
        return "xxxxxxxxxxxx";
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
