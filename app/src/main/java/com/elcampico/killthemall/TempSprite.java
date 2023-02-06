package com.elcampico.killthemall;

import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TempSprite {
    private float x;
    private float y;
    private Bitmap bmp;
    private int life = 15; //15 ticks: cada vez que se llama el método update (tiempo que dura el sprite)
    private List<TempSprite> temps; //El sprite se almacena aquí

    public TempSprite(List<TempSprite> temps, GameView gameView, float x, float y, Bitmap bmp) {
        this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0), gameView.getWidth() - bmp.getWidth());
        this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0), gameView.getHeight() - bmp.getHeight());
        this.bmp = bmp;
        this.temps = temps;
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

    private void update() {
        //Aquí vamos restando el tiempo de vida del sprite
        if (--life < 1) {
            //Lo eliminapos cuando sea menor de 1 (0)
            temps.remove(this);
        }
    }
}