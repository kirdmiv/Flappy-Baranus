package com.kirdmiv.flappybaranus.states;

/**
 * Created by Kirill on 4/10/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kirdmiv.flappybaranus.FlappyBaran;


public class GameOver extends State {

    private Texture background;
    private Texture gameover;
    private BitmapFont font;
    private int score;
    private int highscore;
    private int prevHighScore;
    private static Preferences prefs;

    public GameOver(GameStateManager gsm, int score) {
        super(gsm);
        this.score = score;
        camera.setToOrtho(false, FlappyBaran.WIDTH / 2, FlappyBaran.HEIGHT / 2);
        background = new Texture("backgrnd.jpg");
        gameover = new Texture("gameover.png");
        font = new BitmapFont();
        prefs = Gdx.app.getPreferences("FlappyBaran");

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

        prevHighScore = getHighScore();

        if (prevHighScore < this.score) {
            setHighScore(this.score);
            highscore = this.score;
        } else {
            highscore = prevHighScore;
        }

    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(gameover, camera.position.x - gameover.getWidth() / 2, camera.position.y);
        font.draw(sb, "Your score: " + score, camera.position.x, 170);
        font.draw(sb, "Your highscore: " + highscore, camera.position.x, 150);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();

    }
}