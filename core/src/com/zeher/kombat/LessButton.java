package com.zeher.kombat;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.zeher.kombat.Screens.LevelChooser;

/**
 * Created by zucky on 10/4/2015.
 */
public class LessButton extends TextButton {
    LevelChooser lc;
    float wait;
    public boolean touchFlag=false;
    public Preferences progress;
    public LessButton(String text, TextButton.TextButtonStyle skin,Kombat game) {
        super(text, skin);
        this.lc=game.introScreen.lc;
        this.progress=game.progress;
    }
    @Override
    public void act(float delta){
       // Gdx.app.log("delta: ",""+delta);
        wait+=delta;
        if(touchFlag && lc.curLevel>0 && wait>delta*6) {
            lc.curLevel--;
            progress.putInteger("curLevel",lc.curLevel);
            progress.flush();
            lc.level.setText("" + lc.curLevel);
            wait=0f;
        }

    }
}
