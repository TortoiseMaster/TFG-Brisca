package jordibarea.tfg.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import jordibarea.tfg.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPref = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        final boolean soundOn = sharedPref.getBoolean("Sound",true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);

        Button next = (Button) findViewById(R.id.buttonNewGame);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), GameOptionsActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        next = (Button) findViewById(R.id.buttonConfiguration);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), ConfigurationActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });


        next = (Button) findViewById(R.id.buttonRules);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                if (soundOn){
                    buttonSound.start();
                }
                Intent myIntent = new Intent(view.getContext(), RulesActivity.class);
                myIntent.putExtra("rules_menu",0);
                startActivityForResult(myIntent, 0);
            }

        });

    }
    protected void onDestroy() {
        //stop service and stop music
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }
}
