package com.solidinvictus.earthdefender;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.solidinvictus.earthdefender.screens.MainMenuScreen;
import com.solidinvictus.earthdefender.tools.EarthDefenderDataBase;
import com.solidinvictus.earthdefender.tools.GameCamera;
import com.solidinvictus.earthdefender.tools.ScrollingBackground;

public class SpaceGame extends Game {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 640;
	public SpriteBatch batch;
	public ScrollingBackground scrollingBackground;
	public static boolean IS_MOBIL = false;
	public GameCamera cam;
	private EarthDefenderDataBase dataBase;


	public SpaceGame(EarthDefenderDataBase db) {
		super();
		dataBase=db;
	}

	public SpaceGame() {
		super();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new GameCamera(WIDTH, HEIGHT);


		if(Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS ){
			IS_MOBIL = true;
		}
		IS_MOBIL = true;

		this.scrollingBackground = new ScrollingBackground();
		//******************
		//this.setScreen(new MainMenuScreen(this));
		this.setScreen(new MainMenuScreen(this));

	}

	public EarthDefenderDataBase getDataBase() {
		return dataBase;
	}

	public void setDataBase(EarthDefenderDataBase dataBase) {
		this.dataBase = dataBase;
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(cam.combined());
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}

	@Override
	public void resize(int width, int height){
		cam.update(width, height);
		super.resize(width, height);
	}
}
