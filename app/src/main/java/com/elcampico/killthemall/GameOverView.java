package com.elcampico.killthemall;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameOverView {

    private int screenWidth;
    private int screenHeight;

    private Paint paint;
    private int score;

    public GameOverView(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.score = 0;
        paint = new Paint();
    }

    public void onDraw(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Game Over.",screenWidth/2,screenHeight/2-100,paint);
        paint.setTextSize(50);
        canvas.drawText("Has conseguido un puntaje de: " + score,screenWidth/2,screenHeight/2,paint);
    }

    public void setScore (int score){
        this.score = score;
    }

}
