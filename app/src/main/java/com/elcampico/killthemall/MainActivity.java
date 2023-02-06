package com.elcampico.killthemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

/**
 * @author Martín Antonio Córdoba Getar
 * @version 1.0-alfa
 * Fecha inicial: 7 de febrero de 2022
 */
public class MainActivity extends AppCompatActivity {

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(v -> {
            startActivity(new Intent(this,OptionsActivity.class));
        });
//
    }
}