package com.zeher.kombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;



/**
 * Created by code on 4/2/2015.
 * Class representing the characters on our side in game
 */
public class Bot {
    Kombat game;
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
    public int accuracy; //lesser the more accurate
    public int noOfArrowsFired;
    public boolean flag;
    public int dodgeLevel=1;
    public int maxArrows;
    public Bot(Kombat game){
        this.game=game;
        //lives=5;
        this.batch=game.batch;
        character = new Texture("player.png");
        xPosition=game.width/2;
       // walkSpeed=5;
        screenFractionAbove0=1.28f;
        screenFractionCharWidth=6;
        screenFractionCharHeight=5;
        xRangesToAvoid=new Array();
        startWorking();
    }
    public void render(){
        //Gdx.app.log("alive :", work.isAlive() + "");
        batch.setProjectionMatrix(game.camera.combined);
        batch.draw(character, xPosition, game.height / screenFractionAbove0, game.width / screenFractionCharWidth, game.height / screenFractionCharHeight, 0, 0, 88, 178, true, false);
    }
    public void startWorking() {
        work=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long lastArrowShot=System.currentTimeMillis();
                    flag=true;
                    while(flag) {
                        //Gdx.app.log("thread to chal:","raha h yar");
                        if (!isSafe()) {
                            dodge();
                        }
                            //.app.log("xRanges to avoid ",xRangesToAvoid+"");

                        if (game.gs.botArrows.size > 0 && game.gs.botArrows.size<=game.gs.bot.maxArrows) {

                                //Gdx.app.log("time :",System.currentTimeMillis()-lastArrowShot+"");
                                    Gdx.app.log("arrow interval",arrow_interval+"");
                                    aim();
                                    game.gs.botArrows.get(game.gs.botArrows.size - 1).fire();
                                    game.gs.bot.noOfArrowsFired++;
                                    Gdx.app.log("noofarrowsfired: ",game.gs.bot.noOfArrowsFired+"");
                                    game.gs.initMglNewBotArrow();
                                    lastArrowShot=System.currentTimeMillis();



                        }

                        Thread.sleep(500);

                    }
                    work.join();
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
        if(game.gs.arrows.size>0) {
            Arrow arrowtoDodge = game.gs.arrows.get(i);
            while (arrowtoDodge.yPosition > game.height/4) {
                float expectedXAtBotOrigin = ((game.height / screenFractionAbove0) - arrowtoDodge.c) / arrowtoDodge.m;
                float expectedXAtBotEnd = ((game.height / screenFractionAbove0) + character.getHeight() - arrowtoDodge.c) / arrowtoDodge.m;
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
                if(game.width-game.gs.playerChar.character.getWidth()<expectedXAtBotEnd)
                    rect.y=game.width-game.gs.playerChar.character.getWidth();
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
                if(game.gs.arrows.size>i)
                    arrowtoDodge= game.gs.arrows.get(i);
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
        one.y=game.width-game.gs.playerChar.character.getWidth();
        Gdx.app.log("max :",one.y+"");
        safeRanges.add(one);
        while(spaceLeft && i<xRangesToAvoid.size){
            //Gdx.app.log("got in line :","113");
            Rectangle rect=  xRangesToAvoid.get(i);
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
                spaceLeft = safeRanges.size > 0;
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
        float y1=game.height / screenFractionAbove0;
        float error=MathUtils.random(accuracy)*(game.width / (screenFractionCharWidth*5));
        float x2=0;
        if(MathUtils.random(2)==1)
            x2=game.gs.playerChar.xPosition+ error;
        else
            x2=game.gs.playerChar.xPosition- error;
        Gdx.app.log("Aim difference:",game.gs.playerChar.xPosition+" vs. "+x2);
        float y2=game.height/game.gs.playerChar.screenFractionAbove0;
        game.gs.botArrows.get(game.gs.botArrows.size-1).rotation=(float)Math.toDegrees(Math.atan((y2 - y1) / (x2-x1)))-90;
        if(game.gs.botArrows.get(game.gs.botArrows.size-1).rotation>-100)
            game.gs.botArrows.get(game.gs.botArrows.size-1).rotation-=180;
        //Gdx.app.log("rotation :",game.gs.botArrows.get(game.gs.botArrows.size-1).rotation+"");
    }
    public void moveTo(int x){
        float update= walkSpeed/botPlayerWalkspeedRatio;
        if(xPosition>x){
            update=-update;
        }

        while(notReached(update,x)){
            try {
                Thread.sleep(5);
            }
            catch (InterruptedException e){
                //do nothing
            }
            xPosition+=update;
            game.gs.botArrows.get(game.gs.botArrows.size-1).xPosition=(int)((game.gs.bot.xPosition)+(((float)50/88)*(game.width/game.gs.bot.screenFractionCharWidth)));
        }
    }
    public boolean notReached(float update,int x){
        boolean notReached=true;
        if(update>0 && xPosition>=x)
            notReached=false;
        else if(update<=0 && xPosition<=x)
            notReached=false;
        return notReached;
    }
    public void dispose(){
        character.dispose();
    }
}
