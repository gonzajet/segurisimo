package l.gonza.segurisimo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends  AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                Intent intent = new Intent(Splash.this, LoginActivity.class);
                if( !(preferences.getString("user_id","").equals(""))) {
                  intent = new Intent(Splash.this, HomeActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);



    }
}

