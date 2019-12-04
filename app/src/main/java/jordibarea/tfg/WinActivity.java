package jordibarea.tfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class WinActivity  extends Activity {
    /** Called when the activity is first created. */
    Integer points;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_screen);
        points = getIntent().getIntExtra("points", 0);

        Button next = (Button) findViewById(R.id.buttonWinNewGame);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent myIntent = new Intent(view.getContext(), GameOptionsActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        next = (Button) findViewById(R.id.buttonWinBackToMain);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        TextView text = (TextView) findViewById(R.id.textWinner);
        if (points > 60) {
            text.setText("HAS GANADO!");
            text.setContentDescription("Has ganado!");
        }
        else if (points < 60) {
            text.setText("HAS PERDIDO!");
            text.setContentDescription("Has perdido.");
        }
        else {
            text.setText("EMPATE!");
            text.setContentDescription("Empate.");
        }
        text = (TextView) findViewById(R.id.textPointsWinner);
        text.setText("Tus puntos: " + points +"\nPuntos rival: "+(120 - points));
        text.setContentDescription("Tus puntos: " + points +"\nPuntos rival: "+(120 - points));

    }
}
