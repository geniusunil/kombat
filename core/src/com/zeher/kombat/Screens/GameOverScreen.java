package com.zeher.kombat.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zeher.kombat.Kombat;

/**
 * Created by zucky on 5/27/2015.
 */
public class GameOverScreen implements Screen{
    Kombat game;
    SpriteBatch batch;
    int botLives,playerLives;
    long showTime; //time when the screen showed up
    public GameOverScreen(Kombat game){
        this.game=game;
        this.batch=game.batch;
        game.font.setScale(3);
        botLives=game.gs.bot.lives;
        playerLives=game.gs.playerChar.lives;
    }
    public void show(){
        showTime=System.currentTimeMillis();
    }
    public void hide(){

    }
    public void pause(){

    }
    public void resume(){}
    public void resize(int x, int y){

    }
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        game.batch.draw(game.introScreen.introbg,0,0);
        if(game.gs.bot.lives > game.gs.playerChar.lives){
           game.font.drawWrapped(batch, "bot won! " + botLives + "-" + playerLives + ", Close app and run again", 0, 700,720);
        }
        else if(game.gs.bot.lives <= game.gs.playerChar.lives){
            game.font.drawWrapped(batch,"you won! "+playerLives+"-"+botLives+", Close app and run again",0,700,720);
        }
        if(Gdx.input.isTouched() && System.currentTimeMillis()-showTime>500)
            game.create();
        batch.end();
    }
    public void dispose(){

    }
}
