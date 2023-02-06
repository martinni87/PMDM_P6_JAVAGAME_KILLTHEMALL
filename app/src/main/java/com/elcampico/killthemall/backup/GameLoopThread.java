//package com.elcampico.killthemall;
//
//import android.graphics.Canvas;
//
//public class GameLoopThread extends Thread{
//    static final long FPS = 10;
//    private GameView view;
//    private boolean running = false;
//
//    public GameLoopThread(GameView view){
//        //Construimos el objeto en esta vista GameView
//        this.view = view;
//    }
//    public void setRunning(boolean run){
//        running = run;
//    }
//
//    @Override
//    public void run(){
//        //Ticks = 1000ms/10frames
//        long ticksPS = 1000 / FPS;
//        long startTime;
//        long sleepTime;
//
//        //Mientras running sea true, el código continua ejecutándose.
//        while(running){
//            Canvas c = null;
//            startTime = System.currentTimeMillis();
//            try{
//                //Pintado del canvas
//                c = view.getHolder().lockCanvas();
//                //En cada vuelta se llama al método onDraw que pinta los sprites en el canvas
//                synchronized (view.getHolder()){
//                    view.onDraw(c);
//                }
//            }
//            finally{
//                if(c != null){
//                    view.getHolder().unlockCanvasAndPost(c);
//                }
//            }
//            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
//            try{
//                if(sleepTime > 0)
//                    sleep(sleepTime);
//                else
//                    sleep(10);
//            }catch(Exception e) {}
//        }
//    }
//}