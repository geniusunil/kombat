package com.zeher.kombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.zeher.kombat.Screens.LevelChooser;

/**
 * Created by zucky on 10/4/2015.
 */
public class LessButton extends TextButton {
    LevelChooser lc;
    float wait;
    public LessButton(String text, TextButton.TextButtonStyle skin,Kombat game) {
        super(text, skin);
        this.lc=game.introScreen.lc;

    }
    @Override
    public void act(float delta){
       // Gdx.app.log("delta: ",""+delta);
        wait+=delta;
        if(lc.lowerFlag && lc.curLevel>0 && wait>delta*8) {
            lc.curLevel--;
            lc.level.setText("" + lc.curLevel);
            wait=0f;
        }

    }
}
