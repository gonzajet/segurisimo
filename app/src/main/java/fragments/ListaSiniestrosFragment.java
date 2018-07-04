package fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import adaptador.AdaptadorSiniestros;
import interfaces.IComunicaFragments;
import l.gonza.segurisimo.R;
import model.UserSiniestro;
import sql.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaSiniestrosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaSiniestrosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaSiniestrosFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<UserSiniestro> listaUsuariosSiniestro;
    RecyclerView recyclerUsuariosSiniestro;
    TextInputEditText textInputEditTextFiltro,textInputEditTextFiltro2;

    Activity activity;
    IComunicaFragments interfaceComunicaFragment;
    AppCompatButton appCompatButtonBusqueda;

    private DatabaseHelper databaseHelper;


    public ListaSiniestrosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaSiniestrosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaSiniestrosFragment newInstance(String param1, String param2) {
        ListaSiniestrosFragment fragment = new ListaSiniestrosFragment();
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
        View vista =inflater.inflate(R.layout.fragment_lista_siniestros, container, false);

        appCompatButtonBusqueda = vista.findViewById(R.id.appCompatButtonBusqueda);
        textInputEditTextFiltro = vista.findViewById(R.id.textInputEditTextFiltro);
        textInputEditTextFiltro2 = vista.findViewById(R.id.textInputEditTextFiltro2);
        appCompatButtonBusqueda.setOnClickListener(this);
        databaseHelper = new DatabaseHelper(getContext());


        listaUsuariosSiniestro = new ArrayList<>();
        recyclerUsuariosSiniestro = vista.findViewById(R.id.recyclerId);
        recyclerUsuariosSiniestro.setLayoutManager(new LinearLayoutManager(getContext()));
        llenarLista();
        crearListaView();
        return vista;
    }

    private void llenarLista() {
        listaUsuariosSiniestro = databaseHelper.getAllSiniestros();
    }

    private void buscar() {
        listaUsuariosSiniestro = databaseHelper.getAllSiniestros(textInputEditTextFiltro.getText().toString().trim(),textInputEditTextFiltro2.getText().toString().trim());
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

        if(context instanceof  Activity){
            this.activity =(Activity) context;
            interfaceComunicaFragment = (IComunicaFragments) this.activity;
        }

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.appCompatButtonBusqueda:
                buscar();
                crearListaView();
                break;

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void crearListaView(){
        AdaptadorSiniestros adapter = new AdaptadorSiniestros(listaUsuariosSiniestro);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Toast.makeText(getContext(),"Seleccion: "+listaUsuariosSiniestro.get(recyclerUsuariosSiniestro.getChildAdapterPosition(v)).getName(),Toast.LENGTH_LONG ).show();

                interfaceComunicaFragment.enviarPersonaje(listaUsuariosSiniestro.get(recyclerUsuariosSiniestro.getChildAdapterPosition(v)));
            }
        });
        recyclerUsuariosSiniestro.setAdapter(adapter);

    }
}
