package jordibarea.tfg.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;

import jordibarea.tfg.R;

public class GameOptionsActivity extends Activity {
    Boolean easy = true;
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPref = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        final boolean soundOn = sharedPref.getBoolean("Sound",true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_options);
        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);

        Button next = (Button) findViewById(R.id.buttonContinue);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), GameActivity.class);
                Switch switchElement = (Switch) findViewById(R.id.switchFirstTurn);
                myIntent.putExtra("firstTurn",switchElement.isChecked());
                myIntent.putExtra("easy",easy);
                startActivityForResult(myIntent, 0);
            }

        });


    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_easy:
                if (checked)
                    easy = true;
                    break;
            case R.id.radio_hard:
                if (checked)
                    easy = false;
                    break;
        }
    }
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }
}