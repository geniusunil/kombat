package com.zeher.kombat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zeher.kombat.Screens.GameScreen;

/**
 * Created by code on 3/31/2015.
 * Class rendering the map and bricks in game
 */
public class MapRenderer {
    GameScreen gs;
    SpriteBatch batch;
    public MapRenderer(GameScreen gs){
        this.gs=gs;
        this.batch=gs.batch;
    }
    public void render(){
        batch.setProjectionMatrix(gs.game.camera.combined);
        renderBricks();
    }
    public void renderBricks(){
        //System.out.println("no. of bricks "+ gs.map.bricks.size);
        //int i=0;
        for(Brick brick : gs.map.bricks) {
            //System.out.println(i++);
            brick.img.draw(batch);
        }
    }
}
