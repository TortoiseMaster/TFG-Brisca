package jordibarea.tfg.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import jordibarea.tfg.R;

public class RulesActivity extends Activity {
    int rules_menu;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        rules_menu = getIntent().getIntExtra("rules_menu",0);

        TextView title = (TextView) findViewById(R.id.textRulesSubTitle);
        TextView content = (TextView) findViewById(R.id.textRulesContent);

        if (rules_menu == 0) {
            title.setText("General");
            content.setText("- Juego de cartas de dos jugadores\n- Cartas de la baraja española \nOrden cartas: \n · 1 \n · 3 \n · Resto de cartas en orden descendiente (12, 11, 10...) \n- Objetivo: ganar mas puntos que el rival");
        }
        else if (rules_menu == 1){
            title.setText("Juego");
            content.setText("- Cada jugador tiene 3 cartas en la mano\n- En cada mano cada jugador usa una y roba otra hasta agotar las cartas  \n- La brisca es la carta que indica el palo que domina en la partida\n- Puedes cambiar la brisca si tienes el 7 del mismo palo");
        }
        else if (rules_menu == 2){
            title.setText("Manos");
            content.setText("- El ganador de la mano anterior tira la primera carta\nQuien gana? \n · Gana la carta mas alta del palo de la brisca  \n · Si no hay, gana la carta mas alta del palo de la primera carta tirada \n- El jugador suma los puntos de las cartas ganadas");
        }
        else {
            title.setText("Puntos");
            content.setText("Valor de las cartas:\n - 1 = 11 puntos\n - 3 = 10 puntos\n - 12 = 4 puntos\n - 11 = 3 puntos\n - 10 = 2 puntos\n - Otras = 0 puntos");
        }
        SharedPreferences sharedPref = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        final boolean soundOn = sharedPref.getBoolean("Sound",true);

        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);

        Button next = (Button) findViewById(R.id.rulesMain);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), RulesActivity.class);
                myIntent.putExtra("rules_menu",0);
                startActivityForResult(myIntent, 0);
            }
        });

        next = (Button) findViewById(R.id.rulesPlayability);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), RulesActivity.class);
                myIntent.putExtra("rules_menu",1);
                startActivityForResult(myIntent, 0);
            }
        });
        next = (Button) findViewById(R.id.rulesHand);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), RulesActivity.class);
                myIntent.putExtra("rules_menu",2);
                startActivityForResult(myIntent, 0);
            }
        });
        next = (Button) findViewById(R.id.rulesPoints);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), RulesActivity.class);
                myIntent.putExtra("rules_menu", 3);
                startActivityForResult(myIntent, 0);
            }
        });
    }
//    protected void onDestroy() {
//        //stop service and stop music
//        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
//        super.onDestroy();
//    }
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }
}
