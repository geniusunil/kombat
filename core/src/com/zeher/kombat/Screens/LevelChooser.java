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
    public TextButton maxArrowsLevelUp,walkSpeedLevelUp,arrowSpeedLevelUp,healthBarLevelUp,truthLevelUp;
    public Label coins;
    public int needForMaxArrowsLevelUp,needForWalkSpeedLevelUp,needForArrowSpeedLevelUp,needForHealthBarLevelUp,needForTruthLevelUp;
    public LevelChooser(final Kombat game){
        this.game=game;
        curLevel=game.progress.getInteger("curLevel");

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


        back = new TextButton("Back", style);
        table.add(back).expand().fill();// Add widgets to the table here.

        coins=new Label(""+game.progress.getInteger("coins"),skin);
        coins.setFontScale(10);
        table.add(coins).expand().fill();
        int progressMaxArrows=game.progress.getInteger("maxArrows");
        needForMaxArrowsLevelUp=progressMaxArrows*40+25;
        maxArrowsLevelUp=new TextButton("maxArrows "+progressMaxArrows+"\nneeds: "+needForMaxArrowsLevelUp,style);
        int progressWalkSpeed=game.progress.getInteger("walkSpeed");
        needForWalkSpeedLevelUp=progressWalkSpeed*40+25;
        walkSpeedLevelUp=new TextButton("walkSpeed "+progressWalkSpeed+"\nneeds: "+needForWalkSpeedLevelUp,style);
        int progressArrowSpeed=game.progress.getInteger("arrowSpeed");
        needForArrowSpeedLevelUp=progressArrowSpeed*40+25;
        arrowSpeedLevelUp=new TextButton("arrowSpeed"+progressArrowSpeed+"\nneeds: "+needForArrowSpeedLevelUp,style);
        int progressHealthBar=game.progress.getInteger("healthBar");
        needForHealthBarLevelUp=progressHealthBar*40+25;
        healthBarLevelUp=new TextButton("healthBar "+progressHealthBar+"\nneeds: "+needForHealthBarLevelUp,style);
        int progressTruth=game.progress.getInteger("truth");
        needForTruthLevelUp=progressTruth*40+25;
        truthLevelUp=new TextButton("truth "+progressTruth+"\nneeds: "+needForTruthLevelUp,style);

        table.row();
        table.add(maxArrowsLevelUp).expand().fill();

        table.add(walkSpeedLevelUp).expand().fill();
        table.add(arrowSpeedLevelUp).expand().fill();

        table.add(healthBarLevelUp).expand().fill();
        table.add(truthLevelUp).expand().fill();
        addListerners();

    }

    private void addListerners() {
        maxArrowsLevelUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.progress.getInteger("coins") >= needForMaxArrowsLevelUp) {
                    game.progress.putInteger("maxArrows", (game.progress.getInteger("maxArrows") + 1));
                    game.progress.putInteger("coins", game.progress.getInteger("coins") - needForMaxArrowsLevelUp);
                    game.progress.flush();
                    coins.setText("" + game.progress.getInteger("coins"));
                    int progressMaxArrows = game.progress.getInteger("maxArrows");
                    needForMaxArrowsLevelUp = progressMaxArrows * 40 + 25;
                    maxArrowsLevelUp.setText("maxArrows " + progressMaxArrows + "\nneeds: " + needForMaxArrowsLevelUp);
                }
            }
        });
        walkSpeedLevelUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.progress.getInteger("coins") >= needForWalkSpeedLevelUp) {
                    game.progress.putInteger("walkSpeed", (game.progress.getInteger("walkSpeed") + 1));
                    game.progress.putInteger("coins", game.progress.getInteger("coins") - needForWalkSpeedLevelUp);
                    game.progress.flush();
                    coins.setText("" + game.progress.getInteger("coins"));
                    int progressWalkSpeed = game.progress.getInteger("walkSpeed");
                    needForWalkSpeedLevelUp = progressWalkSpeed * 40 + 25;
                    walkSpeedLevelUp.setText("walkSpeed " + progressWalkSpeed + "\nneeds: " + needForWalkSpeedLevelUp);
                }
            }
        });
        arrowSpeedLevelUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.progress.getInteger("coins")>=needForArrowSpeedLevelUp){
                    game.progress.putInteger("arrowSpeed", (game.progress.getInteger("arrowSpeed") + 1));
                    game.progress.putInteger("coins",game.progress.getInteger("coins")-needForArrowSpeedLevelUp);
                    game.progress.flush();
                    coins.setText("" + game.progress.getInteger("coins"));
                    int progressArrowSpeed=game.progress.getInteger("arrowSpeed");
                    needForArrowSpeedLevelUp=progressArrowSpeed*40+25;
                    arrowSpeedLevelUp.setText("arrowSpeed"+progressArrowSpeed+"\nneeds: "+needForArrowSpeedLevelUp);
                }
            }
        });
        healthBarLevelUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.progress.getInteger("coins")>=needForHealthBarLevelUp){
                    game.progress.putInteger("healthBar", (game.progress.getInteger("healthBar") + 1));
                    game.progress.putInteger("coins",game.progress.getInteger("coins")-needForHealthBarLevelUp);
                    game.progress.flush();
                    coins.setText("" + game.progress.getInteger("coins"));
                    int progressHealthBar=game.progress.getInteger("healthBar");
                    needForHealthBarLevelUp=progressHealthBar*40+25;
                    healthBarLevelUp.setText("healthBar"+progressHealthBar+"\nneeds: "+needForHealthBarLevelUp);
                }
            }
        });
        truthLevelUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.progress.getInteger("coins")>=needForTruthLevelUp){
                    game.progress.putInteger("truth", (game.progress.getInteger("truth") + 1));
                    game.progress.putInteger("coins",game.progress.getInteger("coins")-needForTruthLevelUp);
                    game.progress.flush();
                    coins.setText("" + game.progress.getInteger("coins"));
                    int progressTruth=game.progress.getInteger("truth");
                    needForTruthLevelUp=progressTruth*40+25;
                    truthLevelUp.setText("truth"+progressTruth+"\nneeds: "+needForTruthLevelUp);
                }
            }
        });
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
