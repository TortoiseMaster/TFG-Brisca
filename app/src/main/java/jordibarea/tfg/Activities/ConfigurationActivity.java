package jordibarea.tfg.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import jordibarea.tfg.R;

public class ConfigurationActivity extends Activity {

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_menu);

        Switch switchElement = (Switch) findViewById(R.id.switchSound);
        if (sharedPref.getBoolean("Sound",true) == true){
            switchElement.setChecked(true);
        }
        else {
            switchElement.setChecked(false);
        }
        switchElement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("Sound", true);
                    editor.commit();
                }
                else{
                    SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("Sound", false);
                    editor.commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }
}