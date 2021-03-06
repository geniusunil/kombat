package com.zeher.kombat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zeher.kombat.Screens.GameScreen;

/**
 * Created by code on 4/2/2015.
 * Class representing the characters on our side in game
 */
public class PlayerChar {
    GameScreen gs;
    Texture character;
    SpriteBatch batch;
    public int walkSpeed;
    int xPosition;
    float screenFractionCharWidth,screenFractionCharHeight,screenFractionAbove0;
    public int lives;
    public int arrow_interval;
    //public int arrow_interval_level;
    public int arrowSpeed;
    public int maxArrows=2;
    public int charWidth;
    public int charHeight;
    public int arrowInHandRangeX1;
    public int arrowInHandRangeX2=100;
    public PlayerChar(GameScreen gs){
        this.gs=gs;
        //lives=5;
        this.batch=gs.batch;
        character = new Texture("player.png");
        xPosition=gs.game.width/2;
        //walkSpeed=5;
        screenFractionAbove0=16;
        screenFractionCharWidth=6;
        screenFractionCharHeight=5;
        charWidth=(int) (gs.game.width/screenFractionCharWidth);
        charHeight= (int) (gs.game.height/screenFractionCharHeight);

    }
    public void render(){
        batch.setProjectionMatrix(gs.game.camera.combined);
        batch.draw(character,xPosition,gs.game.height/screenFractionAbove0,charWidth,charHeight);
    }
    public void dispose(){
        character.dispose();
    }
}
