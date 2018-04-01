package com.solidinvictus.earthdefender.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.solidinvictus.earthdefender.SpaceGame;
import com.solidinvictus.earthdefender.tools.CollisionReact;

/**
 * Created by Ali on 09/03/2018.
 */

public class Asteroid {
    public static final int SPEED = 250;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    private static Texture texture;
    public boolean remove = false;
    private float x, y;    //Posicion de la bala
    private CollisionReact react;

    /**
     * Constructor
     *
     * @param x
     */
    public Asteroid(float x) {
        this.x = x;
        this.y = SpaceGame.HEIGHT;
        this.react = new CollisionReact(x, y, WIDTH, HEIGHT);

        if (texture == null)
            texture = new Texture("asteroid.png");

    }

    public void update(float deltaTime) {
        y -= SPEED * deltaTime;     //Para que la bala viaje a 500px por seg
        if (y < -HEIGHT) {
            remove = true;
        }
        react.move(x, y);

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     * Metodo para dibujar la bala
     *
     * @param batch
     */
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public CollisionReact getCollisinReact() {
        return react;
    }
}
