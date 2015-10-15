package com.zeher.kombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zeher.kombat.Screens.GameScreen;

/**
 * Created by zucky on 5/24/2015.
 * arrow shot by bot
 */
public class BotArrow {
    SpriteBatch batch;
    public static Texture arrowImg;
    public static Texture bow;
    float xPosition;
    float yPosition;
    float xOrigin;
    float yOrigin;
    float arrowWidth;
    float arrowHeight;
    float xScale;
    float yScale;
    float rotation;
    float srcX;
    float srcY;
    float srcWidth;
    float srcHeight;
    boolean flipX;
    boolean flipY;
    float arrowWidthFraction=27;
    float dy,dx;
    public boolean hit;
    // following is for the fire()
    Kombat game;
    public Thread fireThread;
    public boolean arrowWithinScreen;
    public BotArrow(Kombat game){
        this.game=game;
        this.batch=game.batch;
        xOrigin=0;
        yOrigin=0;
        xScale=yScale=1;
        srcX=0;
        srcY=0;
        srcWidth=185;
        srcHeight=1762;
        rotation=180;
        float fractionOfCharacterArrowHeight=0.5f;
        flipX=false;
        flipY=false;
        arrowWidth=game.width/arrowWidthFraction;
        arrowHeight= (game.height/game.gs.playerChar.screenFractionCharHeight)*fractionOfCharacterArrowHeight;
        xPosition=(game.gs.bot.xPosition)+(((float)25/88)*(game.gs.game.width/game.gs.bot.screenFractionCharWidth));
        yPosition=game.height/game.gs.bot.screenFractionAbove0+((float)160/180*game.height/game.gs.bot.screenFractionCharHeight);

    }
    public void render(){
        try {
            batch.draw(bow, xPosition - 150/2, yPosition, 75, 0, 150, 80, 1, 1, game.gs.botArrows.get(game.gs.botArrows.size - 1).rotation, 0, 0, 300, 129, false, false);
        }
        catch (IndexOutOfBoundsException e) {
        }
        //batch.draw(bow,100,100);
        for(BotArrow botArrow : game.gs.botArrows) {
            batch.draw(arrowImg, botArrow.xPosition, botArrow.yPosition, botArrow.xOrigin, botArrow.yOrigin, botArrow.arrowWidth, botArrow.arrowHeight, botArrow.xScale, botArrow.yScale, botArrow.rotation, 0, 0, 185,1762,false,false);

        }
    }


    public void fire(){    //when the arrow is fired
        final BotArrow thisArrow= this;
        arrowWithinScreen=true;
        final float m=(float)Math.tan(Math.toRadians(this.rotation+90));
        final float c=this.yPosition-(m*this.xPosition);
        /*Gdx.app.log("curx and cur y and rot: ",curX+" "+curY+" "+curRot);
        Gdx.app.log("m :",""+m);*/
        thisArrow.dy=(float)Math.abs(thisArrow.arrowHeight*Math.cos(Math.toRadians(thisArrow.rotation)));
        thisArrow.dx=dy/m;

        fireThread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    while(true) {
                        while (game.gs.paused){
                            game.gs.pausePoint();
                        }
                        thisArrow.yPosition--;
                        thisArrow.xPosition=(thisArrow.yPosition-c)/m;
                        if(game.getScreen()==game.gs.gos){
                            thisArrow.dispose();
                        }
                        fireThread.sleep(game.gs.bot.arrowSpeed);

                        if(arrowHitPlayer(thisArrow)){
                            try {
                                boolean notDone=true;
                                int i=0;
                                while(notDone) {
                                    if (game.gs.botArrows.get(i).hit) {
                                        game.gs.botArrows.removeIndex(i);
                                        notDone = false;
                                    } else
                                        i++;
                                }
                            }
                            catch (IndexOutOfBoundsException e) {
                            }

                            game.gs.playerChar.lives--;
                            thisArrow.dispose();
                            //Gdx.app.log("arrow remove : ","yes");
                            break;
                        }
                        else if(thisArrow.xPosition<0 || thisArrow.xPosition>720 || thisArrow.yPosition>1280 || thisArrow.yPosition<0){
                            thisArrow.arrowWithinScreen=false;
                            try {
                                boolean notDone=true;
                                int i=0;
                                while(notDone){
                                    if(!game.gs.botArrows.get(i).arrowWithinScreen){
                                        game.gs.botArrows.removeIndex(i);
                                        notDone=false;
                                    }
                                    else
                                        i++;
                                }
                            }
                            catch (IndexOutOfBoundsException e) {
                            }

                            thisArrow.dispose();
                            //Gdx.app.log("arrow remove : ","yes");
                            break;
                        }
                    }



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        fireThread.start();
    }
    public boolean arrowHitPlayer(BotArrow botArrow){
        hit = false;
        Gdx.app.log("BotArrow.java BotArrow yPosition :", botArrow.yPosition + " bot yPosition :" + game.gs.game.height / game.gs.bot.screenFractionAbove0 + " arrow xPosition " + botArrow.xPosition + " bot xposition" + game.gs.bot.xPosition + " dy is : "+botArrow.dy+ " dx is: "+ botArrow.dx);
        if((botArrow.yPosition-botArrow.dy)< (game.height / game.gs.playerChar.screenFractionAbove0+ game.gs.playerChar.charHeight) && (botArrow.yPosition-botArrow.dy)> (game.height / game.gs.playerChar.screenFractionAbove0) && botArrow.xPosition-botArrow.dx >= game.gs.playerChar.xPosition && botArrow.xPosition-botArrow.dx <= (game.gs.playerChar.xPosition + game.gs.playerChar.charWidth)) {
            hit = true;
        }
        return hit;
    }
    public void dispose(){
        try{
            fireThread.join();
        }catch (InterruptedException e){
            //do nothing
        }


        //arrowImg.dispose();
    }
}
