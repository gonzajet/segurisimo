package l.gonza.segurisimo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import model.User;
import sql.DatabaseHelper;

import static l.gonza.segurisimo.R.id.CardViewCase;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView caseCard, perfilCard, mapCard, fileCard;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        databaseHelper = new DatabaseHelper(HomeActivity.this);
        //cards
        caseCard = findViewById(R.id.CardViewCase);
        perfilCard = findViewById(R.id.CardViewPerfil);
        mapCard = findViewById(R.id.CardViewMap);
        fileCard = findViewById(R.id.CardViewFile);

        //agrego click lister para card
        caseCard.setOnClickListener(this);
        perfilCard.setOnClickListener(this);
        mapCard.setOnClickListener(this);
        fileCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.CardViewMap:
                launchActivity(CamaraActivity.class);
                break;
            case R.id.CardViewCase:
                launchActivity(NewSinisterActivity.class);
                break;
            case R.id.CardViewPerfil:
                SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear().apply();
                launchActivity(LoginActivity.class);
                finish();
                break;
        }
    }

    private void launchActivity(Class<?> cls)
    {
        startActivity( new Intent(getApplicationContext(),cls));
    }

}

