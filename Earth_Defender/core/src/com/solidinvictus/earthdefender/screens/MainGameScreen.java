package com.solidinvictus.earthdefender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.solidinvictus.earthdefender.Entities.Asteroid;
import com.solidinvictus.earthdefender.Entities.Bullet;
import com.solidinvictus.earthdefender.Entities.Explosion;
import com.solidinvictus.earthdefender.SpaceGame;
import com.solidinvictus.earthdefender.tools.CollisionReact;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ali on 08/03/2018.
 */
public class MainGameScreen implements Screen {
    /**
     * @VAR Constantes
     */
    public static final float SPEED = 300;
    public static final float SHIP_ANIMATION_SPEED = 0.5f;      //Cada medio segundo se cambia de frame de la nave cdo se anima
    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;
    //Cada 0.15s, se cambia de imagen al mover la nave
    public static final float TIME_SWITCH_ROLL = 0.25f; //Vble para la animacion de la nave-->cto tpo se necesita para cambiar de estado entre cada rollo
    public static final float SHOOT_WAIT_TIME = 0.3f;   //Para limitar el tpo entre disparo y disparo

    public static final float MIN_ASTEROID_SPAWN_TIME = 0.3f;
    public static final float MAX_ASTEROID_SPAWN_TIME = 0.6f;

    float x, y;        //Para las coordenadas de la nave
    SpaceGame game;
    float rollTimer;
    float stateTime;    //variable que nos indica cto tpo la animacion se esta ejecutando
    float shootTimer;
    ArrayList<Bullet> bullets;
    ArrayList<Asteroid> asteroids;
    ArrayList<Explosion> explosions;
    float asteroidSpawnTimer;
    Texture controls;

    Random random;


    Animation[] rolls;      //Clase interna de libGdx que importamos

    int roll;
    BitmapFont scoreFont;
    int score;
    float health; //0 = Muerto -- 1 = Max vida
    Texture blank;
    CollisionReact playerReact;

    public MainGameScreen(SpaceGame game) {

        this.game = game;
        y = 15;
        x = SpaceGame.WIDTH / 2 - SHIP_WIDTH / 2;
        rollTimer = 0;
        bullets = new ArrayList<Bullet>();
        asteroids = new ArrayList<Asteroid>();
        explosions = new ArrayList<Explosion>();
        scoreFont = new BitmapFont(Gdx.files.internal("score.fnt"));
        score = 0;      //Iniciamos el juego con 0 ptos
        health = 1;
        blank = new Texture("blank.png");
        playerReact = new CollisionReact(0, 0, SHIP_WIDTH, SHIP_HEIGHT);

        if(SpaceGame.IS_MOBIL){
            controls = new Texture("controls.png");
        }


        //Tenemos debido a nuestro rollo de imagenes para simular el movimiento
        //de la nave una matriz de dimension 2 de 5 * 2
        roll = 2;       //Estado del movimiento de la nave quieta
        rolls = new Animation[5];
        /**
         * Tenemos 5 estados de la nave y 2 frames de animacion por estado
         * Cargamos en memoria nuestro rollo de imagenes - estados y recotamos gracias
         * a la clase TextureRegion con su metodo split
         */
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);
        /**
         * Fijandonos en nuestro rollo de imagenes definimos los indices para los estados de los giros
         */
        rolls[0] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[2]); //Izquierda a tope -->Tercera fila
        rolls[1] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[1]); //Izquierda --> segunda fila
        rolls[2] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);  //Centro -->primera fila
        rolls[3] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[3]); //Derecha --> Cuarta fila
        rolls[4] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[4]); //Derecha a tope --> quinta fila y ultima

        game.scrollingBackground.setSpeedFixed(false);      //Para que se pueda acelerar
        shootTimer = 0;
        random = new Random();
        asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;

    }

    @Override
    public void show() {
        //img = new Texture("badlogic.jpg");

    }

    @Override
    public void render(float delta) {
        /**
         * Para el movimiento:
         * Multiplicamos por el deltatime para no tener diferencias dependiendo de los fps de cada
         * dispositivo
         */
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (isLeft()) {
            x -= SPEED * Gdx.graphics.getDeltaTime();
            //Definimos el desplazamiento maximo para evitar sobrepasar el borde izquierdo
            if (x < 0) {
                x = 0;
            }
            //Actualizamos la animacion justo al pulsar a la izquierda (Mejora) Y nos permite al pulsar der + izq dejar
            //la nave en un estado concreto al gusto del jugador :)
            if (isJustLeft()&& !isRight() && roll > 0) {
                rollTimer = 0;
                roll--;
            }


            //Actualizamos la animacion al girar a la izquierda
            rollTimer -= Gdx.graphics.getDeltaTime();       //Cada frame se va a sustraer el deltaTime
            if (Math.abs(rollTimer) > TIME_SWITCH_ROLL && roll > 0) {
                rollTimer = 0;
                roll--;

            }
        } else {  //Para que la nave al dejar de moverse, vuelva a su estado quieto
            if (roll < 2) {
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > TIME_SWITCH_ROLL && roll < 4) {
                    rollTimer = 0;
                    roll++;
                }
            }
        }

        if (isRight()) {
            x += SPEED * Gdx.graphics.getDeltaTime();
            //Definimos el desplazamiento maximo para evitar sobrepasar el borde izquierdo
            if (x + SHIP_WIDTH > SpaceGame.WIDTH) {
                x = SpaceGame.WIDTH - SHIP_WIDTH;
            }

            //Actualizamos el rollo justo al pulsar a la derecha (Mejora)
            if (isJustRight() && !isLeft() && roll <= 4) {
                rollTimer = 0;
                roll++;
            }

            //Actualizamos el rollo al girar a la derecha
            rollTimer += Gdx.graphics.getDeltaTime();       //Cada frame se va a aniadir el deltaTime
            if (Math.abs(rollTimer) > TIME_SWITCH_ROLL && roll < 4) { //Para que se que dentro del rango de animaciones
                rollTimer = 0;
                roll++;
                if (roll > 4)
                    roll = 4;
            }
        } else {
            if (roll > 2) {
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > TIME_SWITCH_ROLL && roll > 0) {
                    rollTimer = 0;
                    roll--;
                }
            }
        }

        /**
         * Codigo relacionado con los disparos
         * Se disparan dos balas a la vez
         */
        shootTimer += delta;
        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        if ((isRight() || isLeft()) && shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer = 0;
            int offset = 4; //Diferencia en px entre el extremo de la nave y el canion
            /**
             * Las condiciones son para que los disparos al girar
             * la nave no salgan de la nada
             */
            if (roll == 1 || roll == 3) { //Inclinacion parcial de la nave
                offset = 8;
            }
            if (roll == 0 || roll == 4) { //Inclinacion  completa de la nave
                offset = 16;
            }


            bullets.add(new Bullet(x + offset));
            bullets.add(new Bullet(x + SHIP_WIDTH - offset));
        }

        //Despues de que el jugador se mueve...actualizamos el collision react
        playerReact.move(x, y);

        //Actualizamos las balas
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.remove)
                bulletsToRemove.add(bullet);
        }


        /**
         * Codigo relacionado con los asteroides
         * 1)Para que aparezxca el asteroide
         */
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer <= 0) {
            asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            asteroids.add(new Asteroid(random.nextInt(SpaceGame.WIDTH - Asteroid.WIDTH)));
        }
        /**
         * 2)Para actuazar el asteroide
         */
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
        for (Asteroid asteroid : asteroids) {
            asteroid.update(delta);
            if (asteroid.remove) {
                asteroidsToRemove.add(asteroid);
            }
        }
        /**
         * 3)Para actualizar las explosiones
         */
        ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
        for (Explosion explosion : explosions) {
            explosion.update(delta);
            if (explosion.remove) {
                explosionsToRemove.add(explosion);
            }
        }

        explosions.removeAll(explosionsToRemove);


        /**
         * Despues de las actualizaciones, comprobamos las colisiones
         */
        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.getCollisinReact().collidesWith(asteroid.getCollisinReact())) { //Ha colisionado
                    bulletsToRemove.add(bullet);
                    asteroidsToRemove.add(asteroid);
                    explosions.add(new Explosion(asteroid.getX(), asteroid.getY()));
                    score += 100;   //Cada vez que acertamos un asteroid, la puntuacion sube 100 ptos
                }
            }
        }
        /**
         * Codigo para detectar la colision entre la nave y el asteroid
         * quitamos un 10% de la vida al colisionar
         */
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getCollisinReact().collidesWith(playerReact)) {
                Gdx.app.log("colision","con asteroide");
                asteroidsToRemove.add(asteroid);
                health -= 0.1;  //Quitamos un 10% de la vida al colisionar con el asteroide

                //Si la vida se agota, vamos a la pantalla del Game Over
                if(health <= 0){
                    this.dispose();
                    game.setScreen(new GameOverScreen(game, score));
                    return;
                }

            }
        }

        asteroids.removeAll(asteroidsToRemove);
        bullets.removeAll(bulletsToRemove);

        stateTime += delta;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        //Renderizamos el fondo de pantalla gracias a nuestra clase ScollingBackground
        game.scrollingBackground.updateAndRender(delta, game.batch);

        /**
         * Centramos el score gracias al GlyphLayout
         * Recurso online : https://www.dafont.com/press-start.font
         */
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
        scoreFont.draw(game.batch, scoreLayout, SpaceGame.WIDTH / 2 - scoreLayout.width / 2, SpaceGame.HEIGHT - scoreLayout.height - 10);

        //El orden del renderizado es importante
        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }

        for (Asteroid asteroid : asteroids) {
            asteroid.render(game.batch);
        }

        for (Explosion explosion : explosions) {
            explosion.render(game.batch);
        }

        //Dibujando la barra de vida
        if (health > 0.6f)
            game.batch.setColor(Color.GREEN);
        else if (health > 0.2f)
            game.batch.setColor(Color.ORANGE);
        else
            game.batch.setColor(Color.RED);

        game.batch.draw(blank, 0, 0, SpaceGame.WIDTH * health, 5);
        game.batch.setColor(Color.WHITE);
        //*************************
        game.batch.draw((TextureRegion) rolls[2].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);

        //Renderizamos los controles para moviles
        if(SpaceGame.IS_MOBIL){
            //DIBUJAMOS CONTROL IZQUIERDA
            game.batch.setColor(Color.RED);
            game.batch.draw(controls, 0,0,SpaceGame.WIDTH / 2, SpaceGame.HEIGHT,0,0, SpaceGame.WIDTH /2, SpaceGame.HEIGHT, false, false);

            //DIBUJAMOS CONTROL DERECHA  -->El penultimo parametro es para revertir la orienntacion de la imagen y apunte a la derecha
            game.batch.setColor(Color.BLUE);
            game.batch.draw(controls, SpaceGame.WIDTH / 2,0,SpaceGame.WIDTH/2, SpaceGame.HEIGHT,0,0, SpaceGame.WIDTH / 2 , SpaceGame.HEIGHT, true, false);
            game.batch.setColor(Color.WHITE);       //Buena practica volver a setear el color a Blanco
}

        game.batch.end();

                }

/**
 * Metodos para los controles tanto en Desktop como en android e iOS
 */
private boolean isRight(){
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x >= SpaceGame.WIDTH / 2);
        }

private boolean isLeft(){
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isTouched() && game.cam.getInputInGameWorld().x  < SpaceGame.WIDTH / 2);
        }

private boolean isJustRight(){
        return Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.justTouched() && game.cam.getInputInGameWorld().x  >= SpaceGame.WIDTH / 2);
        }

private boolean isJustLeft(){
        return Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || (Gdx.input.justTouched() && game.cam.getInputInGameWorld().x  < SpaceGame.WIDTH / 2);
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
        //img.dispose();

    }
}
