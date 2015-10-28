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
import com.zeher.kombat.LessButton;
import com.zeher.kombat.MoreButton;

/**
 * Created by zucky on 9/20/2015.
 */
public class LevelChooser implements Screen {
    Kombat game;
    Stage stage;
    Table table;
    public static int curLevel;
    public Label level;
    Texture texture;
    TextureRegion upRegion;
    TextureRegion downRegion;
    BitmapFont buttonFont;
    TextButton.TextButtonStyle style;
    LessButton less;
    Skin skin;
    MoreButton more;
    public TextButton back;
    public LevelChooser(final Kombat game){
        this.game=game;

    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
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

        Gdx.input.setInputProcessor(stage);
        less = new LessButton("lower", style,game);
        table.add(less).expand().fill();


        skin = new Skin(Gdx.files.internal("uiskin.json"));
        level=new Label(""+curLevel,skin);
        level.setFontScale(10);
        table.add(level).expand().fill();

        more = new MoreButton("higher", style,game);
        table.add(more).expand().fill();// Add widgets to the table here.

        table.row();
        back = new TextButton("Back", style);
        table.add(back).expand().fill();// Add widgets to the table here.

        less.addListener(new ActorGestureListener() {
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                less.touchFlag = true;
                Gdx.app.log("occured: ", "touchDown on less");
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                less.touchFlag = false;
                Gdx.app.log("occured: ", "touchUp on less");
            }
        });

        more.addListener(new ActorGestureListener() {
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                more.touchFlag = true;
                Gdx.app.log("occured: ", "touchDown on less");
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                more.touchFlag = false;
                Gdx.app.log("occured: ", "touchUp on less");
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
