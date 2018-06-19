package spinoffpyme.com.shipgame;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by tomas on 19/01/2018.
 */

    //Este hilo lanza continuamente el gameview para pintar todo el rato la pantalla, tiene un sleep de 100 mls
    // cada vez que queramos que la pantalla se vaya cambiando vamos a tener que hacerlo en cada clase que este metodo
    //Va actualizando
public class GameLoopThread extends Thread {
    private final long FPS=50;
    private GameView view;
    private boolean running =false;

    public GameLoopThread(GameView view) {
        this.view = view;
    }
    public void setRunning(boolean run){
        this.running=run;
    }


    @SuppressLint("WrongCall")
    @Override
    public void run() {
        long tiksPS=10/FPS;
        long startTime;
        long sleepTime;

        while(running){
            Canvas c=null;
            startTime= System.currentTimeMillis();
            try{
                c=view.getHolder().lockCanvas();
                synchronized (view.getHolder()){
                    view.onDraw(c);
                    view.CreacionDeBolas(c);
                    view.ComprobarPosicionShip();

                    if(GameView.vidas==0) {
                        Paint pincel = new Paint();
                        pincel.setARGB(255, 255, 0, 0);
                        pincel.setTextSize(100);
                        pincel.setTypeface(Typeface.SANS_SERIF);
                        c.drawText("Fin de la partida",view.getWidth()/4, 500, pincel);
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(c!=null){
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime=tiksPS-(System.currentTimeMillis()-startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            }catch (Exception e){}



        }


    }
}
