package spinoffpyme.com.shipgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by adrian.montes on 11/2/18.
 */

//EN ESTA CLASE ESTA TODO LO RELACIONADO CON LAS PIEDRAS, S
public class BallsDown {
    private int x,y=0;
    private GameView gameView;
    private Bitmap bmp;
    private int ancho,alto=0;




    public BallsDown(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        //ahora obtengo el ancho y el alto de la pantalla
        this.ancho=gameView.getWidth();
        this.alto=gameView.getHeight();

        x=new Random().nextInt(ancho);
        y=0;
    }

    public void update(){

        //if(x+xSpeed > gameView.getWidth() - bmp.getWidth()){
//
  //      x=gameView.getWidth()-bmp.getWidth();
    //
      //      return;
       // }

        y=y+40;



    }

    public int getX() {
        return x;
    }
    public int getY(){
        return y;
    }
    public int anchoBola(){

return bmp.getWidth();
    }

    public int AltoBola(){

        return y+bmp.getHeight();
    }


    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp,x,y,null);
    }





}
