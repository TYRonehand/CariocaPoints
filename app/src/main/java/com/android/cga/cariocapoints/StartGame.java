package com.android.cga.cariocapoints;

import android.content.Context;
import android.icu.text.MessagePattern;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.cga.cariocapoints.clases.ConsultarCuentaUsuario;
import com.android.cga.cariocapoints.clases.ConsultasModal;
import com.android.cga.cariocapoints.clases.MisAlertas;
import com.android.cga.cariocapoints.helpers.JugadorDbHelper;
import com.android.cga.cariocapoints.helpers.PartidaDbHelper;
import com.android.cga.cariocapoints.helpers.PuntuacionDbHelper;
import com.android.cga.cariocapoints.helpers.RondasDbHelper;
import com.android.cga.cariocapoints.modelos.Jugador;
import com.android.cga.cariocapoints.clases.Owner;
import com.android.cga.cariocapoints.modelos.Partida;
import com.android.cga.cariocapoints.modelos.Puntuacion;
import com.android.cga.cariocapoints.modelos.Rondas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StartGame.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StartGame#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartGame extends Fragment implements View.OnClickListener{

    private JugadorDbHelper jugadorDbHelper;
    private RondasDbHelper rondaDbHelper;
    private PartidaDbHelper partidaDbHelper;

    private ListView jugadoresListView;
    private Spinner rondasSpinner;
    private Button terminarPartida;

    private List<Jugador> listaJugadoresDB;
    private List<Rondas> listaRondasDB;

    private  Partida partidaActual;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public StartGame() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment StartGame.
     */
    // TODO: Rename and change types and number of parameters
    public static StartGame newInstance(String param1) {
        StartGame fragment = new StartGame();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_start_game, container, false);



        //cargar listview lista de nombres jugadores
        this.jugadoresListView = (ListView) myView.findViewById(R.id.jugadoresListViewSG);

        //combobox con rondas
        this.rondasSpinner = (Spinner) myView.findViewById(R.id.objetivoSpinner);

        //boton terminar partida
        this.terminarPartida = (Button) myView.findViewById(R.id.terminarBtn);
        this.terminarPartida.setOnClickListener(this);
        this.terminarPartida.setEnabled(false);;//deshabilita terminar partida

        //consulta las rondas
        ConsultarRondas();

        //consulta los jugadores existentes y determina si crear la partida
        ConsultarJugadores();



        return myView;
    }


    private void  ConsultarRondas(){

        // Instancia de helper
        this.rondaDbHelper = new RondasDbHelper(getContext());
        rondaDbHelper.LoadDataRondas();//carga datos
        listaRondasDB = rondaDbHelper.getRondas();
        rondaDbHelper.onCloseDb();


        //Lista con rondas
        List<String> nombres = new ArrayList<>();
        for (Rondas ronda: listaRondasDB ){
            nombres.add(ronda.getNombre());
        }

        //adapter for Spinner drop down list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, nombres);
        rondasSpinner.setAdapter(adapter);

    }

    /*CONSULTA TODOS LOS JUGADORES REGISTRADOS*/
    private void ConsultarJugadores() {

        Log.d("STARTGAME", "Consultar todos los jugadores y agregar a listView");

        //recupera jugadores
        this.jugadorDbHelper = new JugadorDbHelper(getContext());
        listaJugadoresDB = jugadorDbHelper.getJugadores();
        jugadorDbHelper.onCloseDb();

        //lista nombres
        List<String> nombresJugadores = new ArrayList<>();
        for (Jugador miJugador : listaJugadoresDB) {
            nombresJugadores.add(miJugador.getNombreJugador());
            Log.d("STARTGAME","     idJugador: "+miJugador.get_ID());
        }

        //agrega al listview de jugadores
        this.jugadoresListView.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_expandable_list_item_1, nombresJugadores));


        Log.d("VARIABLE_VALUE"," Total: "+listaJugadoresDB.size());

        //Valida el numero de jugadores antes de inicial
        if (listaJugadoresDB.size() < 2 ){

            Log.d("STARTGAME","Partida no cumple con el minimo jugadores");
            MisAlertas.Show(getContext(), "Atención","Se necesitan minimo 2 jugadores");

        }else {

            Log.d("STARTGAME","Partida SI cumple");
            //crea la partida
            this.partidaActual = this.CrearPartida();


            //evento clic a lista de jugadores
            this.jugadoresListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    GuardarPuntosPartida(position);
                }
            });


            //habilita terminar partida
            terminarPartida.setEnabled(true);
        }

    }

    /*Guardar Puntos*/
    private void GuardarPuntosPartida(int position){

        Log.d("STARTGAME","ListaJugador sleccion jugadores");

        String IDRonda="", IDPartida="", IDJugador="";

        Log.d("STARTGAME","consulta nombre jugador");
        String nombreJugador = jugadoresListView.getAdapter().getItem(position).toString();


        //guarda id partida
        IDPartida = partidaActual.get_ID();
        Log.d("STARTGAME","guarda idpartida: "+IDPartida);

        //busca id de ronda seleccionada

        for (Rondas ronda: listaRondasDB ){

            int posicionRondaSeleccionada = rondasSpinner.getSelectedItemPosition();

            if(ronda.getNombre().equals(rondasSpinner.getItemAtPosition(posicionRondaSeleccionada))){
                IDRonda = ronda.get_ID();
                Log.d("STARTGAME","guarda idronda: "+IDRonda);
            }
        }


        //busca id de jugador seleccionado
        for (Jugador jugador: listaJugadoresDB ){

            if(nombreJugador.equals(jugador.getNombreJugador())){
                IDJugador = jugador.get_ID();
                Log.d("STARTGAME","guarda idjugador: "+IDJugador);
            }
        }


        Log.d("STARTGAME","Se procede a guardar partida ");
        if(!IDRonda.isEmpty() && !IDJugador.isEmpty() && !IDPartida.isEmpty()){
            ConsultasModal modal = new ConsultasModal(getActivity(), nombreJugador, IDRonda, IDPartida, IDJugador);
            modal.MostrarAviso();
        }

    }

    /*crea una partida nueva*/
    private Partida CrearPartida(){

        //nueva fecha
        Log.d("STARTGAME","Crear una partida nueva");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String miFechaActual = mdformat.format(calendar.getTime());

        //consultar Cuenta usuario
        Log.d("STARTGAME","get owner");
        ConsultarCuentaUsuario cu = new ConsultarCuentaUsuario(getContext());
        Owner cuentaUsuario = cu.getOwner();


        //jugadores de respaldo
        Log.d("STARTGAME","cargar jugadores respaldo");
        String respJugadores = "";
        for (Jugador miJugador : listaJugadoresDB) {
            respJugadores += miJugador.get_ID() + Partida.PartidaEstado.DELIMITATOR_ID_NAME
                                    + miJugador.getNombreJugador() + Partida.PartidaEstado.DELIMITATOR_INFO_PLAYER;

        }

        Log.d("STARTGAME","instanciado Nueva Partida Objeto e insertando a la DB");
        //crear nueva Partida
        Partida nuevaPartida = new Partida();
        nuevaPartida.setCreator(cuentaUsuario.getEmailOwner());
        nuevaPartida.setFecha(miFechaActual);
        nuevaPartida.setJugadoresRespaldo(respJugadores);

        //insertamos partida en la base datos
        partidaDbHelper = new PartidaDbHelper(getContext());
        partidaDbHelper.insertarPartida(nuevaPartida);
        //cargamos la partida actual con fin de obtener _ID generado por la base de datos
        nuevaPartida = partidaDbHelper.getPartidaIndice(nuevaPartida.getIDPartida());
        partidaDbHelper.onCloseDb();

        //aviso
        MisAlertas.Show(getContext(), "Partida Nueva","Partida creada");


        return nuevaPartida;

    }

    /*cierra la partida actual*/
    private void CerrarPartida(){

        Log.d("STARTGAME","Cerrando partida actual");

        //se cierra la partida
        this.partidaActual.setTerminado(Partida.PartidaEstado.STATE_CLOSED);//cambia estado al objeto

        this.partidaDbHelper = new PartidaDbHelper(getContext());//instancia y carga la bd
        partidaDbHelper.actualizarPartida(partidaActual);//actualiza base datos con objeto
        partidaDbHelper.onCloseDb();//cierra base datos


        MisAlertas.Show(getContext(), "Aviso","Partida actual finalizada");

        //cierra el juego y se dirige a puntuaciones
        getFragmentManager().beginTransaction().replace(R.id.Contenedor, new ScorePanel()).addToBackStack(null).commit();

    }


    //control de botones
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.terminarBtn:
                CerrarPartida();
                break;
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
