package com.android.cga.cariocapoints;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cga.cariocapoints.helpers.JugadorDbHelper;
import com.android.cga.cariocapoints.modelos.Jugador;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewPlayers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewPlayers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPlayers extends Fragment implements View.OnClickListener {


    private JugadorDbHelper jugadorDbHelper;
    private TextView jugadoresTextView;
    private ListView jugadoresListView;
    private Button agregarBtn;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewPlayers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPlayers.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPlayers newInstance(String param1, String param2) {
        NewPlayers fragment = new NewPlayers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_new_players, container, false);


        //load textview nombre jugador
        this.jugadoresTextView = (TextView) myView.findViewById(R.id.nombreTextView);

        //load listview lista de nombres jugadores
        this.jugadoresListView = (ListView) myView.findViewById(R.id.jugadoresListView);
        this.jugadoresListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EliminarJugador(position);
            }
        });



        //load button lista de nombres jugadores
        this.agregarBtn = (Button) myView.findViewById(R.id.agregarnombreBtn);
        this.agregarBtn.setOnClickListener(this);

        //consulta los jugadores existentes
        ConsultarJugadores();


        return myView;
    }



    private void AgregarJugador(){

        Log.d("NEWPLAYER", "AgregarJugador");

        //insertamos nuevo jugador
        try {
            if(jugadoresTextView.getText().toString().length()>=3) {

                Log.d("EVENT", "Nombre Valido");
                //nuevo jugador
                Jugador nuevoJugador = new Jugador(jugadoresTextView.getText().toString().toUpperCase());
                //inserta

                // Instancia de helper
                this.jugadorDbHelper = new JugadorDbHelper(getContext());
                this.jugadorDbHelper.insertarJugador(nuevoJugador);
                this.jugadorDbHelper.onCloseDb();


                //limpia texto y cierra teclado
                jugadoresTextView.setText("");
                View view = this.getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }else{

                Log.d("NEWPLAYER", "Nombre Invalido");
                Toast.makeText(this.getActivity(),"Ingrese un nombre de 3 caracteres min.",Toast.LENGTH_LONG).show();
            }
        }catch (SQLiteException ex){

            Log.d("NEWPLAYER", "Nombre Existe");
            Toast.makeText(this.getActivity(),"El Jugador ya existe",Toast.LENGTH_LONG).show();

        }
        //actualiza la lista de jugadores
        ConsultarJugadores();
    }

    private void EliminarJugador( int position) {

        Log.d("NEWPLAYER", "EliminarJugador");
        ArrayAdapter<String> myAdapter = (ArrayAdapter<String>) jugadoresListView.getAdapter();
        String nombreJugadorEliminar = myAdapter.getItem(position);
        myAdapter.remove(nombreJugadorEliminar);
        myAdapter.notifyDataSetChanged();
        jugadoresListView.setAdapter(myAdapter);


        // Instancia de helper
        this.jugadorDbHelper = new JugadorDbHelper(getContext());
        jugadorDbHelper.eliminarJugador(nombreJugadorEliminar);
        jugadorDbHelper.onCloseDb();


        //actualiza la lista de jugadores
        ConsultarJugadores();
    }


    private void ConsultarJugadores(){

        Log.d("NEWPLAYER", "Consultar todos los jugadores y agregar a listView");

        //recupera jugadores

        this.jugadorDbHelper = new JugadorDbHelper(getContext());
        List<Jugador> listaJugadoresDB = jugadorDbHelper.getJugadores();
        jugadorDbHelper.onCloseDb();

        List<String> nombres =  new ArrayList<>();

        for (Jugador miJugador: listaJugadoresDB) {
            nombres.add(miJugador.getNombreJugador());
        }

        //adapter for Spinner drop down list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, nombres);

        //agrega al listview de jugadores
        jugadoresListView.setAdapter(adapter);
    }



    //control de clicks
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agregarnombreBtn:
                AgregarJugador();
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
