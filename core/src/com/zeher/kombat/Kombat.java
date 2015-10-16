package com.zeher.kombat;
/*
    To DO
    1. game crashes on high levels, 10, 100

    2. levels testing
    3.
    4. graphics - pause screen, social share score
    5. Sound
    6.
    7.
    next 8. flickering arrows after using help button
 */
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.zeher.kombat.Screens.GameScreen;
import com.zeher.kombat.Screens.IntroScreen;

import javax.swing.text.View;

public class Kombat extends Game {
    public GameScreen gs;
    public MyGestureListener mgl;
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public int width=720;
    public int height=1280;
    public float xScale;
    public float yScale;
    public BitmapFont font;
    public IntroScreen introScreen;
    @Override
    public void create () {
        xScale=(float)width/Gdx.graphics.getWidth();
        yScale=(float)height/Gdx.graphics.getHeight();
        //Gdx.app.log("xScale and yScale",xScale+" "+yScale);
        //Gdx.app.log("width",Gdx.graphics.getWidth()+"");
        batch=new SpriteBatch();
        camera=new OrthographicCamera(width,height);
        camera.position.set(width / 2, height / 2, 0);
        camera.update();
        font=new BitmapFont();
        font.setScale(4);
        batch.setProjectionMatrix(camera.combined);
        gs=new GameScreen(this);
        mgl=new MyGestureListener(this);

        introScreen=new IntroScreen(this);
        setScreen(introScreen);


    }
    public void dispose(){
        batch.dispose();
        gs.dispose();
        font.dispose();
    }
}
