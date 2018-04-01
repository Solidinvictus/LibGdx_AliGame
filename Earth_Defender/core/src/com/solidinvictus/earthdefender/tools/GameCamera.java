package com.solidinvictus.earthdefender.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Ali on 11/03/2018.
 */

public class GameCamera {

    //Para nuestra adaptacion a dispositivos Android
    private OrthographicCamera cam;
    private StretchViewport viewPort;

    public GameCamera(int width, int height){
        cam = new OrthographicCamera();
        viewPort = new StretchViewport(width, height, cam);
        viewPort.apply();
        cam.position.set(width/2, height/2, 0);
        cam.update();
    }

    public Matrix4 combined(){
        return cam.combined;
    }

    public void update(int width, int height){
        viewPort.update(width, height);
    }

    /**
     * Para convertir las coordenadas registradas en la pantall en coordenadas globales del mundo
     * @return
     */
    public Vector2 getInputInGameWorld(){
        Vector3 inputScreen = new Vector3(Gdx.input.getX(),Gdx.graphics.getHeight()-Gdx.input.getY(),0);
        Vector3 unprojected = cam.unproject(inputScreen);
        return new Vector2(unprojected.x, unprojected.y);
    }

}
