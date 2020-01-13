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
            title.setText(getString(R.string.general));
            content.setText(getString(R.string.content_general));
        }
        else if (rules_menu == 1){
            title.setText(getString(R.string.rules_play));
            content.setText(getString(R.string.content_play));
        }
        else if (rules_menu == 2){
            title.setText(getString(R.string.rules_hands));
            content.setText(getString(R.string.content_hands));
        }
        else {
            title.setText(getString(R.string.rules_points));
            content.setText(getString(R.string.content_points));
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
