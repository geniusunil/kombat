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
    boolean youWon;
    public GameOverScreen(Kombat game){
        this.game=game;
        this.batch=game.batch;
        game.font.setScale(3);

    }
    public void show(){
        showTime=System.currentTimeMillis();
        botLives=game.gs.bot.lives;
        playerLives=game.gs.playerChar.lives;
        if(botLives<playerLives) {
            youWon = true;
            Gdx.app.log("GameOverScreen.java curLevel", " " + game.introScreen.lc.curLevel);

            game.prefs.putInteger("coins", game.prefs.getInteger("coins") + (game.introScreen.lc.curLevel) * 10);
//            game.prefs.flush();
            game.introScreen.lc.curLevel++;
            if(game.prefs.getInteger("levels_unlocked")<game.introScreen.lc.curLevel){
                game.prefs.putInteger("levels_unlocked",game.introScreen.lc.curLevel);
                game.prefs.flush();
            }
            Gdx.app.log("GameOverScreen.java coins", " " + game.prefs.getInteger("coins"));
            Gdx.app.log("GameOverScreen.java levels unlocked: ", " " + game.prefs.getInteger("levels_unlocked"));
        }
        game.gs.bot.dispose();
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
        if(!youWon){

           game.font.drawWrapped(batch, "bot won! " + botLives + "-" + playerLives + ", Touch run again, Number of Arrows fired :" + game.gs.bot.noOfArrowsFired , 0, 700,720);
        }
        else if(youWon){

            game.font.drawWrapped(batch,"you won! "+playerLives+"-"+botLives+ ", Touch run again, Number of Arrows fired :" + game.gs.bot.noOfArrowsFired,0,700,720);
        }
        if(Gdx.input.isTouched() && System.currentTimeMillis()-showTime>1000)
            game.create();
        batch.end();
    }
    public void dispose(){

    }
}
