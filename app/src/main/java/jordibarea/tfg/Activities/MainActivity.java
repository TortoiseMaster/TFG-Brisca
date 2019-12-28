package jordibarea.tfg.Activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import jordibarea.tfg.MusicPlayer;
import jordibarea.tfg.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        final MediaPlayer buttonSound = MediaPlayer.create(this,R.raw.button_sound);

        startService(new Intent(MainActivity.this, MusicPlayer.class));

        Button next = (Button) findViewById(R.id.buttonNewGame);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buttonSound.start();
                Intent myIntent = new Intent(view.getContext(), GameOptionsActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

//        next = (Button) findViewById(R.id.buttonContinueGame);
//        next.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                buttonSound.start();
//                Intent myIntent = new Intent(view.getContext(), GameActivity.class);
//                startActivityForResult(myIntent, 0);
//            }
//
//        });

        next = (Button) findViewById(R.id.buttonConfiguration);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buttonSound.start();
                Intent myIntent = new Intent(view.getContext(), ConfigurationActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });


        next = (Button) findViewById(R.id.buttonRules);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buttonSound.start();
                Intent myIntent = new Intent(view.getContext(), RulesActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

    }
    protected void onDestroy() {
        //stop service and stop music
        stopService(new Intent(MainActivity.this, MusicPlayer.class));
        super.onDestroy();
    }
}
