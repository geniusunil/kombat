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
    public void isTouched (int x, int y)  {
        /*Gdx.app.log("occurred: ", "touched");
        */
        if((x * game.xScale <= game.gs.controls.controlBounds.getWidth() && y * game.yScale >= game.height - game.gs.controls.controlBounds.getHeight()-game.gs.controls.heightOfButtons )) {
            whichButPressed=0;
            buttonsPressed = true;

        }
        else if(x * game.xScale >= game.width - game.gs.controls.controlBounds.getWidth() && y * game.yScale >= game.height - game.gs.controls.controlBounds.getHeight()-game.gs.controls.heightOfButtons ){
            whichButPressed=1;
            buttonsPressed = true;

        }
        else{
            buttonsPressed=false;
        }
        //Gdx.app.log("bp? ",buttonsPressed+"");
        //Gdx.app.log("touched and checked", x * game.xScale + " " + (game.width-game.gs.controls.controlBounds.getWidth()) + " " + y * game.yScale + " " + (game.height - game.gs.controls.controlBounds.getHeight()));
        if (buttonsPressed && whichButPressed==0 && game.gs.playerChar.xPosition >= 0) {
            game.gs.playerChar.xPosition -= game.gs.playerChar.walkSpeed;
            try {
                game.gs.arrows.get(game.gs.arrows.size - 1).update(0);
            } catch (ArrayIndexOutOfBoundsException e) {

            }

        } else if (buttonsPressed && whichButPressed==1 && game.gs.playerChar.xPosition < game.width - game.width / game.gs.playerChar.screenFractionCharWidth) {
            game.gs.playerChar.xPosition += game.gs.playerChar.walkSpeed;
            try {
                game.gs.arrows.get(game.gs.arrows.size - 1).update(0);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
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
        if(game.gs.arrows.size<=game.gs.playerChar.maxArrows && !buttonsPressed && game.getScreen()==gs) {
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