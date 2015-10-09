package com.zeher.kombat;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.zeher.kombat.Screens.GameScreen;
import com.zeher.kombat.Screens.LevelChooser;

/**
 * Created by zucky on 10/4/2015.
 */
public class LeftButton extends TextButton {
    Kombat game;
    float wait;
    public boolean touchFlag=false;
    public LeftButton(String text, TextButtonStyle skin, Kombat game) {
        super(text, skin);
        this.game=game;
    }
    @Override
    public void act(float delta){
       // Gdx.app.log("delta: ",""+delta);
        wait+=delta;
        if(touchFlag && game.gs.playerChar.xPosition>0 && wait>=delta) {
            game.gs.playerChar.xPosition-=game.gs.playerChar.walkSpeed;
            try {
                game.gs.arrows.get(game.gs.arrows.size - 1).update(0);
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            wait=0f;
        }

    }
}
