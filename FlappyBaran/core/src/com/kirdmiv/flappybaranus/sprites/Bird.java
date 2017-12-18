package com.kirdmiv.flappybaranus.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kirill on 4/10/2017.
 */

public class Bird {
    private static final int MOVEMENT = 100;
    private static final int GRAVITY = -15;
    public static final int FRAME_COUNT = 8;
    private Vector3 position;
    private Vector3 velosity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Sound flap;
    private Texture texture;

    public Bird(int x, int y){
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
        texture = new Texture("Sheep-Animated-Gif4.png");
        birdAnimation = new Animation(new TextureRegion(texture), FRAME_COUNT, 0.9f);
        bounds = new Rectangle(x, y, texture.getWidth() / FRAME_COUNT, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public void update(float dt){
        birdAnimation.update(dt);
        if (position.y > 30)
            velosity.add(0, GRAVITY, 0);
        velosity.scl(dt);
        position.add(MOVEMENT * dt, velosity.y, 0);
        if (position.y < 82)
            position.y = 82;
        if (position.y > 375)
            position.y = 370;
        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);


    }
    public void jump(){
        velosity.y = 300;
        flap.play();
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public boolean onGround() {
        if (position.y < 83)
            return true;
        return false;
    }


    public void dispose() {
        texture.dispose();
        flap.dispose();
    }
}
