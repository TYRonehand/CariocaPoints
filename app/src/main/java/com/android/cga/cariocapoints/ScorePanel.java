package com.android.cga.cariocapoints;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.cga.cariocapoints.clases.DinamicTable;
import com.android.cga.cariocapoints.helpers.PartidaDbHelper;
import com.android.cga.cariocapoints.modelos.Partida;

import java.util.ArrayList;
import java.util.List;


public class ScorePanel extends Fragment {

    private PartidaDbHelper partidaDbHelper;
    private List<Partida> listaPartidasDB;

    private Spinner partidasSpinner;
    private TableLayout tableLayout;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ScorePanel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScorePanel.
     */
    // TODO: Rename and change types and number of parameters
    public static ScorePanel newInstance(String param1, String param2) {
        ScorePanel fragment = new ScorePanel();
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
        View myView = inflater.inflate(R.layout.fragment_score_panel, container, false);

        //instanciamos la table padre
        tableLayout = (TableLayout) myView.findViewById(R.id.ScoreTable);


        //combobox con partidas
        partidasSpinner = (Spinner) myView.findViewById(R.id.partidaSpinner);

        ConsultarPartidas();

        partidasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Log.d("SCOREPANEL","Consulta Partida seleccionada");
                //limpia la tabla
                int hijosConteo = tableLayout.getChildCount();
                // Remove all rows except the first one
                if (hijosConteo > 0) {
                    tableLayout.removeViews(0, hijosConteo);
                }

                //table layout config
                TableLayout.LayoutParams tableParams =  new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,  TableLayout.LayoutParams.MATCH_PARENT);

                tableLayout.setLayoutParams(tableParams);
                tableLayout.setGravity(Gravity.FILL);


                //consulta id de la partida seleccionada
                Partida partida = listaPartidasDB.get(partidasSpinner.getSelectedItemPosition());
                //String[] aux = partidasSpinner.getItemAtPosition(partidasSpinner.getSelectedItemPosition()).toString().split(Partida.PartidaEstado.DELIMITATOR_ID_NAME);
                //partida.setIDPartida(aux[1]);

                //genera instancia la formacion de la matriz de informacion
                Log.d("SCOREPANEL","Consulta informacion de la partida: "+partida.get_ID());
                DinamicTable dinamicTable = new DinamicTable(getContext(), partida);


                //se crea el tablero
                Log.d("SCOREPANEL","Se carga tablero puntajes y se genera la vista");
                String[][] infoTablero = dinamicTable.getInformacionTabla();


                //recorremos elementos
                for(int i=0;i<dinamicTable.getSizeX();i++){

                    //crea fila view
                    TableRow tableRow = getTableRow();

                    //agrega celdas a la fila
                    for(int j=0;j<dinamicTable.getSizeY();j++){


                        //crea una etiqueta con elemento de informaicon
                        TextView textView = getTextView(Color.BLACK,Color.WHITE);
                        textView.setText( infoTablero[i][j] );
                        tableRow.addView(textView);
                    }

                    //agrega una fila a la tabla
                    tableLayout.addView(tableRow);

                }


                //se consulta resultados
                Log.d("SCOREPANEL","Se consultan puntajes totales por jugaor");
                Number[] resultTablero = dinamicTable.getTotalPuntos();
                Log.d("SCOREPANEL","Se consulta posicion de ganador");
                int posicionGanador = dinamicTable.BuscarPosicionGanador();

                //crea fila view
                TableRow tableRow = getTableRow();
                for(int k=0; k<dinamicTable.getSizeY();k++){


                    int colorTexto,colorFondo ;

                    if(k==posicionGanador) {
                        colorTexto = Color.BLACK;
                        colorFondo = Color.GREEN;
                    }else {
                        colorTexto = Color.BLACK;
                        colorFondo = Color.YELLOW;
                    }
                    TextView totalResultados = getTextView(colorTexto, colorFondo);
                    if(k<=0){//=0
                        totalResultados.setText("Resultados");
                    }else{//>0
                        totalResultados.setText(resultTablero[k]+"");
                    }
                    tableRow.addView(totalResultados);

                }
                //agrega una fila a la tabla
                tableLayout.addView(tableRow);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //limpia la tabla
                int hijosConteo = tableLayout.getChildCount();
                // Remove all rows except the first one
                if (hijosConteo > 0) {
                    tableLayout.removeViews(0, hijosConteo);
                }

                TextView textView = getTextView(Color.BLACK,Color.YELLOW);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tableLayout.addView(textView);
                tableLayout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        });


        return myView;
    }

    private TableRow getTableRow(){
        //layout params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(12,12,12,12);

        //crea fila view
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(tableRowParams);
        tableRow.setBackgroundColor(Color.BLACK);
        tableRow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return  tableRow;
    }

    private TextView getTextView(int textoColor,int fondoColor){
        //layout params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableRowParams.setMargins(12,12,12,12);

        //crea una etiqueta con elemento de informaicon
        TextView textView = new TextView(getContext());
        textView.setPadding(20,4,12,4);
        textView.setBackgroundColor(fondoColor);
        textView.setTextColor(textoColor);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textView.setTextSize(12);
        textView.setLayoutParams(tableRowParams);

        return  textView;
    }


    private void  ConsultarPartidas(){

        // Instancia de helper
        this.partidaDbHelper = new PartidaDbHelper(getContext());
        listaPartidasDB = partidaDbHelper.getPartida();
        partidaDbHelper.onCloseDb();


        List<String> nombres = new ArrayList<>();

        //Lista con rondas
        for (Partida partida: listaPartidasDB ){
            nombres.add( partida.get_ID() + Partida.PartidaEstado.DELIMITATOR_ID_NAME + partida.getFecha() );
        }

        //adapter for Spinner drop down list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, nombres);
        partidasSpinner.setAdapter(adapter);

    }


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
