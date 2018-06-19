package spinoffpyme.com.shipgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
//CLASE INICIAL QUE EJECUTA EL JUEGO

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        int valor=Integer.valueOf(getIntent().getExtras().getInt("Dificultad"));
        setContentView(new GameView(this,valor));
    }
}
