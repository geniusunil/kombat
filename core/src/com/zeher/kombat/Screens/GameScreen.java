package com.zeher.kombat.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.zeher.kombat.Arrow;
import com.zeher.kombat.Bot;
import com.zeher.kombat.BotArrow;
import com.zeher.kombat.Brick;
import com.zeher.kombat.Controls;
import com.zeher.kombat.Kombat;
import com.zeher.kombat.Map;
import com.zeher.kombat.MapRenderer;
import com.zeher.kombat.MyGestureListener;
import com.zeher.kombat.PlayerChar;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by code on 3/30/2015.
 * Class representing the home in game
 */
public class GameScreen implements Screen{

    public Kombat game;
    public Map map;
    //String mapPNG;
    public Texture allBricks;
    MapRenderer maprenderer;
    public SpriteBatch batch;
    public PlayerChar playerChar;
    public Bot bot;
    public Controls controls;
    public int width;
    public int height;
    public float horizontalScale; //to copy scale value from Map.java
    public float verticalScale;
    public Arrow arrow;
    public BotArrow botArrow;
    public Array<Arrow> arrows;
    public Array<BotArrow> botArrows;
    public Texture background;
    public GameOverScreen gos;
    int numOfParameters=4;  //paramters changing in diff. levels
    int params[];
    public InputMultiplexer gameS;
    public boolean paused=false; //to pause entire game
    public GameScreen(Kombat game){
        this.batch=game.batch;
        this.game=game;
        width=game.width;
        height=game.height;
        playerChar = new PlayerChar(this);
        arrows=new Array(); //already done above
        botArrows= new Array();
        bot = new Bot(game);
    }

    public void show(){
        unpause();
//        mapPNG="maps/map0.png";
        //allBricks=new Texture("allBricks.png");
        if(!bot.startWorkingAlreadyCalled)
            bot.startWorking();
        controls = new Controls(game);
        gameS= new InputMultiplexer();
        gameS.addProcessor(game.mgl);
        gameS.addProcessor(controls.stage);
        Gdx.input.setInputProcessor(gameS); //all gestures will be automatically handled by event listener
        background=new Texture("bg.jpg");
//        map=new Map(this,mapPNG);
//        //keepChangingBrickTypes(); //starts a thread which changes the brick-types in every 5 sec.
        //maprenderer=new MapRenderer(this);
        if(arrows.size==0 && botArrows.size==0) {
            initMglNewArrow();
            initMglNewBotArrow();
        }

        BotArrow.arrowImg=new Texture("arrow.png");
        BotArrow.bow=new Texture("bow.png");
        game.font.setScale(4);
        gos=new GameOverScreen(game);

        Gdx.app.log("in gs show","yo");
    }
    public void initMglNewArrow(){
        game.gs.arrow=new Arrow(game);
        game.gs.arrows.add(arrow);

        Gdx.app.log("arrowsSize :", " " + arrows.size);

    }
    public void initMglNewBotArrow(){
        game.gs.botArrow=new BotArrow(game);
        game.gs.botArrows.add(botArrow);
    }
    public void render(float delta){
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.camera.combined);
        batch.begin();
        //maprenderer.render();

        batch.draw(background, 0, 0);
        if(bot.lives>0 && playerChar.lives>0)
            game.font.draw(batch,"bot : "+bot.lives+" you : "+playerChar.lives,0,600);
        else {

                bot.flag=false;

            /*catch ( e){

            }*/

            game.setScreen(gos);
            game.gs.dispose();
        }
        game.gs.arrow.render();
        playerChar.render();
        bot.render();
        game.gs.botArrow.render();
        game.font.draw(batch, "" + game.gs.botArrows.size, 250, 250);
        //Gdx.app.log("GameScreen.java arrows by player: ", game.gs.arrows.size + "");
       // Gdx.app.log("GameScreen.java arrows by bot: ",game.gs.botArrows.size+"");
        batch.end();
        controls.render(delta);

    }
    public void hide(){
        pauseGame();
    }
    public void pause(){
        pauseGame();
    }
    public void resume(){

        Gdx.app.log("GameScreen resume: ","occured");
        unpause();
    }
    public void resize(int x,int y){

    }
    public void dispose(){
        while(arrows.size>0){
            arrows.removeIndex(0);
        }
        while(botArrows.size>0){
            //BotArrow barr=botArrows.get(0);
            botArrows.removeIndex(0);
            //barr.dispose();
        }
       /* map.dispose();
        allBricks.dispose();*/
        playerChar.dispose();
       // controls.dispose();
    }

    public synchronized void pausePoint() {
        while (paused) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void pauseGame() {
        paused = true;
    }

    public synchronized void unpause() {
        paused = false;
        this.notifyAll();
    }

    public void keepChangingBrickTypes(){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Random random=new Random();
                    int type=random.nextInt(5);
                    while(true) {
                        for (int i = 0; i < map.bricks.size; i++) {
                            Brick brick = map.bricks.get(i);
                            brick.update(type);
                            type = random.nextInt(5);

                        }
                        Thread.sleep(5000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        thread.start();
    }
    public void setLevel(int curLevel){
        int remainder=curLevel%numOfParameters;
        int quotient=curLevel/numOfParameters;
        Gdx.app.log("setLevel:","curlevel "+curLevel+" rem "+remainder+" quo"+quotient);
        params=new int[numOfParameters];
        for(int i=0;i<numOfParameters;i++){
            params[i]=quotient;
            if(remainder>0) {
                params[i]++;
                remainder--;
            }
        }

        //on the bot side

        bot.maxArrows=(params[0]+1);
        bot.walkSpeed=params[1]/2+1;
        bot.arrowSpeed=5/((params[2]/5)+1)+1;
        bot.accuracy=30/((params[3]/3)+1);
        bot.lives=curLevel+1;
        //bot.dodgeLevel=params[4]+1;
        Gdx.app.log("bot arrowspeed: ",bot.arrowSpeed+"");
        //player side
       // game.gs.playerChar.arrow_interval_level=bot.arrow_interval_level;
        game.gs.playerChar.maxArrows=(params[0]+1);
        game.gs.playerChar.walkSpeed=params[1]/2+1;
        game.gs.playerChar.arrowSpeed=5/((params[2]/5)+1)+1; //arrowSpeed can't be zero
        game.gs.playerChar.lives=curLevel+1;

        //Gdx.app.log("arrow_intervals",game.gs.playerChar.arrow_interval +"");
        Gdx.app.log("params",Arrays.toString(params));

        //again bot
        game.gs.bot.workSleepTime=game.height/4*game.gs.playerChar.arrowSpeed;
        Gdx.app.log("GameScreen.java workSleepTime: ",game.gs.bot.workSleepTime+"");
    }
}
