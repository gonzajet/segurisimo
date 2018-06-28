package l.gonza.segurisimo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import model.User;
import sql.DatabaseHelper;

public class NewSinisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText textInputEditTextPatente,textInputEditTextPoliza,textInputEditTextNombre,textInputEditTextDireccion,textInputEditTextSeguro,textInputEditTextTelefono;
    private AppCompatButton appCompatButtonBusqueda,appCompatButtonSiguiente;
    private DatabaseHelper databaseHelper;
    private Boolean isOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sinister);

        initViews();
        initListeners();
        initObjects();
        initFunctions();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        textInputEditTextPatente = findViewById(R.id.textInputEditTextPatente);
        textInputEditTextPoliza = findViewById(R.id.textInputEditTextPoliza);
        textInputEditTextNombre = findViewById(R.id.textInputEditTextNombre);
        textInputEditTextDireccion = findViewById(R.id.textInputEditTextDireccion);
        textInputEditTextSeguro = findViewById(R.id.textInputEditTextSeguro);
        textInputEditTextTelefono = findViewById(R.id.textInputEditTextTelefono);

        appCompatButtonBusqueda = findViewById(R.id.appCompatButtonBusqueda);
        appCompatButtonSiguiente = findViewById(R.id.appCompatButtonSiguiente);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonBusqueda.setOnClickListener(this);
        appCompatButtonSiguiente.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonBusqueda:
               User user = getUser();
               isOk = true;
               if(user.getId() == 0){
                   setEnableTextInput(true);
                   setTextInputClear();
                   createUserSiniester();
               }else{
                   setTextInputUser(user);
               }
                break;
            case R.id.appCompatButtonSiguiente:
                if(isOk){
                    startActivity( new Intent(getApplicationContext(),MapsActivity.class));
                }

    }
    }

    private void createUserSiniester() {
        User user = new User();
        user.setName(textInputEditTextNombre.getText().toString().trim());
        user.setPoliza(textInputEditTextPoliza.getText().toString().trim());
        user.setSeguro(textInputEditTextSeguro.getText().toString().trim());
        user.setPatente(textInputEditTextPatente.getText().toString().trim());
        user.setDireccion(textInputEditTextDireccion.getText().toString().trim());
        user.setTelefono(textInputEditTextTelefono.getText().toString().trim());

        databaseHelper.addUserForSiniestro(user);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(this);
    }

    private void initFunctions(){
        setEnableTextInput(false);
        isOk = false;
    }

    private User getUser(){
//    private User getUser(String usuario,String poliza){
       return databaseHelper.getUserForPatenteYPoliza(textInputEditTextPatente.getText().toString().trim(),textInputEditTextPoliza.getText().toString().trim());
//       return databaseHelper.getUserForPatenteYPoliza(textInputEditTextPatente.getText().toString().trim(),textInputEditTextPoliza.getText().toString().trim());
    }

    private void setEnableTextInput(boolean isEnable){
        textInputEditTextNombre.setEnabled(isEnable);
        textInputEditTextDireccion.setEnabled(isEnable);
        textInputEditTextSeguro.setEnabled(isEnable);
        textInputEditTextTelefono.setEnabled(isEnable);
    }

    private void setTextInputUser(User user){
        textInputEditTextNombre.setText(user.getName());
        textInputEditTextDireccion.setText(user.getDireccion());
        textInputEditTextSeguro.setText(user.getSeguro());
        textInputEditTextTelefono.setText(user.getTelefono());
    }

    private void setTextInputClear(){
        textInputEditTextNombre.setText("");
        textInputEditTextDireccion.setText("");
        textInputEditTextSeguro.setText("");
        textInputEditTextTelefono.setText("");
    }
}
