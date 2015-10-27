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
    int touchDragPointer=-1;
    //int touchDownPointer=-1;
    boolean buttonsPressed;
    int whichButPressed;   //0 for left navigation button and 1 for right
    public long lastArrowHit; //time of last arrow hit by user
    public MyGestureListener(Kombat game){
        this.game=game;
        this.arrow=game.gs.arrow;
        gs=game.gs;
        //game.gs.initMglNewArrow();
        game.introScreen.helpPointer=-1;
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
        /*if(game.getScreen()==game.gs)
            touchDownPointer=pointer;*/
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //Gdx.app.log("occurred: ", "touchUp" + buttonsPressed);
        //Gdx.app.log("from mgl: game.gs.arrow.arrow_interval = ",game.gs.playerChar.arrow_interval+"");
        //for instructino screen
        if(game.getScreen()==game.introScreen.instructions && System.currentTimeMillis()-game.introScreen.instructions.showTime>500){
            game.introScreen.goToGameScreen();
            Gdx.app.log("Mygesturelistener.java instructionScreen: ","active");
            return false;

        }

        //for gamescreen

        if(game.getScreen()==gs && lastArrowHit==0 )                   //to avoid an involantary arrow fired when gamescreen is loaded
            lastArrowHit=System.currentTimeMillis()-game.gs.playerChar.arrow_interval;
        if( game.getScreen()==gs
                && game.gs.arrows.size<=game.gs.playerChar.maxArrows
                && (pointer==touchDragPointer
                    || (touchDragPointer==-1
                        && pointer!=game.gs.controls.controlsPointer1
                        && pointer!=game.gs.controls.controlsPointer2
                        && pointer!=game.introScreen.helpPointer)) ) {
           /* this will execute if
            1. player has not already fired arrows more than the limit
            2. either i> pointer is same as tdpointer
                     or ii> there was no touchdrag at all plus this pointer is not the same as acting on buttons- left right and pause
            3. all this is happening on gamescreen
           */

            try {
                game.gs.arrows.get(game.gs.arrows.size - 1).fire();
                lastArrowHit=System.currentTimeMillis();
            }
            catch(IndexOutOfBoundsException e){

            }

            //lastArrowHit=System.currentTimeMillis();

            game.gs.initMglNewArrow();
            touchDragPointer=-1;
            prevScreenX=-1;

        }
        game.introScreen.helpPointer=-1;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Gdx.app.log("occurred: ", "touchdrag");
        if(game.getScreen()==game.gs && touchDragPointer==-1)
            touchDragPointer=pointer;
        if(game.getScreen()==game.gs && touchDragPointer!=game.gs.controls.controlsPointer1 && touchDragPointer!=game.gs.controls.controlsPointer2  && pointer==touchDragPointer ) {
            /*
                this will execute if
                1. touchdragpointer is not the same as that of control pointer 1
                2. tdpointer is not the same as that of control pointer 2
                3. tdpointer and pointer of the method are same
                4. All this is hapening on the gamescreen
            */

            if (prevScreenX == -1)
                prevScreenX = screenX;
            float deltaX = screenX - prevScreenX;
            prevScreenX = screenX;
            //Gdx.app.log("deltaX ", ""+deltaX);
            //if (screenX * game.xScale > game.gs.controls.controlBounds.getWidth() && screenX * game.xScale < game.width - game.gs.controls.controlBounds.getWidth()) {
            try {
                game.gs.arrows.get(game.gs.arrows.size - 1).update(-(0.15f * deltaX * game.xScale));

            } catch (ArrayIndexOutOfBoundsException e) {
            }
            game.gs.arrow.calculatePlayerArrowRange();
            /*game.gs.arrows.get(game.gs.arrows.size - 1).m=(float)Math.tan(Math.toRadians(game.gs.arrows.get(game.gs.arrows.size - 1).rotation+90));
            game.gs.arrows.get(game.gs.arrows.size - 1).c=game.gs.arrows.get(game.gs.arrows.size - 1).yPosition-(game.gs.arrows.get(game.gs.arrows.size - 1).m*game.gs.arrows.get(game.gs.arrows.size - 1).xPosition);
            game.gs.playerChar.arrowInHandRangeX1=(int)(((game.height / game.gs.bot.screenFractionAbove0) -  game.gs.arrows.get(game.gs.arrows.size - 1).c) /  game.gs.arrows.get(game.gs.arrows.size - 1).m);
            game.gs.playerChar.arrowInHandRangeX2=(int)(((game.height / game.gs.bot.screenFractionAbove0) + game.gs.bot.charHeight - game.gs.arrows.get(game.gs.arrows.size - 1).c) / game.gs.arrows.get(game.gs.arrows.size - 1).m);
            if(game.gs.playerChar.arrowInHandRangeX1<game.gs.playerChar.arrowInHandRangeX2){    // so that expectedXAtBotEnd is always greater than expectedXAtBotOrigin
                game.gs.playerChar.arrowInHandRangeX2+=game.gs.playerChar.arrowInHandRangeX1;
                game.gs.playerChar.arrowInHandRangeX1=game.gs.playerChar.arrowInHandRangeX2-game.gs.playerChar.arrowInHandRangeX1;
                game.gs.playerChar.arrowInHandRangeX2=game.gs.playerChar.arrowInHandRangeX2-game.gs.playerChar.arrowInHandRangeX1;
            }*/
            //}
        }
        else
            touchDragPointer=-1;
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