package com.zeher.kombat;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.zeher.kombat.Screens.LevelChooser;

/**
 * Created by zucky on 10/4/2015.
 */
public class MoreButton extends TextButton {
    LevelChooser lc;
    float wait;
    public boolean touchFlag=false;
    public Preferences prefs;
    public MoreButton(String text, TextButtonStyle skin, Kombat game) {
        super(text, skin);
        this.lc=game.introScreen.lc;
        prefs=game.prefs;
    }
    @Override
    public void act(float delta){
       // Gdx.app.log("delta: ",""+delta);
        wait+=delta;
        if(touchFlag && lc.curLevel<prefs.getInteger("levels_unlocked") && wait>delta*6) {
            lc.curLevel++;
            lc.level.setText("" + lc.curLevel);
            wait=0f;
        }

    }
}
