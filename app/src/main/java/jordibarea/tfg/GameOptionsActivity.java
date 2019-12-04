package jordibarea.tfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class GameOptionsActivity extends Activity {

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_options);

        Button next = (Button) findViewById(R.id.buttonContinue);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent myIntent = new Intent(view.getContext(), GameActivity.class);
                // switchElement = (Switch) findViewById(R.id.switchChangeBrisca);
                // myIntent.putExtra("changeBrisca",switchElement.isChecked());
                Switch switchElement = (Switch) findViewById(R.id.switchFirstTurn);
                myIntent.putExtra("firstTurn",switchElement.isChecked());
                startActivityForResult(myIntent, 0);
            }

        });


    }
}