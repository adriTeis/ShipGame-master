package spinoffpyme.com.shipgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    Button btnJugar;
    int dificultad=0;
    EditText Usuario;
    //En esta variable cojo la instancia de la base de datos para poder comunicarme con ella
    FirebaseDatabase VaribleDataBase;
    //En esta variable puedo escribir en el Json
    DatabaseReference reference;
    TextView texto;
    String TextoBD="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Usuario=(EditText) findViewById(R.id.TextoUsuario);

        btnJugar = (Button)findViewById(R.id.btnJugar);

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(dificultad==0||Usuario.getText().toString().isEmpty()){

                    Toast.makeText(MainActivity.this,"Selecciona una dificultad y introduce Usuario",Toast.LENGTH_LONG).show();

                }else {

                    Intent juego = new Intent(MainActivity.this, GameActivity.class);
                    juego.putExtra("Dificultad",dificultad);
                    startActivityForResult(juego,dificultad);
                    GameView.Usuario(Usuario.getText().toString());

                }
            }
        });

        texto = (TextView) findViewById(R.id.Resultado);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this,"Juega otra vez",Toast.LENGTH_SHORT).show();
    }




    public void BotonFacil(View view) {
        dificultad=1;


    }

    public void BotonDificil(View view) {

       dificultad=3;


    }

    public void BotonMedio(View view) {

       dificultad=2;


    }

    public void BotonVerUsuarios(View view) {

        VaribleDataBase = FirebaseDatabase.getInstance();
        reference=VaribleDataBase.getReference("Usuarios/Records");
        ValueEventListener valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot valor;
                Iterator<DataSnapshot> valores = dataSnapshot.getChildren().iterator();
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    TextoBD=TextoBD+valores.next().getValue().toString()+"\n";
                }
                texto.setText(TextoBD);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
}
