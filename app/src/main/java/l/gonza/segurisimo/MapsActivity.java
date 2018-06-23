package l.gonza.segurisimo;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener, View.OnClickListener {

    private GoogleMap mMap;
    private Marker markerPosition;
    private FusedLocationProviderClient mFusedLocationClient;

    public LatLng getLatLngPosition() {
        return LatLngPosition;
    }

    public void setLatLngPosition(LatLng latLngPosition) {
        LatLngPosition = latLngPosition;
    }

    private LatLng LatLngPosition;

    public Boolean getSeguiente() {
        return seguiente;
    }

    public void setSeguiente(Boolean seguiente) {
        this.seguiente = seguiente;
    }

    private Boolean seguiente;

    private final int MY_PERMISSIONS = 100;
    private Button buttonPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buttonPosition = findViewById(R.id.buttonPosition);
        Button buttonSiguiente = findViewById(R.id.buttonSiguiente);

        buttonPosition.setOnClickListener(this);
        buttonSiguiente.setOnClickListener(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("AR")
                .build();


        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Snackbar.make(getWindow().getDecorView().getRootView(), "Place: " + place.getLatLng().toString(), Snackbar.LENGTH_LONG).show();
                createOrSetMarket(place.getLatLng());

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Snackbar.make(getWindow().getDecorView().getRootView(), "An error occurred: " + status, Snackbar.LENGTH_LONG).show();
            }
        });
        setSeguiente(false);

        if(mayRequestStoragePermission())
            buttonPosition.setEnabled(true);
        else
            buttonPosition.setEnabled(false);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        mMap.setMyLocationEnabled(true);

        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnMapClickListener(this);
        getPosition();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.equals(markerPosition)){
            LatLng latLng = new LatLng(marker.getPosition().latitude,marker.getPosition().latitude);
            setLatLngPosition(latLng);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        createOrSetMarket(latLng);
        setLatLngPosition(latLng);
    }

    public void createOrSetMarket(LatLng latLng){
        if(markerPosition == null){
            markerPosition = mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title("Ya tu sabes").snippet("la que ta lo conoces"));
            setSeguiente(true);
        }else{
            markerPosition.setPosition(latLng);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonPosition:
                getPosition();
                if(getLatLngPosition() != null){
                    createOrSetMarket(getLatLngPosition());

                }else{
                    Snackbar.make(getWindow().getDecorView().getRootView(),"nop", Snackbar.LENGTH_LONG).show();
                }
//                LatLng test = getLatLngPosition();

                break;
            case R.id.buttonSiguiente:
                if(getSeguiente()){
                    Snackbar.make(getWindow().getDecorView().getRootView(),"siguiente "+ getLatLngPosition().toString(), Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(getWindow().getDecorView().getRootView(),"falta localizacion", Snackbar.LENGTH_LONG).show();
                }
        }
    }

    public void getPosition() {

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
//                            Snackbar.make(getWindow().getDecorView().getRootView(), "Place: " + location.getLatitude() + "long" + location.getLongitude(), Snackbar.LENGTH_LONG).show();
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

//                            Snackbar.make(getWindow().getDecorView().getRootView(), latLng.toString(), Snackbar.LENGTH_LONG).show();
                            setLatLngPosition(latLng);

                        } else {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Nada de nada", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) || (shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION))){
            Snackbar.make(getWindow().getDecorView().getRootView(), "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, MY_PERMISSIONS);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MapsActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                buttonPosition.setEnabled(true);
                getPosition();
            }
        }else{
            Toast.makeText(MapsActivity.this, "Permisos denegados", Toast.LENGTH_SHORT).show();
        }
    }

}

