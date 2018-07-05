package l.gonza.segurisimo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import adaptador.AdaptadorSiniestros;
import fragments.DetalleSiniestroFragment;
import fragments.ListaSiniestrosFragment;
import interfaces.IComunicaFragments;
import model.UserSiniestro;

public class ListaSiniestroActivity extends AppCompatActivity implements ListaSiniestrosFragment.OnFragmentInteractionListener,DetalleSiniestroFragment.OnFragmentInteractionListener, IComunicaFragments {

    ListaSiniestrosFragment listaFragment;
    DetalleSiniestroFragment detalleFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card_view);

       implementarListaFragment();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void enviarPersonaje(UserSiniestro siniestro) {
        detalleFragment = new DetalleSiniestroFragment();
        Bundle bundleEnvio = new Bundle();
        bundleEnvio.putSerializable("objeto",siniestro);
        detalleFragment.setArguments(bundleEnvio);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment,detalleFragment).addToBackStack(null).commit();
    }

    @Override
    public void volverLista() {
        implementarListaFragment();
    }

    private void implementarListaFragment(){
        listaFragment = new ListaSiniestrosFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment,listaFragment).commit();
    }
}
