package com.zeher.kombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.zeher.kombat.Screens.GameScreen;

import java.util.Arrays;


/**
 * Created by code on 4/2/2015.
 * Class representing the characters on our side in game
 */
public class Bot {
    GameScreen gs;
    Texture character;
    SpriteBatch batch;
    public int walkSpeed;
    float xPosition;
    public Array<Rectangle> xRangesToAvoid;
    float screenFractionCharWidth,screenFractionCharHeight,screenFractionAbove0;
    public Array<Rectangle> safeRanges;
    public Array<Integer> safeMidPoints;
    public Thread work;
    public int lives;
    //public int arrow_interval_level;
    public int arrow_interval;    //starts from zero
    final float botPlayerWalkspeedRatio=4;
    public int arrowSpeed;
    public Bot(GameScreen gs){
        this.gs=gs;
        lives=5;
        this.batch=gs.batch;
        character = new Texture("player.png");
        xPosition=gs.game.width/2;
       // walkSpeed=5;
        screenFractionAbove0=1.28f;
        screenFractionCharWidth=6;
        screenFractionCharHeight=5;
        xRangesToAvoid=new Array();
        startWorking();
    }
    public void render(){
        //Gdx.app.log("alive :", work.isAlive() + "");
        batch.setProjectionMatrix(gs.game.camera.combined);
        batch.draw(character, xPosition, gs.game.height / screenFractionAbove0, gs.game.width / screenFractionCharWidth, gs.game.height / screenFractionCharHeight,0,0,88,178,true,false);
    }
    public void startWorking() {
        work=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long lastArrowShot=System.currentTimeMillis();
                    while(true) {
                        //Gdx.app.log("thread to chal:","raha h yar");
                        if (isSafe()) {
                            //.app.log("xRanges to avoid ",xRangesToAvoid+"");

                            if (gs.botArrows.size > 0 ) {

                                //Gdx.app.log("time :",System.currentTimeMillis()-lastArrowShot+"");
                                if(System.currentTimeMillis()-lastArrowShot>arrow_interval) {
                                    Gdx.app.log("arrow interval",arrow_interval+"");
                                    aim();
                                    gs.botArrows.get(gs.botArrows.size - 1).fire();
                                    gs.initMglNewBotArrow();
                                    lastArrowShot=System.currentTimeMillis();
                                }


                            }
                        }
                        else{
                            dodge();
                        }
                        Thread.sleep(100);

                    }
                }
                catch (InterruptedException e){
                    //do nothing
                }
            }
        });
        work.start();


    }
    public boolean isSafe(){
        while(xRangesToAvoid.size>0)
            xRangesToAvoid.removeIndex(0);// clear everytime for correct results
        int i=0;
        boolean isSafe=true;
        if(gs.arrows.size>0) {
            Arrow arrowtoDodge = gs.arrows.get(i);
            while (arrowtoDodge.yPosition > gs.game.height/4) {
                float expectedXAtBotOrigin = ((gs.game.height / screenFractionAbove0) - arrowtoDodge.c) / arrowtoDodge.m;
                float expectedXAtBotEnd = ((gs.game.height / screenFractionAbove0) + character.getHeight() - arrowtoDodge.c) / arrowtoDodge.m;
                if(expectedXAtBotEnd<expectedXAtBotOrigin){
                    expectedXAtBotEnd+=expectedXAtBotOrigin;
                    expectedXAtBotOrigin=expectedXAtBotEnd-expectedXAtBotOrigin;
                    expectedXAtBotEnd=expectedXAtBotEnd-expectedXAtBotOrigin;
                }
                //Gdx.app.log("x1 and x2",expectedXAtBotOrigin+" "+expectedXAtBotEnd+ " +- "+character.getWidth());
                //Gdx.app.log("xPosition: ",xPosition+" ");
                Rectangle rect=new Rectangle();
                rect.x= (int) (expectedXAtBotOrigin-character.getWidth());
                //next is to avoid bot from going out of arena :D ......loser
                if(gs.game.width-gs.playerChar.character.getWidth()<expectedXAtBotEnd)
                    rect.y=gs.game.width-gs.playerChar.character.getWidth();
                else
                    rect.y=(int)expectedXAtBotEnd;                       // x and y represent the range from which xposition should be out
                xRangesToAvoid.add(rect);
                if ((xPosition<expectedXAtBotOrigin && xPosition>expectedXAtBotEnd) ||((xPosition+character.getWidth())>expectedXAtBotOrigin && (xPosition+character.getWidth())<expectedXAtBotEnd) || (expectedXAtBotEnd>xPosition && expectedXAtBotEnd<(xPosition+character.getHeight()))) {
                    isSafe=false;
//                    Gdx.app.log("need to dodge:", "yes");
                }
                else{
//                    Gdx.app.log("need to dodge:", "no");
                }
                i++;
                if(gs.arrows.size>i)
                    arrowtoDodge= gs.arrows.get(i);
            }
        }

        return isSafe;
    }

    public void dodge(){ //dodges arrows
        Gdx.app.log("dodge :","called");
        boolean spaceLeft=true;
        int i=0;
        safeRanges=new Array();
        Rectangle one=new Rectangle();
        one.x=0;
        one.y=gs.game.width-gs.playerChar.character.getWidth();
        Gdx.app.log("max :",one.y+"");
        safeRanges.add(one);
        while(spaceLeft && i<xRangesToAvoid.size){
            //Gdx.app.log("got in line :","113");
            Rectangle rect=xRangesToAvoid.get(i);
            i++;
            for(int j=0;j<safeRanges.size;j++) {
                Rectangle cur=safeRanges.get(j);
                Rectangle addThis=new Rectangle();
                if (rect.x >= cur.x && rect.y<= cur.y){

                    addThis.x=cur.x;
                    addThis.y=rect.x;
                    if(addThis.y-addThis.x>=character.getWidth())
                        safeRanges.add(addThis);
                    addThis=new Rectangle();
                    addThis.x=rect.y;
                    addThis.y=cur.y;
                    if(addThis.y-addThis.x>=character.getWidth())
                        safeRanges.add(addThis);
                    safeRanges.removeIndex(j);
                    j--;
                }
                else if(rect.x<cur.x && rect.y<cur.y){
                    addThis.x=rect.y;
                    addThis.y=cur.y;
                    safeRanges.removeIndex(j);
                    j--;
                    if(addThis.y-addThis.x>=character.getWidth())
                        safeRanges.add(addThis);
                }
                else if(rect.x>cur.x && rect.y>cur.y){
                    addThis.x=cur.x;
                    addThis.y=rect.x;
                    safeRanges.removeIndex(j);
                    j--;
                    if(addThis.y-addThis.x>=character.getWidth())
                        safeRanges.add(addThis);
                }
                if(safeRanges.size>0){
                    spaceLeft=true;
                }
                else
                    spaceLeft=false;
                j++;
            }
        }
        //Gdx.app.log("safeRanges : ",safeRanges+"");
        safeMidPoints=convertRangesToMidPoints(safeRanges);
        //Gdx.app.log("safeMidPoints : ",safeMidPoints+"");
        int distance=0;
        if(safeMidPoints.size>0)
            distance=Math.abs((int)(xPosition - safeMidPoints.get(0)));
        int prevDistance=distance;
        int k=1;
        while(distance<=prevDistance && k<safeMidPoints.size){
            prevDistance=distance;
            distance=Math.abs((int)(xPosition - safeMidPoints.get(0)));
            k++;
        }

        try {
            Gdx.app.log("safe spot: ",safeMidPoints.get(k-1)+"");
            moveTo(safeMidPoints.get(k - 1));
        }catch (IndexOutOfBoundsException e){

        }
    }//end of dodge
    public Array<Integer> convertRangesToMidPoints(Array<Rectangle> safeRanges){
        Array<Integer> safeMids=new Array();
        for(int i=0;i<safeRanges.size;i++){
            safeMids.add((int)((safeRanges.get(i).y+safeRanges.get(i).x)/2-character.getWidth()/2));
        }
        safeMids.sort();
        return safeMids;
    }
    public void aim(){
        float x1=xPosition;
        float y1=gs.game.height / screenFractionAbove0;
        float x2=gs.playerChar.xPosition;
        float y2=gs.game.height/gs.playerChar.screenFractionAbove0;
        gs.botArrows.get(gs.botArrows.size-1).rotation=(float)Math.toDegrees(Math.atan((y2 - y1) / (x2-x1)))-90;
        if(gs.botArrows.get(gs.botArrows.size-1).rotation>-100)
            gs.botArrows.get(gs.botArrows.size-1).rotation-=180;
        //Gdx.app.log("rotation :",gs.botArrows.get(gs.botArrows.size-1).rotation+"");
    }
    public void moveTo(int x){
        float update= walkSpeed/botPlayerWalkspeedRatio;
        if(xPosition>x){
            update=-update;
        }
        while(xPosition!=x){
            try {
                Thread.sleep(5);
            }
            catch (InterruptedException e){

            }
            xPosition+=update;
            gs.botArrows.get(gs.botArrows.size-1).xPosition=(int)((gs.bot.xPosition)+(((float)50/88)*(gs.game.width/gs.bot.screenFractionCharWidth)));
        }
    }
    public void dispose(){
        character.dispose();
    }
}
