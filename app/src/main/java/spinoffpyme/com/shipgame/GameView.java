package spinoffpyme.com.shipgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by tomas on 19/01/2018.
 */

//El gameview es la clase pincipal que llama a todas, cada vez que gameloop la lanza todo el rato con el hilo
//llama todas las demas que tienen que pintar algo dentro de ella,

public class GameView extends SurfaceView {

    //En esta variable cojo la instancia de la base de datos para poder comunicarme con ella
    FirebaseDatabase VaribleDataBase;
    //En esta variable puedo escribir en el Json
    DatabaseReference reference;
    private GameLoopThread gameLoop;
    private HiloCreadorPiedras hiloCreadorPiedras;
    //Creo un ArrayList para meter las piedras que creamos y desde el hilo ir añadiendolas,
    //Cada vez que se crea una piedra vamos recorriendo el array list y los objetos que tengan
    //la altura de la Y mas grande que la pantalla quiere decir que ya se creo y lo elimino
    public List<BallsDown> listaBolas = new ArrayList<BallsDown>();
    private SurfaceHolder holder;
    private Ship ship;
    private BallsDown ballDown;
    private GameView gameView;
    private boolean pulsado = true;
    private int PosActualNabex;
    private int PosActualNabey;
    int dificultad;
    static int vidas = 3;
    int RestarVida = 1;
    boolean PrimerChoque=false;
    int posicion;
    int metros;
    static String User;
    Boolean Enviado=false;
    Boolean pintado=false;



    public GameView(Context context, int dificultad) {
        super(context);
        this.dificultad = dificultad;
        holder = getHolder();
        gameLoop = new GameLoopThread(this);
        hiloCreadorPiedras = new HiloCreadorPiedras(this, dificultad);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {


                ship = createShip();
                gameLoop.setRunning(true);
                gameLoop.start();

                //Ahora lanzo el hilo que crea las piedras
                Log.i("Dificultdad", "dificultads");
                hiloCreadorPiedras.setRunning(true);

                hiloCreadorPiedras.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoop.setRunning(false);
                while (retry) {
                    try {
                        gameLoop.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    public void obtenerdificultad(int dificul) {
        this.dificultad = dificul;
    }

    public Ship createShip() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
        ship = new Ship(this, bmp);
        return ship;
    }

    public void createBalls() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.rocas);
        ballDown = new BallsDown(this, bmp);

        listaBolas.add(ballDown);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawColor(Color.BLACK);
        Bitmap left = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        Bitmap right = BitmapFactory.decodeResource(getResources(), R.drawable.right);
        Bitmap Corazon1 = BitmapFactory.decodeResource(getResources(),R.drawable.corazon);
        Bitmap Corazon2 = BitmapFactory.decodeResource(getResources(),R.drawable.corazon);
        Bitmap Corazon3 = BitmapFactory.decodeResource(getResources(),R.drawable.corazon);

        //Le digo que se pinte en la posicion 0 y en la altura partido 2
        canvas.drawBitmap(left, 0, getHeight() / 2, null);
        //le digo que se pinte en la posicion maxima del ancho menos el ancho del dibujo
        //de alto en la altura partido 2
        canvas.drawBitmap(right, getWidth() - right.getWidth(), getHeight() / 2, null);
        ship.onDraw(canvas);
        //AQUI CREO LAS VIDAS EN LA PANTALLA, DEPENDIENDO EL NUNMERO DE VIDAS QUE ME QUEDEN
        if(vidas==3) {
            canvas.drawBitmap(Corazon1, 80, 20, null);
            canvas.drawBitmap(Corazon2, 160, 20, null);
            canvas.drawBitmap(Corazon3, 240, 20, null);
        }else if(vidas==2){
            canvas.drawBitmap(Corazon1, 80, 20, null);
            canvas.drawBitmap(Corazon2, 160, 20, null);
        }else if(vidas== 1){
            canvas.drawBitmap(Corazon1, 80, 20, null);
        }


    }

    //ESTE METODO MANDA EL RECORD DE MI PUNTUACION SL MORIR ADEMAS DE IR CREANDO BOLAS
    public void CreacionDeBolas(final Canvas canvas) throws InterruptedException {

        Paint pincel1 = new Paint();
        pincel1.setARGB(255, 255, 0, 0);
        pincel1.setTextSize(100);
        pincel1.setTypeface(Typeface.SERIF);
        canvas.drawText(String.valueOf(metros), getWidth()-pincel1.getTextSize()-200,80, pincel1);
        metros=metros+1;

        if(vidas==0) {

                VaribleDataBase = FirebaseDatabase.getInstance();
                reference = VaribleDataBase.getReference("Usuarios/Records");
                reference.push().setValue(User+": "+metros+" Metros");
               // reference.child("Usuario").setValue(User+": "+metros+" Metros");
                Enviado = true;

                sleep(2500);
                System.exit(0);
        }



        metros=metros+1;


        for (int i = 0; i <= listaBolas.size() - 1; i++) {

            listaBolas.get(i).onDraw(canvas);

        }


    }


    //EN ESTE METODO CALCULO EN QUE PARTE DE LA PANTALLA SE TOCO PARA MOVER LA NABE
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int Accion = MotionEventCompat.getActionMasked(event);
        //compruebo si la pulsacion fue un toque o movido

        switch (Accion) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_POINTER_DOWN:
                float x = event.getX();
                float y = event.getY();

                if (x > 0 && x < 170 && y < (getHeight() / 2 + 170) && y > getHeight() / 2) {
                    ship.moveRight(33);


                } else if (x > (getWidth() - 170) && x < getWidth() && y < (getHeight() / 2 + 170) && y > getHeight() / 2) {
                    ship.movLeft(33);

                }
                break;


            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                ship.movLeft(0);
                ship.moveRight(0);

                break;

        }

        return true;
    }

//EN ESTE METODO CADA VEZ QUE UNA POLOTA TOCA MI NABE PIERDO UNA VIDA, Y SI LA PELOTA SE BORRA DE LA PANTALLA
    //SE ELIMINA
    public void ComprobarPosicionShip(){
int bolaGolpeada = 0;

        for (int i = 0; i < listaBolas.size(); i++) {

            if (listaBolas.get(i).getX() <= ship.PosicionActualX() + ship.anchoNave()-1 && listaBolas.get(i).getX() >= ship.PosicionActualX()+1 ||
                    listaBolas.get(i).getX() + listaBolas.get(i).anchoBola() <= ship.PosicionActualX() + ship.anchoNave()-1 && listaBolas.get(i).getX() + listaBolas.get(i).anchoBola() >= ship.PosicionActualX()+1) {
                if (listaBolas.get(i).AltoBola() >= ship.alturaNabe()+1 && listaBolas.get(i).getY() <= this.getHeight()) {


                    if(posicion!=i){

                        vidas=vidas-1;
                        posicion=i;

                    }

                }
            }
                    if (listaBolas.get(i).getY() > this.getWidth()) {
                        listaBolas.remove(i);
                        posicion=posicion-1;
                        System.out.println("Borrada");

                }

            }




        }
        public static void Usuario(String Usuario){
        User=Usuario;

        }




    }



