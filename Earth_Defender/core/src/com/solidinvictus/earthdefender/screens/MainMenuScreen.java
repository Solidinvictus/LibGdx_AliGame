package com.solidinvictus.earthdefender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.solidinvictus.earthdefender.SpaceGame;
import com.solidinvictus.earthdefender.tools.ScrollingBackground;

/**
 * Created by Ali on 08/03/2018.
 */

public class MainMenuScreen implements Screen {
    //Constantes
    private static final int EXIT_BUTTON_WITH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;
    private static final int PLAY_BUTTON_WITH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_Y = 230;

    //Variables
    SpaceGame game;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;


    //Constructor
    public MainMenuScreen(SpaceGame game) {
        this.game = game;
        /**
         * Instanciamos nuestras texturas cargando las imagenes dentro de assets
         */
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");

        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        /**
         * Color de fondo del menu
         */
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        //Renderizamos el fondo de pantalla gracias a nuestra clase ScollingBackground
        game.scrollingBackground.updateAndRender(delta, game.batch);

        int x = SpaceGame.WIDTH / 2 - EXIT_BUTTON_WITH / 2;     //Para facilitanos la  vida
        //Para resaltar la imagen al poner el cursor (en verdad es un switch entre 2 imagenes)
        if (game.cam.getInputInGameWorld().x < x + EXIT_BUTTON_WITH && game.cam.getInputInGameWorld().x > x
                && SpaceGame.HEIGHT - game.cam.getInputInGameWorld().y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT
                && SpaceGame.HEIGHT - game.cam.getInputInGameWorld().y  > EXIT_BUTTON_Y) {
            game.batch.draw(exitButtonActive, SpaceGame.WIDTH / 2 - EXIT_BUTTON_WITH / 2, EXIT_BUTTON_Y, EXIT_BUTTON_WITH, EXIT_BUTTON_HEIGHT);
            //Evento para salir de la app al clikear en la imagen
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, SpaceGame.WIDTH / 2 - EXIT_BUTTON_WITH / 2, EXIT_BUTTON_Y, EXIT_BUTTON_WITH, EXIT_BUTTON_HEIGHT);
        }

        x = SpaceGame.WIDTH / 2 - PLAY_BUTTON_WITH / 2;
        if (game.cam.getInputInGameWorld().x < x + PLAY_BUTTON_WITH && game.cam.getInputInGameWorld().x > x
                && SpaceGame.HEIGHT - game.cam.getInputInGameWorld().y  < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT
                && SpaceGame.HEIGHT - game.cam.getInputInGameWorld().y  > PLAY_BUTTON_Y) {
            game.batch.draw(playButtonActive, SpaceGame.WIDTH / 2 - PLAY_BUTTON_WITH / 2, PLAY_BUTTON_Y, PLAY_BUTTON_WITH, PLAY_BUTTON_HEIGHT);
            //Evento del click para entrar en la pantalla del juego
            if (Gdx.input.justTouched()) {
                this.dispose();     //Cerramos el Screen antes de ir a la pantalla nueva [Buenas practicas y alivio para la gráfica]
                game.setScreen(new MainGameScreen(game));
            }
        } else {
            game.batch.draw(playButtonInactive, SpaceGame.WIDTH / 2 - PLAY_BUTTON_WITH / 2, PLAY_BUTTON_Y, PLAY_BUTTON_WITH, PLAY_BUTTON_HEIGHT);
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
