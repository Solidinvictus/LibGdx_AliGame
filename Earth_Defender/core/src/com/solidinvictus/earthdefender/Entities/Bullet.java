package com.solidinvictus.earthdefender.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.solidinvictus.earthdefender.SpaceGame;
import com.solidinvictus.earthdefender.tools.CollisionReact;

/**
 * Created by Ali on 09/03/2018.
 */

public class Bullet {

    public static final int SPEED = 500;
    public static final int DEFAULT_Y = 40;
    public static final int WIDTH = 3;      //Anchura bala
    public static final int HEIGHT = 12;    //Longitud bala
    private static Texture texture;
    public boolean remove = false;
    float x, y;    //Posicion de la bala
    private CollisionReact react;

    /**
     * Constructor
     *
     * @param x
     */
    public Bullet(float x) {
        this.x = x;
        this.y = DEFAULT_Y;
        this.react = new CollisionReact(x, y, WIDTH, HEIGHT);

        if (texture == null)
            texture = new Texture("bullet.png");
    }

    public void update(float deltaTime) {
        y += SPEED * deltaTime;     //Para que la bala viaje a 500px por seg
        if (y > SpaceGame.HEIGHT) {
            remove = true;
        }

        react.move(x, y);

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
