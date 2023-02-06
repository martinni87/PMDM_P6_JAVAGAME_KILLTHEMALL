package com.elcampico.killthemall;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * @author Martín Antonio Córdoba Getar
 * @version 1.0-alfa
 * Fecha inicial: 7 de febrero de 2022
 */
public class Sprite {
    /**
     * Para la selección del sprite dentro del bitmap:
     * Dirección = 0 arriba, 1 izquierda, 2 abajo, 3 derecha
     * Animación = 3 espalda, 1 izquierda, 0 frente, 2 derecha.
     */
    final int[] DIRECTION_TO_ANIMATION_MAP = {3,1,0,2};
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
//    private static final int MAX_SPEED = 15;
    private int selectedSpeed;
    private int x = 0;
    private int y = 0;
    private int xSpeed;
    private int ySpeed;
    private GameView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int characterClass;

    public Sprite(GameView gameView, Bitmap bmp, int speed, int characterClass) {
        this.gameView = gameView;
        this.characterClass = characterClass;
        this.bmp = bmp;
        //El ancho del bitmap entre el número de columnas, nos da el ancho de 1 sprite
        this.width = bmp.getWidth() / BMP_COLUMNS;
        //El alto del bitmap entre el número de filas, nos da el alto de 1 sprite
        this.height = bmp.getHeight() / BMP_ROWS;
        //Inicializamos un objeto random
        Random rnd = new Random();
        //Asignamos valores random a las velocidades x y
        selectedSpeed = speed;
        xSpeed = rnd.nextInt(selectedSpeed * 2) - selectedSpeed;
        ySpeed = rnd.nextInt(selectedSpeed * 2) - selectedSpeed;
//        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
//        ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        //Asignamos puntos de partida random x, y
        x = rnd.nextInt(gameView.getWidth() - width);
        y = rnd.nextInt(gameView.getHeight() - height);
    }

    private void update (){
        //Con estas dos condiciones verificamos si el sprite debe avanzar o retroceder porque ha llegado al límite.
        //Modificamos la posición x a velocidad xSpeed.
        if ((x >= gameView.getWidth() - width - xSpeed) || (x + xSpeed <= 0)){
            xSpeed = -xSpeed;
        }
        x = x + xSpeed;

        //Modificamos la posición y a velocidad xSpeed.
        if ((y >= gameView.getHeight() - height - ySpeed) || (y + ySpeed <= 0)){
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;

        //Con esto vamos cambiando el recorte del frame utilizado, siempre toma valores de 0, 1 o 2
        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDraw (Canvas canvas){
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        //Rectángulo fuente dentro del bitmap srcX selecciona la columna del bitmap y srcY la fila.
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        //Rectángulo destino en el canvas (donde voy a pintar el fragmento de bitmap)
        Rect dst = new Rect (x, y, x + width, y + height);
        canvas.drawBitmap(bmp,src,dst,null);
    }

    private int getAnimationRow(){
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public boolean isCollision(float xTouched, float yTouched) {
        //xTouch yTouch es posición donde el usuario pulsa. X Y es ubicación del sprite en el canvas.
        /**
         * Si xTouched < x, entonces hemos pulsado fuera.
         * Si xTouched > x + width, hemos pulsado fuera.
         */
        //El return dará true cuando pulsemos en el sprite y false cuando pulsemos fuera.
        return xTouched > x && xTouched < x + width && yTouched > y && yTouched < y + height;
    }

    public int getCharacterClass(){
        return this.characterClass;
    }
}