package com.zeher.kombat.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zeher.kombat.Kombat;
import com.zeher.kombat.LessButton;

/**
 * Created by zucky on 10/28/2015.
 */
public class Settings implements Screen {
    Kombat game;
    Stage stage;
    Table table;

    Texture texture;
    TextureRegion upRegion;
    TextureRegion downRegion;
    BitmapFont buttonFont;
    TextButton.TextButtonStyle style;

    TextButton resetGame;
    TextButton back;
    public Settings (Kombat game){
        this.game=game;
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
        resetGame=new TextButton("reset game progress", style);
        table.add(resetGame).expand().fill();

        back = new TextButton("Back", style);
        table.add(back).expand().fill();// Add widgets to the table here.
        resetGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resetGame();
            }
        });


    }

    private void resetGame() {
        game.progress.putInteger("coins",0);
        game.progress.putInteger("levels_unlocked",0);
        game.progress.flush();
    }


    @Override
    public void show() {

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

    }
}
