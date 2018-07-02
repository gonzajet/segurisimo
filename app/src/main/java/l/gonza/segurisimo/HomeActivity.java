package l.gonza.segurisimo;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import model.User;
import sql.DatabaseHelper;

import static l.gonza.segurisimo.R.id.CardViewCase;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView caseCard, perfilCard, mapCard, fileCard;
    private DatabaseHelper databaseHelper;
    NotificationManager nmgr;
    final int NOTIFICATION_ID = 0;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseHelper = new DatabaseHelper(HomeActivity.this);


        //cards

        caseCard = findViewById(R.id.CardViewCase);
        mapCard = findViewById(R.id.CardViewMap);
        fileCard = findViewById(R.id.CardViewStart);

        //agrego click lister para card
        caseCard.setOnClickListener(this);
        mapCard.setOnClickListener(this);
        fileCard.setOnClickListener(this);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sesion:
                SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear().apply();
                launchActivity(LoginActivity.class);
                finish();
                return true;
            case R.id.menu_settings:
                Log.i("ActionBar", "Settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.CardViewMap:
                launchActivity(ListaSiniestroActivity.class);
                break;
            case R.id.CardViewCase:
                launchActivity(NewSinisterActivity.class);
                break;
            case R.id.CardViewStart:
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(HomeActivity.this)
                                .setSmallIcon(android.R.drawable.stat_sys_warning)
                                .setLargeIcon((((BitmapDrawable)getResources()
                                        .getDrawable(R.drawable.splash1)).getBitmap()))
                                .setContentTitle("Pedido de grua en camino")
                                .setContentText("Se ha procesado el pedido")
                                .setContentInfo("4")
                                .setTicker("Alertaaaa");
                Intent notIntent =
                        new Intent(HomeActivity.this, HomeActivity.class);
                PendingIntent contIntent =
                        PendingIntent.getActivity(HomeActivity.this,0, notIntent, 0);

                mBuilder.setContentIntent(contIntent);


                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

                break;


        }
    }

    private void launchActivity(Class<?> cls)
    {
        startActivity( new Intent(getApplicationContext(),cls));
    }

}

