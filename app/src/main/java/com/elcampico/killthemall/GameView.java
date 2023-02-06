package com.elcampico.killthemall;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView {
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private long lastClick;
    private Bitmap bmpBlood;
    MediaPlayer mediaPlayer;
    private SoundPool soundPool;
    private int angelScream = -1;
    private int badGuyScream = -1;
    private int gameOverSound = -1;
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private int enemies;
    private int selectedSpeed;
    private int playerLife = 3;
    private int playerScore = 0;
    private int level = 1;  //TODO: en futura actualización del juego, cuando se hayan matado a todos los enemigos, aumentamos la dificultad.
                            //TODO: establecer la velocidad y la cantidad de enemigos en función de la velocidad.
    private GameOverView gameOverView;
    private boolean gameOver = false;
    private boolean gameCompleted = false;

    public GameView(Context context, int enemies, int speed) {
        super(context);
        mediaPlayer = MediaPlayer.create(getContext(),R.raw.music);
        this.enemies = enemies;
        this.selectedSpeed = speed;
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
                mediaPlayer.setLooping(true); //Para que se reproduzca en bucle hasta que acabe el juego
                mediaPlayer.start();
                gameOverView = new GameOverView(getWidth(), getHeight());
                gameCompleted = true;
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
        bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try{
            //Creamos los asset managers para poder gestionar los sonidos cortos en este contexto
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            //Los descriptores almacenan la información relativa al fichero que se debe reproducir
            //y la asocian a una variable integer.
            descriptor = assetManager.openFd("female_scream.wav");
            angelScream = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("dead_man.wav");
            badGuyScream = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("gameover.mp3");
            gameOverSound = soundPool.load(descriptor, 0);

        } catch(IOException e) {
            //Imprime error en logcat en caso de error.
            Log.d("MARTIN DEBUG", "Error en la carga de audios: " + e.getMessage());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if (!gameOver){
            for (int i = temps.size() -1; i >= 0; i--){
                temps.get(i).onDraw(canvas);
            }
            for (Sprite sprite: sprites){
                sprite.onDraw(canvas);
            }
        }
        else {
            gameOverView.setScore(playerScore);
            gameOverView.onDraw(canvas);
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
                        if (sprite.getCharacterClass() == 1){
                            //Se ha matado a un ángel ==> perdemos 1 vida
                            playerLife--;
                            soundPool.play(angelScream, 1, 1, 0, 0, 1);
                            if (playerLife == 0){
                                gameOver = true;
                                mediaPlayer.stop();
                                soundPool.play(gameOverSound,1,1,0,0,1);
                            }
                        }
                        else{
                            //Se ha matado un badguy ==> ganamos 1 punto
                            playerScore++;
                            soundPool.play(badGuyScream, 5, 5, 0, 0, 1);
                        }
                        break;
                    }
                }
            }
        }
        return true;
    }

    private Sprite getSingleSprite(int resource, int characterClass){
        //Characterclass = 0 => Bad guy, Characterclass = 1 => angel
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this,bmp, selectedSpeed, characterClass);
    }

    private void createAllSprites(){
        for (int i = 0; i < enemies; i++){
            int badGuyId = (int)(Math.random()*6+1);
            switch (badGuyId){
                case 1: sprites.add(getSingleSprite(R.drawable.bad1, 0));break;
                case 2: sprites.add(getSingleSprite(R.drawable.bad2, 0));break;
                case 3: sprites.add(getSingleSprite(R.drawable.bad3, 0));break;
                case 4: sprites.add(getSingleSprite(R.drawable.bad4, 0));break;
                case 5: sprites.add(getSingleSprite(R.drawable.bad5, 0));break;
                case 6: sprites.add(getSingleSprite(R.drawable.bad6, 0));break;
                default:
                    Log.d("MARTIN DEBUG", "Sprite no existe. badGuyId: " + badGuyId);
            }
            int angelsId = (int)(Math.random()*6+1);
            switch (angelsId){
                case 1: sprites.add(getSingleSprite(R.drawable.good1,1));break;
                case 2: sprites.add(getSingleSprite(R.drawable.good2,1));break;
                case 3: sprites.add(getSingleSprite(R.drawable.good3,1));break;
                case 4: sprites.add(getSingleSprite(R.drawable.good4,1));break;
                case 5: sprites.add(getSingleSprite(R.drawable.good5,1));break;
                case 6: sprites.add(getSingleSprite(R.drawable.good6,1));break;
                default:
                    Log.d("MARTIN DEBUG", "Sprite no existe. angelsId: " + angelsId);
            }

        }






    }
}