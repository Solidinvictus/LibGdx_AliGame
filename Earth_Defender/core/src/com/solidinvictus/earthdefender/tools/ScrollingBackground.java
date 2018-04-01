package com.solidinvictus.earthdefender.tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.solidinvictus.earthdefender.SpaceGame;

/**
 * Clase para mostrar una imagen de fondo que de la sensacion de velocidad
 */
public class ScrollingBackground {

    public static final int DEFAULT_SPEED = 80;
    public static final int ACCELERATION = 100;
    public static final int GOAL_REACH_ACCELERATION = 200;

    Texture image;
    float y1, y2;
    int speed; //px / seg
    int goalSpeed;
    float imageScale;   //Para redimensionar la imagen
    boolean speedFixed ;    //Para fijar la acceleracion solo en el juego, no en otras pantallas

    public ScrollingBackground(){
        image = new Texture("stars_background.png");
        y1 = 0;
        y2 = image.getHeight();
        imageScale = SpaceGame.WIDTH / image.getWidth();
        speed = 0;
        goalSpeed = DEFAULT_SPEED;
        speedFixed = true;
    }

    public void updateAndRender(float deltaTime, SpriteBatch batch){
        //Ajuste de velocidad
        if(speed < goalSpeed){
            speed += GOAL_REACH_ACCELERATION * deltaTime;
            if(speed > goalSpeed)
                speed = goalSpeed;
        }else if(speed > goalSpeed){
            speed -= GOAL_REACH_ACCELERATION * deltaTime;
            if(speed < goalSpeed)
                speed = goalSpeed;
        }

        if(!speedFixed){
            speed += ACCELERATION * deltaTime;
        }

        y1 -=speed * deltaTime;
        y2 -=speed * deltaTime;

        //Si la imagen alcanza el pie de la pantalla y no es visible, volvemos a ponerla
        if(y1 + image.getHeight() * imageScale <=0){
            y1 = y2 + image.getHeight() * imageScale;
        }

        if(y2 + image.getHeight() * imageScale <=0){
            y2 = y1 + image.getHeight() * imageScale;
        }

        //Render
        batch.draw(image, 0, y1, SpaceGame.WIDTH,image.getHeight() * imageScale);
        batch.draw(image, 0, y2, SpaceGame.WIDTH,image.getHeight() * imageScale);

    }

    /*public void resize(int width, int height){
        imageScale = width / image.getWidth();
    }*/

    public void setSpeed(int goalSpeed){
        this.goalSpeed = goalSpeed;
    }

    public void setSpeedFixed(boolean speedFixed){
        this.speedFixed = speedFixed;
    }


}
