package com.solidinvictus.earthdefender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.solidinvictus.earthdefender.SpaceGame;

import com.badlogic.gdx.graphics.Color;
import com.solidinvictus.earthdefender.tools.EarthDefenderDataBase;
import com.solidinvictus.earthdefender.tools.ScrollingBackground;

/**
 * Created by Ali on 11/03/2018.
 */

public class GameOverScreen implements Screen {

    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;

    EarthDefenderDataBase database;

    SpaceGame game;
    int score, highScore;

    Texture gameOverBanner;
    BitmapFont scoreFont;

    public GameOverScreen(SpaceGame game, int score) {
        this.game = game;
        this.score = score;
        //Seteamos las preferencias y cogemos el valor de la maxima puntuacion desde el archivo del guardado
        Preferences prefs = Gdx.app.getPreferences("earthdefender");
        this.highScore = prefs.getInteger("highscore",0);
        //Actualizamos el max score dentro de preferencias si se supera la puntuacion maxima registrada
        database = game.getDataBase();
        database.saveCurrentGame(highScore);

        if(score > highScore && !game.IS_MOBIL) {
            prefs.putInteger("highscore", score);

        }
        prefs.flush();  //Para guardar el dato en Desktop
        /**
         * Aqui insertamos en la base de datos si emulamos en Android
         */
        if(score > highScore && game.IS_MOBIL){
            database = game.getDataBase();
            /**
             * //Se almacena la mayor puntuacion
             */
            database.getCurrentGame(score);
            database.getBestScore().setScore(score);
            database.saveCurrentGame(score);


        }

        //Cargamos texturas y fonts
        gameOverBanner = new Texture(("game_over.png"));
        scoreFont = new BitmapFont(Gdx.files.internal("score.fnt"));

        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
    }




    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        //Renderizamos el fondo de pantalla gracias a nuestra clase ScollingBackground
        game.scrollingBackground.updateAndRender(delta, game.batch);

        //Centramos nuestro banner y hacemos un padding de 15 px
        //El GlyphLayout dibuja desde arriba hacia abajo
        game.batch.draw(gameOverBanner, SpaceGame.WIDTH / 2 - BANNER_WIDTH /2, SpaceGame.HEIGHT - BANNER_HEIGHT -15, BANNER_WIDTH, BANNER_HEIGHT);
        GlyphLayout scoreLayout;
        GlyphLayout highScoreLayout;
        GlyphLayout tryAgainLayout;
        GlyphLayout mainMenuLayout;

        scoreLayout = new GlyphLayout(scoreFont,"Score: \n"+score, Color.WHITE,0,Align.left,false);
        //*****
        if(!game.IS_MOBIL) {
            highScoreLayout = new GlyphLayout(scoreFont, "High Score: \n" + highScore, Color.WHITE, 0, Align.left, false);

        }else{
            database.endCurrentGame(highScore);
            highScoreLayout = new GlyphLayout(scoreFont, "High Score: \n" + database.getBestScore().getScore(), Color.WHITE, 0, Align.left, false);
        }

        scoreFont.draw(game.batch, scoreLayout, SpaceGame.WIDTH / 2 - scoreLayout.width/2, SpaceGame.HEIGHT- BANNER_HEIGHT - 15 * 2);
        scoreFont.draw(game.batch, highScoreLayout, SpaceGame.WIDTH /2 - highScoreLayout.width/2, SpaceGame.HEIGHT - BANNER_HEIGHT - scoreLayout.height - 15 * 3);
        tryAgainLayout= new GlyphLayout(scoreFont,"Try Again");
        mainMenuLayout = new GlyphLayout(scoreFont,"Main menu");

        float tryAgainX = SpaceGame.WIDTH  /2 - tryAgainLayout.width / 2;
        float tryAgainY = SpaceGame.HEIGHT / 2 - tryAgainLayout.height / 2;
        float mainMenuX = SpaceGame.WIDTH  /2 - mainMenuLayout.width / 2;
        float mainMenuY = SpaceGame.HEIGHT / 2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 15;

        //Para detectar la seleccion del jugador
        float touchX = game.cam.getInputInGameWorld().x, touchY = SpaceGame.HEIGHT - game.cam.getInputInGameWorld().y;

        //Si try again ha sido pulsada
        if(Gdx.input.isTouched()){
            //Try Again
            if(touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY){
                this.dispose();
                game.batch.end();
                game.setScreen(new MainGameScreen(game));
                return;
            }

            //Main Menu
            if(touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY){
                this.dispose();
                game.batch.end();
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
        //Dibujando los botones
        scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);


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
