package fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import l.gonza.segurisimo.R;
import model.Imangen;
import model.UserSiniestro;
import sql.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetalleSiniestroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetalleSiniestroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleSiniestroFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View vista;

    private GoogleMap mMap;
    private MapView mapView;
    private Marker marker;
    private LatLng ubicacion;
    private DatabaseHelper databaseHelper;

    TextView textViewFechaYHora,textViewNombre,textViewPatente,textViewDireccion,textViewPoliza,textViewEmail,textViewTelefono;
    ImageView imageViewAccidente;

    private OnFragmentInteractionListener mListener;

    public DetalleSiniestroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleSiniestroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleSiniestroFragment newInstance(String param1, String param2) {
        DetalleSiniestroFragment fragment = new DetalleSiniestroFragment();
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
         vista = inflater.inflate(R.layout.fragment_detalle_siniestro, container, false);

        Bundle objetoSiniestro = getArguments();

        UserSiniestro siniestro = null;

        textViewFechaYHora = vista.findViewById(R.id.textViewFechaYHora);
        textViewNombre = vista.findViewById(R.id.textViewNombre);
        textViewPatente = vista.findViewById(R.id.textViewPatente);
        textViewDireccion = vista.findViewById(R.id.textViewDireccion);
        textViewPoliza = vista.findViewById(R.id.textViewPoliza);
        textViewEmail = vista.findViewById(R.id.textViewEmail);
        textViewTelefono = vista.findViewById(R.id.textViewTelefono);
        imageViewAccidente = vista.findViewById(R.id.imageViewAccidente);

        databaseHelper = new DatabaseHelper(getContext());

        if(objetoSiniestro != null){
            siniestro = (UserSiniestro) objetoSiniestro.getSerializable("objeto");
//            String fechaHora = siniestro.getFecha()+" "+siniestro.getHora();
            String fechaHora = "";
            textViewFechaYHora.setText(fechaHora);
            textViewNombre.setText(getString(R.string.nombre)+": "+siniestro.getName());
            textViewPatente.setText(getString(R.string.patente)+": "+siniestro.getPatente());
            textViewDireccion.setText(getString(R.string.direccion)+": "+siniestro.getDireccion());
            textViewPoliza.setText(getString(R.string.poliza)+": "+siniestro.getPoliza());
            textViewEmail.setText(getString(R.string.email)+": "+siniestro.getEmail());
            textViewTelefono.setText(getString(R.string.telefono)+": "+siniestro.getTelefono());
            ubicacion = new LatLng(siniestro.getLat(),siniestro.getLon());

            String path = databaseHelper.getAllImagen(siniestro.getId()).get(0).getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            imageViewAccidente.setImageBitmap(bitmap);

        }

        return vista;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);
        if(mapView !=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,18));
        marker = mMap.addMarker(new MarkerOptions().position(ubicacion).draggable(true).title("Accidente").snippet("En este lugar ocurrio un siniestro"));
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
}
