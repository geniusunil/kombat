package com.zeher.kombat;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.zeher.kombat.Screens.GameScreen;

/**
 * Created by zucky on 10/4/2015.
 */
public class RightButton extends TextButton {
    Kombat game;
    float wait;
    public boolean touchFlag=false;
    public RightButton(String text, TextButtonStyle skin, Kombat game) {
        super(text, skin);
        this.game=game;
    }
    @Override
    public void act(float delta){
       // Gdx.app.log("delta: ",""+delta);
        wait+=delta;
        if(touchFlag && game.gs.playerChar.xPosition+game.gs.playerChar.character.getWidth()<game.width && wait>=delta) {
            game.gs.playerChar.xPosition+=(game.gs.playerChar.walkSpeed);
            try {
                game.gs.arrows.get(game.gs.arrows.size - 1).update(0);
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            wait=0f;
        }

    }
}
