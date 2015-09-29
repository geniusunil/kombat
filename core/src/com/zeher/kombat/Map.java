package com.zeher.kombat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.zeher.kombat.Screens.GameScreen;
/**
 * Created by code on 3/31/2015.
 * Class representing the home in game
 */
public class Map {
    static int BRICK = 0x9f9f9f;
    public Array<Brick> bricks=new Array<Brick>();
    GameScreen gs;
    Brick brick;
    public float horizontalScale;   // one pixel on png map = scale no. of pixels on actual world
    public float verticalScale;
    public int width;
    public int height;
    public Map(GameScreen gs,String png){
        this.gs=gs;
        width=gs.width;
        height=gs.height;
        loadBinary(png);
    }

    private void loadBinary(String png) {
        Pixmap pixmap = new Pixmap(Gdx.files.internal(png));
        horizontalScale=width/pixmap.getWidth();
        verticalScale=height/pixmap.getHeight();
        gs.horizontalScale=horizontalScale;
        gs.verticalScale=verticalScale;
        for (int y = 0; y < pixmap.getHeight(); y++) {
            for (int x = 0; x < pixmap.getWidth(); x++) {
                int pixel = pixmap.getPixel(x, y) >>> 8;
                if (pixel == BRICK) {
                    //System.out.println("bricks matched");
                    brick = new Brick(gs, x, y);
                    bricks.add(brick);
                }
            }
        }
    }
    public void dispose(){
        brick.dispose();
        for(Brick brick: bricks)
            brick.dispose();
    }

}
