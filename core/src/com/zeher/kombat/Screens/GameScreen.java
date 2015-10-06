package com.zeher.kombat.Screens;

import com.badlogic.gdx.Gdx;
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
    int numOfParameters=7;  //paramters changing in diff. levels
    int params[]=new int[7];
    public GameScreen(Kombat game){
        this.batch=game.batch;
        this.game=game;
        width=game.width;
        height=game.height;
        playerChar = new PlayerChar(this);
        arrows=new Array(); //already done above
        botArrows= new Array();
        controls = new Controls(this);
    }

    public void show(){
//        mapPNG="maps/map0.png";
        //allBricks=new Texture("allBricks.png");
        Gdx.input.setInputProcessor(game.mgl); //all gestures will be automatically handled by event listener
        background=new Texture("bg.jpg");
//        map=new Map(this,mapPNG);
//        //keepChangingBrickTypes(); //starts a thread which changes the brick-types in every 5 sec.
        //maprenderer=new MapRenderer(this);
        bot = new Bot(game);
        initMglNewArrow();
        initMglNewBotArrow();


        BotArrow.arrowImg=new Texture("arrow.png");
        BotArrow.bow=new Texture("bow.png");
        game.font.setScale(4);


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
            game.setScreen(new GameOverScreen(game));
            game.gs.dispose();
        }
        game.gs.arrow.render();
        playerChar.render();
        bot.render();
        game.gs.botArrow.render();

        batch.setColor(1,1,1,0.5f);
        controls.render();
        batch.setColor(1,1,1,1);
        game.font.draw(batch,""+game.gs.arrows.size,250,250);
        batch.end();
        if(Gdx.input.isTouched()){
            game.mgl.isTouched(Gdx.input.getX(), Gdx.input.getY());

        }

    }
    public void hide(){

    }
    public void pause(){

    }
    public void resume(){

    }
    public void resize(int x,int y){

    }
    public void dispose(){
        for(Arrow arr : game.gs.arrows){
            arr.dispose();
        }
        while(arrows.size>0){
            arrows.removeIndex(0);
        }
        for(BotArrow barr : game.gs.botArrows){
            barr.dispose();
        }
        while(botArrows.size>0){
            botArrows.removeIndex(0);
        }
       /* map.dispose();
        allBricks.dispose();*/
        playerChar.dispose();
        controls.dispose();
        //Gdx.app.log("gs dispose()","called");
        bot.dispose();


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
        int remainder=curLevel%7;
        int quotient=curLevel/7;
        Gdx.app.log("setLevel:","curlevel "+curLevel+" rem "+remainder+" quo"+quotient);
        for(int i=0;i<7;i++){
            params[i]=quotient;
            if(remainder>0) {
                params[i]++;
                remainder--;
            }
        }

        //on the bot side

        bot.arrow_interval=10000/(params[0]+1);
        bot.walkSpeed=params[1]+1;
        bot.arrowSpeed=10/(params[2]+1);
        bot.accuracy=10/(params[3]+1);
        bot.lives=curLevel+1;
        bot.dodgeLevel=params[4]+1;
        Gdx.app.log("dodge Level: ",bot.dodgeLevel+"");
        //player side
       // game.gs.playerChar.arrow_interval_level=bot.arrow_interval_level;
        game.gs.playerChar.maxArrows=(params[0]+1)*2;
        game.gs.playerChar.walkSpeed=params[1]+1;
        game.gs.playerChar.arrowSpeed=10/(params[2]+1);
        game.gs.playerChar.lives=curLevel+1;

        //Gdx.app.log("arrow_intervals",game.gs.playerChar.arrow_interval +"");
        Gdx.app.log("params",Arrays.toString(params));
    }
}
