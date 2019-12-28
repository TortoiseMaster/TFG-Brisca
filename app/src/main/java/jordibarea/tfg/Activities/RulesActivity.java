package jordibarea.tfg.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import jordibarea.tfg.MusicPlayer;
import jordibarea.tfg.R;

public class RulesActivity extends Activity {

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);

        TextView title = (TextView) findViewById(R.id.textRulesSubTitle);
        TextView content = (TextView) findViewById(R.id.textRulesContent);

        Button next = (Button) findViewById(R.id.rulesMain);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextView title = (TextView) findViewById(R.id.textRulesSubTitle);
                TextView content = (TextView) findViewById(R.id.textRulesContent);
                title.setText("General");
                content.setText("- Juego de cartas de dos jugadores\n- Cartas de la baraja española \nOrden cartas: \n · 1 \n · 3 \n · Resto de cartas en orden descendiente \n- Objetivo: ganar mas puntos que el rival");
            }
        });

        next = (Button) findViewById(R.id.rulesPlayability);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextView title = (TextView) findViewById(R.id.textRulesSubTitle);
                TextView content = (TextView) findViewById(R.id.textRulesContent);
                title.setText("Juego");
                content.setText("- Cada jugador tiene 3 cartas en la mano\n- En cada mano cada jugador usa una y roba otra hasta agotar las cartas  \n- La brisca es la carta que indica el palo que domina en la partida\n- Puedes cambiar la brisca si tienes el 7 del mismo palo");
            }
        });
        next = (Button) findViewById(R.id.rulesHand);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextView title = (TextView) findViewById(R.id.textRulesSubTitle);
                TextView content = (TextView) findViewById(R.id.textRulesContent);
                title.setText("Manos");
                content.setText("- El ganador de la mano anterior tira la primera carta\nQuien gana? \n · Gana la carta mas alta del palo de la brisca  \n · Si no hay, gana la carta mas alta del palo de la primera carta tirada \n- El jugador suma los puntos de las cartas ganadas");
            }
        });
        next = (Button) findViewById(R.id.rulesPoints);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TextView title = (TextView) findViewById(R.id.textRulesSubTitle);
                TextView content = (TextView) findViewById(R.id.textRulesContent);
                title.setText("Puntos");
                content.setText("Valor de las cartas:\n - 1: 11 puntos\n - 3: 10 puntos\n - 12: 4 puntos\n - 11: 3 puntos\n - 10: 2 puntos\n - Otras: 0 puntos");
            }
        });
    }
    protected void onDestroy() {
        //stop service and stop music
        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
        super.onDestroy();
    }
}