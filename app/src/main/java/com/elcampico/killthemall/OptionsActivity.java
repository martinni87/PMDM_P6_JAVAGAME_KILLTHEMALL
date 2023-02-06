package com.elcampico.killthemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity {

    Button btnStartGame;
    TextView numberOfEnemies;
    TextView speedOfCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_options);

        btnStartGame = (Button)findViewById(R.id.btn_toWar);
        numberOfEnemies = (TextView) findViewById(R.id.num_enemies);
        speedOfCharacters = (TextView) findViewById(R.id.speed);

        btnStartGame.setOnClickListener(v -> {
            int enemies = 5;
            int speed = 5;

            try{
                enemies = Integer.parseInt(numberOfEnemies.getText().toString());
            } catch (Exception e){
                Log.d("MARTIN DEBUG", "ERROR: Variable enemies nula. Se asigna 0." + e.getMessage());
            }
            try{
                speed = Integer.parseInt(speedOfCharacters.getText().toString());
            } catch (Exception e){
                Log.d("MARTIN DEBUG", "ERROR: Variable speed nula. Se asigna 0." + e.getMessage());
            }
            Log.d("MARTIN DEBUG", "Enemies: " + enemies + ". speed: " + speed);
            startActivity(new Intent(this,GameSetActivity.class));
        });
    }
}