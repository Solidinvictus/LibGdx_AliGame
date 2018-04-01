package com.solidinvictus.earthdefender;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AndroidActivity extends Activity {
    SharedPreferences myPrefs;
    SharedPreferences.Editor editor;
    boolean isEnable;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);

        /**
         * Preferences
         * Habilitamos el boton  o no y le cambiamos el color
         */

        myPrefs = getSharedPreferences("ButtonDisable", Context.MODE_PRIVATE);

        myPrefs = getSharedPreferences("ButtonDisable", Context.MODE_PRIVATE);
        editor = myPrefs.edit();

        isEnable = myPrefs.getBoolean("ButtonDisable", false);
        myPrefs.edit().putBoolean("ButtonDisable", isEnable).apply();

        if (isEnable) {
            button2.setEnabled(false);
            button2.setBackgroundColor(Color.RED);
        } else {
            button2.setEnabled(true);
            button2.setBackgroundColor(Color.GRAY);
        }

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getBaseContext(),"Claim", Toast.LENGTH_SHORT).show();

                editor.putBoolean("ButtonDisable", false);
                editor.commit();

                button2.setEnabled(false);
                button2.setBackgroundColor(Color.GRAY);
            }
        });

    }


    public void startGame(View v){
        Intent i=new Intent(this,AndroidLauncher.class);
        startActivity(i);
    }






}
