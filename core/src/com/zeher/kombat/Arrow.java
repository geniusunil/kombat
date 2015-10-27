package com.zeher.kombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zeher.kombat.Screens.GameScreen;

/**
 * Created by code on 4/7/2015.
 * Class representing the home in game
 */
public class Arrow {
    Kombat game;
    SpriteBatch batch;
    /*Texture arrowImg;
    Texture bow;*/
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
    float fractionOfCharacterArrowHeight=0.5f;
    public Thread fireThread;
    public boolean hit;
    // following is for the fire()

    float m;
    float c;

    public boolean arrowWithinScreen;
    public float marginRight;
    public Arrow(Kombat game){
        this.game=game;
        this.batch = game.batch;
        /*bow=new Texture("bow.png");
        arrowImg=new Texture("arrow.png");*/
        xOrigin=0;
        yOrigin=0;
        xScale=yScale=1;
        srcX=0;
        srcY=0;
        srcWidth=185;
        srcHeight=1762;


        flipX=false;
        flipY=false;
        arrowWidth=game.width/arrowWidthFraction;
        arrowHeight= (game.height/game.gs.playerChar.screenFractionCharHeight)*fractionOfCharacterArrowHeight;
        xPosition=(game.gs.playerChar.xPosition)+(((float)50/88)*(game.gs.game.width/game.gs.playerChar.screenFractionCharWidth));
        yPosition=game.gs.game.height/game.gs.playerChar.screenFractionAbove0+((float)125/180*game.gs.game.height/game.gs.playerChar.screenFractionCharHeight);
        rotation=0.0f;
        marginRight=(game.gs.bot.charWidth)/3;
    }
    public void render() {

        for (int i = 0; i < game.gs.arrows.size; i++) {
            Arrow arrow=game.gs.arrows.get(i);
            try {     //draw the bow
                //Gdx.app.log("bow: ","drawn");
                batch.draw(game.gs.bow, xPosition - 150 / 2, yPosition, 75, 0, 150, 80, 1, 1, game.gs.arrows.get(game.gs.arrows.size - 1).rotation, 0, 0, 300, 129, false, false);
            }catch (ArrayIndexOutOfBoundsException e) {
            }
            //Gdx.app.log("game.gs.arrow.arrow_interval",game.gs.arrow.arrow_interval+"");
            if(i==game.gs.arrows.size-1 && game.gs.arrows.size<=game.gs.playerChar.maxArrows) {
                batch.draw(game.gs.arrowImg, arrow.xPosition, arrow.yPosition, arrow.xOrigin, arrow.yOrigin, arrow.arrowWidth, arrow.arrowHeight, arrow.xScale, arrow.yScale, arrow.rotation, 0, 0, 185, 1762, false, false);
                break;
            }
            else if(i<game.gs.arrows.size-1)
                batch.draw(game.gs.arrowImg, arrow.xPosition, arrow.yPosition, arrow.xOrigin, arrow.yOrigin, arrow.arrowWidth, arrow.arrowHeight, arrow.xScale, arrow.yScale, arrow.rotation, 0, 0, 185, 1762, false, false);
            }

    }

    public void update(float angle){
        xPosition=(game.gs.playerChar.xPosition)+(((float)50/88)*(game.width/game.gs.playerChar.screenFractionCharWidth));
        yPosition=game.height/game.gs.playerChar.screenFractionAbove0+((float)125/180*game.height/game.gs.playerChar.screenFractionCharHeight);

        rotation+=angle;
        if(rotation>60)
            rotation=60;
        else if(rotation<-60){
            rotation=-60;
        }
        calculatePlayerArrowRange();
    }
    public void fire(){    //when the arrow is fired
        final Arrow thisArrow= this;
        arrowWithinScreen=true;
        m=(float)Math.tan(Math.toRadians(this.rotation+90));
        c=this.yPosition-(m*this.xPosition);
        /*Gdx.app.log("curx and cur y and rot: ",curX+" "+curY+" "+curRot);
        Gdx.app.log("m :",""+m);*/
        thisArrow.dy=(float)Math.abs(thisArrow.arrowHeight * Math.cos(Math.toRadians(thisArrow.rotation)));
        thisArrow.dx=dy/m;
        fireThread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {

                    while(true) {
                        while (game.gs.paused){
                            game.gs.pausePoint();
                        }
                        thisArrow.yPosition+=3;
                        thisArrow.xPosition=(thisArrow.yPosition-c)/m;
                        if(game.getScreen()==game.gs.gos){
                            thisArrow.dispose();
                        }
                        Thread.sleep(game.gs.playerChar.arrowSpeed);
                        if(arrowHitBot(thisArrow)){
                            try {
                                boolean notDone=true;
                                int i=0;
                                while(notDone){
                                    if(game.gs.arrows.get(i).hit){
                                        game.gs.arrows.removeIndex(i);
                                        notDone=false;
                                    }
                                    else
                                        i++;
                                }
                            }catch (IndexOutOfBoundsException e){

                            }
                            game.gs.bot.lives--;
                            Gdx.app.log("Arrow.java dodge bot.xposition: ",""+game.gs.bot.xPosition);

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
                                    if(!game.gs.arrows.get(i).arrowWithinScreen){
                                        game.gs.arrows.removeIndex(i);
                                        notDone=false;
                                    }
                                    else
                                        i++;
                                }
                            }catch(IndexOutOfBoundsException e){
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
    public boolean arrowHitBot(Arrow arrow){
        hit = false;
        //Gdx.app.log("height reduced : and dx",arrow.dy+" "+arrow.dx);
        if(arrow.yPosition+dy> game.height / game.gs.bot.screenFractionAbove0
                && (arrow.yPosition+dy)<(game.height / game.gs.bot.screenFractionAbove0 + game.gs.bot.character.getHeight())
                && arrow.xPosition +dx >= game.gs.bot.xPosition
                && arrow.xPosition+dx <= (game.gs.bot.xPosition + game.gs.bot.charWidth - marginRight)) {
            hit = true;
        }
        //Gdx.app.log("arrow yPosition :",arrow.yPosition+" bot yPosition :"+gs.game.height / gs.bot.screenFractionAbove0+" arrow xPosition "+arrow.xPosition+" bot xposition"+gs.bot.xPosition);
        return hit;
    }
    public void calculatePlayerArrowRange(){
        game.gs.arrows.get(game.gs.arrows.size - 1).m=(float)Math.tan(Math.toRadians(game.gs.arrows.get(game.gs.arrows.size - 1).rotation+90));
        game.gs.arrows.get(game.gs.arrows.size - 1).c=game.gs.arrows.get(game.gs.arrows.size - 1).yPosition-(game.gs.arrows.get(game.gs.arrows.size - 1).m*game.gs.arrows.get(game.gs.arrows.size - 1).xPosition);
        game.gs.playerChar.arrowInHandRangeX1=(int)(((game.height / game.gs.bot.screenFractionAbove0) -  game.gs.arrows.get(game.gs.arrows.size - 1).c) /  game.gs.arrows.get(game.gs.arrows.size - 1).m);
        game.gs.playerChar.arrowInHandRangeX2=(int)(((game.height / game.gs.bot.screenFractionAbove0) + game.gs.bot.charHeight - game.gs.arrows.get(game.gs.arrows.size - 1).c) / game.gs.arrows.get(game.gs.arrows.size - 1).m);
            /*if(game.gs.playerChar.arrowInHandRangeX1<game.gs.playerChar.arrowInHandRangeX2){    // so that expectedXAtBotEnd is always greater than expectedXAtBotOrigin
                game.gs.playerChar.arrowInHandRangeX2+=game.gs.playerChar.arrowInHandRangeX1;
                game.gs.playerChar.arrowInHandRangeX1=game.gs.playerChar.arrowInHandRangeX2-game.gs.playerChar.arrowInHandRangeX1;
                game.gs.playerChar.arrowInHandRangeX2=game.gs.playerChar.arrowInHandRangeX2-game.gs.playerChar.arrowInHandRangeX1;
            }*/
    }
    public void dispose(){
        try{
            fireThread.join();
        }catch (InterruptedException e){
            //do nothing
        }
        //arrowImg.dispose();   //causing black texture, fix this later
    }
}
