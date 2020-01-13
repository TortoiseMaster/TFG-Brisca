package jordibarea.tfg.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jordibarea.tfg.R;

public class WinActivity  extends Activity {
    /** Called when the activity is first created. */
    Integer points;
    MediaPlayer winnerSound;

    public void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPref = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        final boolean soundOn = sharedPref.getBoolean("Sound",true);

        final MediaPlayer buttonSound = MediaPlayer.create(this, R.raw.button_sound);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_screen);
        points = getIntent().getIntExtra("points", 0);

        Button next = (Button) findViewById(R.id.buttonWinNewGame);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), GameOptionsActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        next = (Button) findViewById(R.id.buttonWinBackToMain);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        TextView text = (TextView) findViewById(R.id.textWinner);
        if (points > 60) {
            text.setText(getString(R.string.win_message));
            text.setContentDescription(getString(R.string.win_message));
            winnerSound = MediaPlayer.create(this, R.raw.victory);
        }
        else if (points < 60) {
            text.setText(getString(R.string.lose_message));
            text.setContentDescription(getString(R.string.lose_message));
            winnerSound = MediaPlayer.create(this, R.raw.match_defeat);
        }
        else {
            text.setText(getString(R.string.draw_message));
            text.setContentDescription(getString(R.string.draw_message));
        }
        if (soundOn && points != 60){
            winnerSound.start();
        }
        text = (TextView) findViewById(R.id.textPointsWinner);
        text.setText(getString(R.string.your_points) + points +"\n"+ getString(R.string.rival_points)  +(120 - points));
        text.setContentDescription(getString(R.string.your_points) + points + getString(R.string.rival_points) + (120 - points));

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
