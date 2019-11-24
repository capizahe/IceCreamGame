package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Random;

public class Kid {


    public  float INIT_X;
    public  float INIT_Y;


    public static final int  SPRITE_SIZE_WIDTH = 150;
    public static final int  SPRITE_SIZE_HEIGHT = 170;
    public static final float GRAVITY_FORCE=10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spritekid;

    private boolean isgone;

    public Kid(Context context , float screenWidth, float screenHeigth){

        Random random = new Random();
        this.speed = 10;

        Bitmap originalbitmap = BitmapFactory.decodeResource(
                context.getResources(),R.drawable.kid
        );
        this.spritekid = Bitmap.createScaledBitmap(originalbitmap,SPRITE_SIZE_WIDTH,SPRITE_SIZE_HEIGHT,false);

        this.maxX = screenWidth - spritekid.getWidth();
        this.maxY = screenHeigth - spritekid.getHeight();


        this.positionX = this.maxX;
        this.positionY = this.maxY-random.nextInt((int)this.maxY);

        this.INIT_X = this.positionX;
        this.INIT_Y = this.positionY;
    }


    public int getMIN_SPEED() {
        return MIN_SPEED;
    }

    public int getMAX_SPEED() {
        return MAX_SPEED;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getSpritekid() {
        return spritekid;
    }

    public void setSpritekid(Bitmap spritekid) {
        this.spritekid = spritekid;
    }

    public boolean isIsgone() {
        return isgone;
    }

    public void setIsgone(boolean isgone) {
        this.isgone = isgone;
    }

    public void updateInfo () {
        this.positionX -= speed;
        if(this.positionX <= 0)
            isgone = true;
      //  Log.i("Kid position", "X:" +this.getPositionX() + " Y:"+this.getPositionY() );
    }

    public void restartKidPosition(){

        this.positionX = this.INIT_X;
        this.positionY = this.INIT_Y;

    }
}
