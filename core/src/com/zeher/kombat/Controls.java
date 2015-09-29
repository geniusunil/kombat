package com.zeher.kombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.zeher.kombat.Screens.GameScreen;

public class Controls {
    GameScreen gs;
    SpriteBatch batch;
    TextureRegion left;
    TextureRegion right;

    Rectangle controlBounds;
    int leftRightWidth=240;
    int leftRightHeight=240;
    int heightOfButtons;

    public Controls (GameScreen gs) {
        this.gs = gs;
        batch=gs.batch;
        controlBounds=new Rectangle();
        controlBounds.setSize(leftRightWidth,leftRightHeight);
        loadAssets();
    }

    private void loadAssets () {
        Texture texture = new Texture(Gdx.files.internal("controls.png"));
        TextureRegion[] buttons = TextureRegion.split(texture, 64, 64)[0];

        left = buttons[0];
        right = buttons[1];

    }

    public void render () {
        batch.draw(left, 0, heightOfButtons,controlBounds.getWidth(),controlBounds.getHeight());
        batch.draw(right, gs.game.width-controlBounds.getWidth(),heightOfButtons,controlBounds.getWidth(),controlBounds.getHeight());
        //batch.draw(cubeControl, 320,0,80,80);
    }

    public void dispose () {

        left.getTexture().dispose();
        right.getTexture().dispose();

    }
}
