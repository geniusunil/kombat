package com.zeher.kombat.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zeher.kombat.Kombat;

/**
 * Created by zucky on 5/30/2015.
 */
public class IntroScreen implements Screen{
    Kombat game;
    long showTime; //time when the screen showed up
    Texture introbg;
    public LevelChooser lc;
    //for the button
    Stage stage;
    Table table;
    public TextButton help,settings;
    public boolean helpBtouchFlag;
    public int helpPointer=-1;
    public InstructionsScreen instructions;
    public IntroScreen (Kombat game){
        this.game=game;
        introbg=new Texture("introbg.jpg");
    }
    @Override
    public void show() {
        showTime=System.currentTimeMillis();

        instructions=new InstructionsScreen(game);
        lc=new LevelChooser(game);
        //for the button
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //table.setDebug(true); // This is optional, but enables debug lines for tables.
        Texture texture=new Texture(Gdx.files.internal("controls.png"));
        TextureRegion upRegion = new TextureRegion(texture,64,64);
        //BitmapFont buttonFont =new BitmapFont();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.font = game.font;

        TextButton levels = new TextButton("level", style);
        table.add(levels);
        settings = new TextButton("settings", style);
        table.add(settings);// Add widgets to the table here.
        help = new TextButton("help",style);
        table.add(help);
        TextButton play = new TextButton("play",style);
        table.add(play);
        levels.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(lc);
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Settings(game));
            }
        });
        help.addListener(new ActorGestureListener() {

            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {


                helpBtouchFlag = true;
                helpPointer=pointer;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(instructions);
                helpBtouchFlag = false;
                // Gdx.app.log("occured: ", "touchUp on less");
            }
        });

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goToGameScreen();
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.font.setScale(8);
        game.batch.begin();
        game.batch.draw(introbg, 0, 0);
        game.font.drawMultiLine(game.batch, "Karna \n    vs \n       Arjuna", 100, 1200);
        game.font.setScale(5);
        game.font.draw(game.batch, "Touch for a bout!", 100, 750);
        game.font.setScale(2);
        //game.font.draw(game.batch,""+game.gs.arrows.size,250,250);



        game.batch.end();
        //for the button
        stage.act(delta);
        stage.draw();
       /* if(Gdx.input.isTouched() && System.currentTimeMillis()-showTime>500){
            *//*game.setScreen(new LevelChooser(game));
            //game.setScreen(new InstructionsScreen(game));
            this.dispose();*//*
            //Gdx.app.log("introscreen arrows :",game.gs.arrows.size+"");
        }*/
    }
    public void goToGameScreen(){
        game.setScreen(game.gs);
        if(game.gs.bot.lives==0)
            game.gs.setLevel(LevelChooser.curLevel);
    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
