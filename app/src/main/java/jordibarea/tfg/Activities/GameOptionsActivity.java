package jordibarea.tfg.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import jordibarea.tfg.MusicPlayer;
import jordibarea.tfg.R;

public class GameOptionsActivity extends Activity {

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_options);
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);

        Button next = (Button) findViewById(R.id.buttonContinue);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buttonSound.start();
                Intent myIntent = new Intent(view.getContext(), GameActivity.class);
                Switch switchElement = (Switch) findViewById(R.id.switchFirstTurn);
                myIntent.putExtra("firstTurn",switchElement.isChecked());
                startActivityForResult(myIntent, 0);
            }

        });


    }
    protected void onDestroy() {
        //stop service and stop music
        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
        super.onDestroy();
    }
}