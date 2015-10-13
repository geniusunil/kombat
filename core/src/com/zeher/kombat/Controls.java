package com.zeher.kombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zeher.kombat.Screens.GameScreen;

public class Controls {
    Kombat game;
    GameScreen gs;
    SpriteBatch batch;
    TextureRegion left;
    TextureRegion right;

    Rectangle controlBounds;
    int leftRightWidth=240;
    int leftRightHeight=240;
    int heightOfButtons;

    public Stage stage;
    public Table table;
    TextButton.TextButtonStyle style;
    LeftButton leftB;
    Texture texture;
    RightButton rightB;
    TextureRegion upRegion;
    TextureRegion downRegion;
    BitmapFont buttonFont;
    public int controlsPointer1=-1;

    public int controlsPointer2=-1;
    public Controls (Kombat game) {
        this.game=game;
        this.batch=game.batch;
        controlBounds=new Rectangle();
        controlBounds.setSize(leftRightWidth, leftRightHeight);



        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        //table.setDebug(true); // This is optional, but enables debug lines for tables.
        texture=new Texture(Gdx.files.internal("controls.png"));
        upRegion = new TextureRegion(texture,64,64);
        downRegion =new TextureRegion(texture,64,64);
        buttonFont =new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = buttonFont;
        loadAssets();
    }

    private void loadAssets () {
        Texture texture = new Texture(Gdx.files.internal("controls.png"));
        TextureRegion[] buttons = TextureRegion.split(texture, 64, 64)[0];
        leftB = new LeftButton("lower", style,game);
        table.add(game.introScreen.settings).width(120).height(120).padRight(240);
        table.add(game.introScreen.help).width(120).height(120).padLeft(240);
        table.row();
        table.add(leftB).width(leftRightWidth).height(leftRightHeight).padRight(120);
        table.left().bottom();

        rightB = new RightButton("lower", style,game);
        rightB.align(Align.right);
        table.add(rightB).width(leftRightWidth).height(leftRightHeight).padLeft(120);

        leftB.addListener(new ActorGestureListener() {
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftB.touchFlag = true;
                controlsPointer1=pointer;
                // Gdx.app.log("occured: ", "touchDown on less");
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftB.touchFlag = false;
                controlsPointer1=-1;
                // Gdx.app.log("occured: ", "touchUp on less");
            }
        });
        rightB.addListener(new ActorGestureListener() {
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightB.touchFlag = true;
                controlsPointer2 = pointer;
                // Gdx.app.log("occured: ", "touchDown on less");
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightB.touchFlag = false;
                controlsPointer2=-1;
                // Gdx.app.log("occured: ", "touchUp on less");
            }
        });
    }

    public void render (float delta) {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

        stage.draw();

    }

    public void dispose () {
/*
        left.getTexture().dispose();
        right.getTexture().dispose();*/

    }
}
