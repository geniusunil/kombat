package com.zeher.kombat;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.zeher.kombat.Screens.LevelChooser;

/**
 * Created by zucky on 10/4/2015.
 */
public class MoreButton extends TextButton {
    LevelChooser lc;
    float wait;
    public boolean touchFlag=false;
    public MoreButton(String text, TextButtonStyle skin, Kombat game) {
        super(text, skin);
        this.lc=game.introScreen.lc;

    }
    @Override
    public void act(float delta){
       // Gdx.app.log("delta: ",""+delta);
        wait+=delta;
        if(touchFlag && lc.curLevel<100 && wait>delta*8) {
            lc.curLevel++;
            lc.level.setText("" + lc.curLevel);
            wait=0f;
        }

    }
}
