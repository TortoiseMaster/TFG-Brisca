package jordibarea.tfg.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jordibarea.tfg.MusicPlayer;
import jordibarea.tfg.R;

public class WinActivity  extends Activity {
    /** Called when the activity is first created. */
    Integer points;
    MediaPlayer winnerSound;

    public void onCreate(Bundle savedInstanceState) {

        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_screen);
        points = getIntent().getIntExtra("points", 0);

        Button next = (Button) findViewById(R.id.buttonWinNewGame);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buttonSound.start();
                Intent myIntent = new Intent(view.getContext(), GameOptionsActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        next = (Button) findViewById(R.id.buttonWinBackToMain);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buttonSound.start();
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        TextView text = (TextView) findViewById(R.id.textWinner);
        if (points > 60) {
            text.setText("HAS GANADO!");
            text.setContentDescription("Has ganado!");
            winnerSound = MediaPlayer.create(this, R.raw.victory);
        }
        else if (points < 60) {
            text.setText("HAS PERDIDO!");
            text.setContentDescription("Has perdido.");
            winnerSound = MediaPlayer.create(this, R.raw.match_defeat);
        }
        else {
            text.setText("EMPATE!");
            text.setContentDescription("Empate.");
        }
        winnerSound.start();
        text = (TextView) findViewById(R.id.textPointsWinner);
        text.setText("Tus puntos: " + points +"\nPuntos rival: "+(120 - points));
        text.setContentDescription("Tus puntos: " + points +" Puntos rival: "+(120 - points));

    }
    protected void onDestroy() {
        //stop service and stop music
        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
        super.onDestroy();
    }
}
