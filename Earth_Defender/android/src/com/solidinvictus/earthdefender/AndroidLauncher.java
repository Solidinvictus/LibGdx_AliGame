package com.solidinvictus.earthdefender;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import database.DataBaseAndroid;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		DataBaseAndroid androidDB = new DataBaseAndroid(this);
        initialize(new SpaceGame(androidDB), config);
		//initialize( new AndroidActivity(), config);
    }
}
