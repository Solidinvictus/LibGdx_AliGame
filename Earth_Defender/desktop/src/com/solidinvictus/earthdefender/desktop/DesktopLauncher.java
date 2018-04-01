package com.solidinvictus.earthdefender.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.solidinvictus.earthdefender.SpaceGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SpaceGame(), config);
		//Definimos el tamaño de nuestra pantalla del escritorio y de clquier S.O.
		config.width = SpaceGame.WIDTH;
		config.height = SpaceGame.HEIGHT;
		config.resizable = false;    //Para que el usuario no pueda redimensionar la pantalla
	}
}
