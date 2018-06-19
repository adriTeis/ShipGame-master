package spinoffpyme.com.shipgame;

import android.util.Log;

/**
 * Created by adrian.montes on 14/2/18.
 */
//EN ESTE HILO ACTUALIZO LA PANTALLA MAS RAPIDO SEGUN LA DIFICULTAD QUE SE LE DIGA, PARA QUE LAS PIEDRAS
    //VAYAN MAS RAPIDO
public class HiloCreadorPiedras extends Thread {

    private final long FPS=100;
    private GameView view;
    private boolean running= false;
    private int dificultad;

    public HiloCreadorPiedras(GameView view,int dificultad) {
        this.view = view;
        this.dificultad=dificultad;
    }
    public void setRunning(boolean run){
        this.running=run;
    }
    public void setDificultad(int dificultad){
        this.dificultad= this.dificultad;

    }

public void DificultadFacil(){

        long tiksPS=10000/10;
        long startTime;
        long TiempoDormir;

        while(running){
            startTime= System.currentTimeMillis();

            view.createBalls();

            //Ahora le digo el tiempo en el que deve ejecutarse
            TiempoDormir=tiksPS-(System.currentTimeMillis()-startTime);
            try {
                if (TiempoDormir > 0)
                    sleep(TiempoDormir);
                else
                    sleep(10);
            }catch (Exception e){}
        }


    }
    public void DifucltadDificil(){
        long tiksPS=10000/70;
        long startTime;
        long TiempoDormir;

        while(running){
            startTime= System.currentTimeMillis();

            view.createBalls();

            //Ahora le digo el tiempo en el que deve ejecutarse
            TiempoDormir=tiksPS-(System.currentTimeMillis()-startTime);
            try {
                if (TiempoDormir > 0)
                    sleep(TiempoDormir);
                else
                    sleep(10);
            }catch (Exception e){}
        }
    }
    public void DifucltadMedia(){
        long tiksPS=10000/35;
        long startTime;
        long TiempoDormir;

        while(running){
            startTime= System.currentTimeMillis();

            view.createBalls();

            //Ahora le digo el tiempo en el que deve ejecutarse
            TiempoDormir=tiksPS-(System.currentTimeMillis()-startTime);
            try {
                if (TiempoDormir > 0)
                    sleep(TiempoDormir);
                else
                    sleep(10);
            }catch (Exception e){}
        }
    }






    public void run(){



        if(this.dificultad==1){
            Log.i("Dificultad Facil", String.valueOf(dificultad));
            DificultadFacil();

        }else if(this.dificultad==2){
            Log.i("Dificultad Media", String.valueOf(dificultad));
            DifucltadMedia();

        }else if(this.dificultad==3){
            Log.i("Dificultad Dificil", String.valueOf(dificultad));
            DifucltadDificil();

        }







    }
}






