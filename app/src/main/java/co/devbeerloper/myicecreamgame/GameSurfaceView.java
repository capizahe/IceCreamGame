package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;

public class GameSurfaceView extends SurfaceView implements Runnable {

    private boolean isPlaying;
    private IceCreamCar icecreamCar;
    private Kid kid;
    private Adult adult;
    private Paint paint,textPaint;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private int points=0;
    private Random random;
    private boolean start_points;
    private int counter=0;

    private float screenWith,screenHeight;

    /**
     * Contructor
     * @param context
     */
    public GameSurfaceView(Context context, float screenWith, float screenHeight) {
        super(context);
        points=0;
        random= new Random();
        this.screenWith=screenWith;
        this.screenHeight=screenHeight;
        icecreamCar = new IceCreamCar(context, screenWith, screenHeight);
        kid = new Kid(context,screenWith,screenHeight);
        adult = new Adult(context,screenWith,screenHeight);
        paint = new Paint();
        textPaint = new Paint();
        textPaint.setTextSize(60);
        textPaint.setFakeBoldText(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        holder = getHolder();
        isPlaying = false;
        Toast.makeText(context,"Toca 5 veces para empezar a jugar", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method implemented from runnable interface
     */
    @Override
    public void run() {
        while (isPlaying) {
            updateInfo();
            paintFrame();

        }
    }

    private void updateInfo() {

        icecreamCar.updateInfo ();
        kid.updateInfo();
        adult.updateInfo();
        if(start_points) {

            if (icecreamCar.carIntersectsObject(kid)) {
                this.points += 10;
                kid = new Kid(getContext(), this.screenWith, this.screenHeight);
                Log.i("POINTS ", this.points + "");
                textPaint.setARGB(random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt());

            }

            if (icecreamCar.carIntersectsObject(adult)) {
                this.points = 0;
                adult = new Adult(getContext(), this.screenWith, this.screenHeight);
            }
        }

        if(kid.isIsgone()) {
            kid = new Kid(getContext(), this.screenWith, this.screenHeight);
        }
        if(adult.isIsgone()){
            adult = new Adult(getContext(), this.screenWith, this.screenHeight);
        }


    }


    private void paintFrame() {
        if (holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.CYAN);
            canvas.drawBitmap(icecreamCar.getSpriteIcecreamCar(),icecreamCar.getPositionX(),icecreamCar.getPositionY(),paint);
            canvas.drawBitmap(kid.getSpritekid(),kid.getPositionX(),kid.getPositionY(),paint);
            canvas.drawBitmap(adult.getSpritekid(),adult.getPositionX(),adult.getPositionY(),paint);
            canvas.drawText("POINTS:"+points,this.screenWith-400,this.screenHeight-20,textPaint);
            holder.unlockCanvasAndPost(canvas);
        }

    }


    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void resume() {


        try {
            isPlaying = true;
            gameplayThread = new Thread(this);
            gameplayThread.start();
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Detect the action of the touch event
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                System.out.println("TOUCH UP - STOP JUMPING");
                icecreamCar.setJumping(false);
                break;
            case MotionEvent.ACTION_DOWN:
                counter++;
                if(counter == 5)
                start_points = true;
                //Log.i("(x,y) = ", motionEvent.getX() +","+motionEvent.getY());
                System.out.println("TOUCH DOWN - JUMP");
                icecreamCar.setJumping(true);
                break;
        }
        return true;
    }


}
