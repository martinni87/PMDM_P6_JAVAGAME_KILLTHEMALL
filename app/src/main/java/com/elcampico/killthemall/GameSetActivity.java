package com.elcampico.killthemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class GameSetActivity extends AppCompatActivity {
    private int enemies = 23;
    private int speed = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        enemies = getIntent().getExtras().getInt("enemies");
        speed = getIntent().getExtras().getInt("speed");
        setContentView(new GameView(this,enemies,speed));

    }
}