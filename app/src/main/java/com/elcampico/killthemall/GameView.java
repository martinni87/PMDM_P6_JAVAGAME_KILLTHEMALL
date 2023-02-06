package com.elcampico.killthemall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView {
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private long lastClick;
    private Bitmap bmpBlood;
    private List<TempSprite> temps = new ArrayList<TempSprite>();

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createAllSprites();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        for (int i = temps.size() -1; i >= 0; i--){
            temps.get(i).onDraw(canvas);
        }
        for (Sprite sprite: sprites){
            sprite.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick > 500){
            lastClick = System.currentTimeMillis();
            synchronized (getHolder()){
                float xTouched = event.getX();
                float yTouched = event.getY();
                for (int i = sprites.size()-1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    //Si la siguiente condición es true, se elimina el sprite.
                    if (sprite.isCollision(xTouched,yTouched)) {
                        sprites.remove(sprite);
                        temps.add(new TempSprite(temps, this, xTouched, yTouched, bmpBlood));
                        break;
                    }
                }
            }
        }
        return true;
    }

    private Sprite getSingleSprite(int resource){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this,bmp);
    }

    private void createAllSprites(){
        sprites.add(getSingleSprite(R.drawable.bad1));
        sprites.add(getSingleSprite(R.drawable.bad2));
        sprites.add(getSingleSprite(R.drawable.bad3));
        sprites.add(getSingleSprite(R.drawable.bad4));
        sprites.add(getSingleSprite(R.drawable.bad5));
        sprites.add(getSingleSprite(R.drawable.bad6));
        sprites.add(getSingleSprite(R.drawable.good1));
        sprites.add(getSingleSprite(R.drawable.good2));
        sprites.add(getSingleSprite(R.drawable.good3));
        sprites.add(getSingleSprite(R.drawable.good4));
        sprites.add(getSingleSprite(R.drawable.good5));
        sprites.add(getSingleSprite(R.drawable.good6));
    }
}