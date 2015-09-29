package com.zeher.kombat.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zeher.kombat.Kombat;

/**
 * Created by zucky on 9/20/2015.
 */
public class LevelChooser implements Screen {
    Kombat game;
    Stage stage;
    Table table;
    public static int curLevel;
    Label level;
    Texture texture;
    TextureRegion upRegion;
    TextureRegion downRegion;
    BitmapFont buttonFont;
    TextButton.TextButtonStyle style;
    TextButton less;
    Skin skin;
    TextButton more;
    TextButton back;
    public static boolean levelFlag;
    public LevelChooser(final Kombat game){
        this.game=game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(true); // This is optional, but enables debug lines for tables.
        texture=new Texture(Gdx.files.internal("controls.png"));
        upRegion = new TextureRegion(texture,64,64);
        downRegion =new TextureRegion(texture,64,64);
        buttonFont =new BitmapFont();

        style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = buttonFont;
        less = new TextButton("lower", style);
        table.add(less).expand().fill();

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        level=new Label(""+curLevel,skin);
        level.setFontScale(10);
        table.add(level).expand().fill();

        more = new TextButton("higher", style);
        table.add(more).expand().fill();// Add widgets to the table here.

        table.row();
        back = new TextButton("Back", style);
        table.add(back).expand().fill();// Add widgets to the table here.


        //add listeners to widgets
        less.addListener(new ChangeListener() {

           /* boolean touchdown=true;*/

            /*@Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                touchdown=false;
                Gdx.app.log("touchdown touchup:",""+touchdown);
                Gdx.app.log("touchup in levelchooser: ","occurred");
                //do your stuff
                //it will work when finger is released..
            }*/
           /* @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                  int pointer, int button) {
                Gdx.app.log("touchdown: ","hai");
                //touchdown=true;
                *//*while(touchdown){
                    Gdx.app.log("level less pressed",less.isPressed()+"");
                    curLevel--;
                    Gdx.app.log("curLevel: ",curLevel+"");
                    level.setText(""+curLevel);
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e){

                    }*//*
                return true;
                }*/
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //while (less.isPressed()) {
//                    Gdx.app.log("ispressed ", "yes");
                    curLevel--;
                    level.setText("" + curLevel);
                    /*Gdx.app.log("curLevel: ", curLevel + "");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }*/
                //}
            }




        });
        more.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                curLevel++;
                level.setText(""+curLevel);
            }
        });
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.introScreen);

            }
        });
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
