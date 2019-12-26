package jordibarea.tfg.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import jordibarea.tfg.MusicPlayer;
import jordibarea.tfg.R;

public class ConfigurationActivity extends Activity {

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_menu);

//        Button next = (Button) findViewById(R.id.buttonContinue);
//
//        next.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Switch switchElement = (Switch) findViewById(R.id.switchChangeBrisca);
//                Intent myIntent = new Intent(view.getContext(), GameActivity.class);
//                myIntent.putExtra("changeBrisca",switchElement.isChecked());
//                startActivityForResult(myIntent, 0);
//            }
//
//        });
//        Switch switchElement = (Switch) findViewById(R.id.switchChangeBrisca);

    }
    protected void onDestroy() {
        //stop service and stop music
        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
        super.onDestroy();
    }
}