package com.elcampico.killthemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

/**
 * @author Martín Antonio Córdoba Getar
 * @version 1.0-alfa
 * Fecha inicial: 7 de febrero de 2022
 */
public class GameSetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        int enemies = getIntent().getExtras().getInt("enemies");
        int speed = getIntent().getExtras().getInt("speed");
        setContentView(new GameView(this,enemies,speed));

    }
}