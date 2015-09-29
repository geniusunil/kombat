package com.zeher.kombat;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.zeher.kombat.Screens.GameScreen;


/**
 * Created by code on 3/31/2015.
 * Class representing the bricks in middle of gameScreen
 */
public class Brick {
    int type;
    public Sprite img;
    GameScreen gs;
    public Rectangle bounds;
    public int oneBrickWidth=6;
    public int oneBrickHeight=6;
    public float horizontalScale; //to copy scale value from Map.java
    public float verticalScale;
    public Brick(GameScreen gs, int x, int y) {
        this.gs=gs;
        img=new Sprite(gs.allBricks,0,0,oneBrickWidth,oneBrickHeight);
        bounds=new Rectangle();
        bounds.x=x;
        bounds.y=y;
        setBrickLocationSize(x,y);
        this.horizontalScale=gs.horizontalScale;
        this.verticalScale=gs.verticalScale;
    }
    public void setBrickType(int n){
        type=n;
    }
    public void update(int type){
        setBrickType(type);
        switch(type){
            case 0:
                img=new Sprite(gs.allBricks,0,0,oneBrickWidth,oneBrickHeight);
                break;
            case 1:
                img=new Sprite(gs.allBricks,oneBrickWidth,0,oneBrickWidth,oneBrickHeight);
                break;
            case 2:
                img=new Sprite(gs.allBricks,oneBrickWidth*2,0,oneBrickWidth,oneBrickHeight);
                break;
            case 3:
                img=new Sprite(gs.allBricks,oneBrickWidth*3,0,oneBrickWidth,oneBrickHeight);
                break;
            case 4:
                img=new Sprite(gs.allBricks,oneBrickWidth*4,0,oneBrickWidth,oneBrickHeight);
                break;

        }
        setBrickLocationSize((int)bounds.x,(int)bounds.y);
    }
    public void setBrickLocationSize(int x, int y){

        img.setPosition((x*horizontalScale),(y*verticalScale));
        img.setSize(horizontalScale,verticalScale);

    }
    public void dispose(){
        img.getTexture().dispose();
    }
}
