package spinoffpyme.com.shipgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by tomas on 19/01/2018.
 */

public class Ship {
    private int x,y=0;
    private GameView gameView;
    private Bitmap bmp;
    private int width,height=0;
    private int xSpeed=0;

    //En la clase Ship tenemos todo lo realacionado con la nave, su movimiento y cuando se pinta,
    // cada vez que llaman a su onDraw
    public Ship(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width=gameView.getWidth();
        this.height=gameView.getHeight();

        x=width/2;
        y=height-bmp.getHeight();
    }


    public void update(){
    if(x+xSpeed > gameView.getWidth() - bmp.getWidth()){

    x=gameView.getWidth()-bmp.getWidth();
    return;
        }
        if(x+xSpeed <= 0){

        x=0;
        return;
        }

        x=x+xSpeed;


    }



    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp,x,y,null);
    }

    public void movLeft(int movimiento) {

        xSpeed=movimiento;
    }

    public void moveRight(int movimiento) {

        xSpeed=-movimiento;
    }

    public int PosicionActualX(){

        return x;

    }

    public int anchoNave(){

        return this.bmp.getWidth();
    }

    public int alturaNabe(){
        return y;
    }


}

