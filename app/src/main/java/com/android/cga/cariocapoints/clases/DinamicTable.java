package com.android.cga.cariocapoints.clases;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import com.android.cga.cariocapoints.R;
import com.android.cga.cariocapoints.helpers.JugadorDbHelper;
import com.android.cga.cariocapoints.helpers.PartidaDbHelper;
import com.android.cga.cariocapoints.helpers.PuntuacionDbHelper;
import com.android.cga.cariocapoints.helpers.RondasDbHelper;
import com.android.cga.cariocapoints.modelos.Jugador;
import com.android.cga.cariocapoints.modelos.Partida;
import com.android.cga.cariocapoints.modelos.Puntuacion;
import com.android.cga.cariocapoints.modelos.Rondas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gutie on 15/01/2017.
 */

public class DinamicTable {


    private PuntuacionDbHelper puntuacionDbHelper;
    private RondasDbHelper rondasDbHelper;
    private JugadorDbHelper jugadorDbHelper;
    private PartidaDbHelper partidaDbHelper;


    private String[][] informacionTabla;
    private Number[] totalResultado;
    private int posicionGanador;

    private int SizeX=0,SizeY=0;

    private List<Puntuacion> listaPuntuaciones;
    private List<Rondas> listaRondas;
    private List<Jugador> listaJugador;

    public DinamicTable(Context context, Partida partida){


        Log.d("DINAMICTABLE","---Consulta jugadores");
        listaJugador = new ArrayList<>();

        String nombresJugadorRespaldo = partida.getJugadoresRespaldo();

        //carga jugadores de lista jugadoresRespaldo en Partida
        String[] jugadorRespaldo = nombresJugadorRespaldo.split(Partida.PartidaEstado.DELIMITATOR_INFO_PLAYER);

        for(String infoJugador :jugadorRespaldo){
            //corta info jugador en 2 para obtener idjugador y nombre jugador
            String[] auxJugador = infoJugador.split(Partida.PartidaEstado.DELIMITATOR_ID_NAME);

            Log.d("DINAMICTABLE","idjugador: "+auxJugador[0]+" nombrejugador: "+auxJugador[1] );
            //creamos jugador
            Jugador aux = new Jugador();
            aux.set_ID(auxJugador[0]);
            aux.setNombreJugador(auxJugador[1]);
            //agregamos a la lista
            listaJugador.add(aux);
        }


        Log.d("DINAMICTABLE","---Consulta puntuacion de PARTIDA: "+partida.get_ID());
        //puntuaciones de una partida x
        puntuacionDbHelper = new PuntuacionDbHelper(context);
        listaPuntuaciones = puntuacionDbHelper.getPuntuacionPartida(partida.get_ID());
        puntuacionDbHelper.onCloseDb();

        Log.d("DINAMICTABLE","    ->listaPuntuaciones size: "+listaPuntuaciones.size());



        //todas las rondas
        rondasDbHelper = new RondasDbHelper(context);
        listaRondas = rondasDbHelper.getRondas();
        rondasDbHelper.onCloseDb();
        Log.d("DINAMICTABLE","---listaRondas size: "+listaRondas.size());


        //table string
        this.setSizeY(listaJugador.size()+1);
        this.setSizeX(listaRondas.size()+1);

        informacionTabla = new String[this.getSizeX()][this.getSizeY()];


        Log.d("DINAMICTABLE","empieza a ordenar informacion asociada a los actores");
        //ordena los puntajes dentro de una matriz y asigna cabeceras
        int i=0;
        for(Jugador jugador:listaJugador){


            String idjugador = jugador.get_ID();
            informacionTabla[0][i+1] = jugador.getNombreJugador();

            int j=0;
            for (Rondas ronda: listaRondas){

                String idronda = ronda.get_ID();
                informacionTabla[j+1][0] = ronda.getNombre();

                for (Puntuacion puntos: listaPuntuaciones) {

                    Log.d("DINAMICTABLE","!!!!!!!!!!!!!!!!!!!!!!!\nidpuntuacion: "+puntos.get_ID());
                    Log.d("DINAMICTABLE","                       \npuntos: "+puntos.getPuntos());
                    Log.d("DINAMICTABLE","                       \nidjugador: "+jugador.get_ID()+"\nidjugador: "+puntos.getIDJugador());
                    Log.d("DINAMICTABLE","                       \nidpartida: "+partida.get_ID()+"\nidpartida: "+puntos.getIDPartida());
                    Log.d("DINAMICTABLE","                       \nidronda: "+ronda.get_ID()+"\nidronda: "+puntos.getIDRonda());

                    if(puntos.getIDJugador().equals(idjugador) && puntos.getIDRonda().equals(idronda)){

                        Log.d("DINAMICTABLE","IGUALES");
                        informacionTabla[j+1][i+1] = puntos.getPuntos();

                    }else{

                    }
                }

               j++;//incremento
            }
            i++;//incremento
        }

    }


    public Number[] getTotalPuntos(){


        totalResultado = new Number[getSizeY()];

        //ordena los puntajes dentro de una matriz y asigna cabeceras
        int i=1;
        for(Jugador jugador:listaJugador){

            int j=1;
            int suma=0;
            for (Rondas ronda: listaRondas){

                try {
                    suma += Integer.parseInt(informacionTabla[j][i]);
                }catch (Exception ex){
                    suma += 0;
                }

                j++;//incremento
            }
            totalResultado[i]=suma;
            i++;//incremento
        }
        Log.d("DINAMICTABLE","Consulta Resultados totales de puntajes: "+totalResultado[1]);
        return totalResultado;
    }


    public int BuscarPosicionGanador(){

        Log.d("DINAMICTABLE","Busca ganador con menor puntaje");

        int posicion = 1;

        int menor = totalResultado[1].intValue();


        for(int i = 1; i < getSizeY(); i++){

            if(totalResultado[i].intValue()<menor) {

                menor = totalResultado[i].intValue();
                posicion = i;
            }
        }

        Log.d("DINAMICTABLE","ganador posicion: "+posicion);
        return posicion;

    }

    public String[][] getInformacionTabla() {
        return informacionTabla;
    }

    public int getSizeX() {
        return SizeX;
    }

    public void setSizeX(int sizeX) {
        SizeX = sizeX;
    }

    public int getSizeY() {
        return SizeY;
    }

    public void setSizeY(int sizeY) {
        SizeY = sizeY;
    }

    public int getPosicionGanador() {
        return posicionGanador;
    }

    public void setPosicionGanador(int posicionGanador) {
        this.posicionGanador = posicionGanador;
    }




}
