package com.kirdmiv.flappybaranus.states;

/**
 * Created by Kirill on 4/10/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kirdmiv.flappybaranus.FlappyBaran;

import com.kirdmiv.flappybaranus.sprites.Bird;


public class PlayState extends State {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 5;
    private static final int GROUND_Y_OFFSET = -30;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private int score = 0;
    private BitmapFont font;

    private Array<com.kirdmiv.flappybaranus.sprites.Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        font = new BitmapFont();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        bird = new Bird(50, 300);
        camera.setToOrtho(false, FlappyBaran.WIDTH / 2, FlappyBaran.HEIGHT / 2);
        bg = new Texture("backgrnd.jpg");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<com.kirdmiv.flappybaranus.sprites.Tube>();

        for (int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new com.kirdmiv.flappybaranus.sprites.Tube(i * (TUBE_SPACING + com.kirdmiv.flappybaranus.sprites.Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            bird.jump();

    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        camera.position.x = bird.getPosition().x + 80;

        for (int i = 0; i < tubes.size; i++){

            com.kirdmiv.flappybaranus.sprites.Tube tube = tubes.get(i);

            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((com.kirdmiv.flappybaranus.sprites.Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds()) || bird.onGround())
                gsm.set(new GameOver(gsm, score));
            if (bird.getPosition().x > (score + 1) * (com.kirdmiv.flappybaranus.sprites.Tube.TUBE_WIDTH + TUBE_SPACING + bird.getBounds().width / 2)){
                score++;
            }
        }
        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        for (com.kirdmiv.flappybaranus.sprites.Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosBotTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        font.draw(sb, Integer.toString(score), camera.position.x, 370);
        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for (com.kirdmiv.flappybaranus.sprites.Tube tube : tubes)
            tube.dispose();

    }

    private void updateGround(){
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }
}