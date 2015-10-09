package com.zeher.kombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.zeher.kombat.Screens.GameScreen;
import com.zeher.kombat.Screens.LevelChooser;

/**
 * Created by code on 3/27/2015.
 * Class representing the home in game
 */
public class MyGestureListener implements InputProcessor {
    Kombat game;
    Arrow arrow;
    GameScreen gs;
    int prevScreenX=-1;
    boolean buttonsPressed;
    int whichButPressed;   //0 for left navigation button and 1 for right
    public long lastArrowHit; //time of last arrow hit by user
    public MyGestureListener(Kombat game){
        this.game=game;
        this.arrow=game.gs.arrow;
        gs=game.gs;
        //game.gs.initMglNewArrow();
    }

    @Override
    public boolean keyDown(int keycode) {

        Gdx.app.log("occurred: ", "keyDown");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        Gdx.app.log("occurred: ", "keyDown");
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        Gdx.app.log("occurred: ", "keyTyped");
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
       // Gdx.app.log("occurred: ", "touchdown");

        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //Gdx.app.log("occurred: ", "touchUp" + buttonsPressed);
        //Gdx.app.log("from mgl: game.gs.arrow.arrow_interval = ",game.gs.playerChar.arrow_interval+"");
        if(lastArrowHit==0 && game.getScreen()==gs)                   //to avoid an involantary arrow fired when gamescreen is loaded
            lastArrowHit=System.currentTimeMillis()-game.gs.playerChar.arrow_interval;
        if(game.gs.arrows.size<=game.gs.playerChar.maxArrows && !(game.gs.controls.leftB.touchFlag || game.gs.controls.rightB.touchFlag) && game.getScreen()==gs) {
            try {
                game.gs.arrows.get(game.gs.arrows.size - 1).fire();
                lastArrowHit=System.currentTimeMillis();
            }
            catch(IndexOutOfBoundsException e){

            }

            //lastArrowHit=System.currentTimeMillis();

            game.gs.initMglNewArrow();
        }
        prevScreenX=-1;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Gdx.app.log("occurred: ", "touchdrag");
        if(prevScreenX==-1)
            prevScreenX=screenX;
        float deltaX=screenX-prevScreenX;
        prevScreenX=screenX;
        //Gdx.app.log("deltaX ", ""+deltaX);
        //if (screenX * game.xScale > game.gs.controls.controlBounds.getWidth() && screenX * game.xScale < game.width - game.gs.controls.controlBounds.getWidth()) {
            try {
                game.gs.arrows.get(game.gs.arrows.size - 1).update(-(0.25f * deltaX * game.xScale));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        //}

        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        Gdx.app.log("occurred: ", "mouseMoved");
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Gdx.app.log("occurred: ", "scrolled");
        return false;
    }

}