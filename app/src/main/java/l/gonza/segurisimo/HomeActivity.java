package l.gonza.segurisimo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import static l.gonza.segurisimo.R.id.CardViewCase;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView caseCard, perfilCard, mapCard, fileCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

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
                launchActivity(MapsActivity.class);
                break;
            case R.id.CardViewCase:
                launchActivity(NewSinisterActivity.class);
                break;
        }
    }

    private void launchActivity(Class<?> cls)
    {
        startActivity( new Intent(getApplicationContext(),cls));
    }

}

