package com.solidinvictus.earthdefender.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Ali on 11/03/2018.
 */

public class Explosion {

    public static final float FRAME_LENGTH = 0.2f;
    public static final int OFFSET = 8;    //Este parametro lo usaremos para adaptar la colision, ya que el asteroid es 16*16 px
    public static final int SIZE = 32;  //Tamanio en px de la explosion

    private static Animation anim = null;
    public boolean remove = false;
    float x, y;
    float stateTime;

    public Explosion(float x, float y) {
        this.x = x - OFFSET;    //Para centrar la explosion en el centro del asteroid cso hay colision
        this.y = y - OFFSET;    //Para centrar la explosion en el centro del asteroid cso hay colision
        stateTime = 0;

        if (anim == null) {
            /**
             * El Texturergion.split() trabaja con arrays de 2 D, en este caso tenemos un array de 1 dimension,
             * por lo que hay que indicalselo-->[0]
             * para acceder al primera y unica  fila de nuestro rollo de imagenes de la explosion
             */
            anim = new Animation(FRAME_LENGTH, TextureRegion.split(new Texture("explosion.png"), SIZE, SIZE)[0]);
        }
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (anim.isAnimationFinished(stateTime))
            remove = true;
    }

    public void render(SpriteBatch batch) {
        batch.draw((TextureRegion) anim.getKeyFrame(stateTime), x, y);
    }
}





