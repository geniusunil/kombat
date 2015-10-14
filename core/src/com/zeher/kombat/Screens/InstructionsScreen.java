package com.zeher.kombat.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.zeher.kombat.Kombat;

/**
 * Created by zucky on 6/2/2015.
 */
public class InstructionsScreen implements Screen{
    Kombat game;
    long showTime; //time when the screen showed up
    Texture ins=new Texture("instructions.jpg");
    public InstructionsScreen (Kombat game){
        this.game=game;
    }
    @Override
    public void show() {
        showTime=System.currentTimeMillis();

    }

    @Override
    public void render(float delta) {
        //Gdx.app.log("instruction arrows :", game.gs.arrows.size + "");
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(ins, 0, 0);
        //game.font.draw(game.batch,""+game.gs.arrows.size,250,250);
        Gdx.app.log("GameScreen.java arrows by player: ", game.gs.arrows.size + "");
        Gdx.app.log("GameScreen.java arrows by bot: ", game.gs.botArrows.size + "");
        game.batch.end();
        if(Gdx.input.isTouched() && System.currentTimeMillis()-showTime>500){

            game.introScreen.goToGameScreen();

            this.dispose();

        }
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
        ins.dispose();
    }
}
