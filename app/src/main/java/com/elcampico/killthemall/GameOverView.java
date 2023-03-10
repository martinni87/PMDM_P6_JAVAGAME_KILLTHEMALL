package com.elcampico.killthemall;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author Martín Antonio Córdoba Getar
 * @version 1.0-alfa
 * Fecha inicial: 7 de febrero de 2022
 */
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

    /**
     * Método onDraw para pintar en el canvas el texto Game Over e indicar el puntaje ganado tras la partida.
     * @param canvas
     */
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
