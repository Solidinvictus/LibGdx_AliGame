package com.solidinvictus.earthdefender.tools;

/**
 * Created by Ali on 11/03/2018.
 */

public class CollisionReact {

    float x, y;
    int width, height;

    public CollisionReact(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Metodo que nos devulene true si se detecta las condiciones de collision
     *
     * @param react
     * @return
     */
    public boolean collidesWith(CollisionReact react) {
        return x < react.x + react.width && y < react.y + react.height && x + width > react.x && y + height > react.y;
    }


}
