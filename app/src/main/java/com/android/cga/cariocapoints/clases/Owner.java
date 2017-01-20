package com.android.cga.cariocapoints.clases;

/**
 * Created by gutie on 14/01/2017.
 */

public class Owner {


    private String IDowner;
    private String NombreOwner;
    private String EmailOwner;
    private String FonoOwner;

    public Owner(String id, String nombre, String email,String fono){
        this.IDowner = id;
        this.EmailOwner = email;
        this.NombreOwner = nombre;
        this.FonoOwner = fono;
    }


    public String getIDowner() {
        return IDowner;
    }

    public void setIDowner(String IDowner) {
        this.IDowner = IDowner;
    }

    public String getNombreOwner() {
        return NombreOwner;
    }

    public void setNombreOwner(String nombreOwner) {
        NombreOwner = nombreOwner;
    }

    public String getEmailOwner() {
        return EmailOwner;
    }

    public void setEmailOwner(String emailOwner) {
        EmailOwner = emailOwner;
    }

    public String getFonoOwner() {
        return FonoOwner;
    }

    public void setFonoOwner(String fonoOwner) {
        FonoOwner = fonoOwner;
    }

}
