package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.Player.*;

public class CharactersScreen implements Screen{

    private static final int FRAME_COLS = 17;
    private static final int FRAME_ROWS = 7;
    private static final float FRAME_DURATION = 0.1f;   //duration of each frame in seconds
    final TurnQuest game;

    private Texture spriteSheet;
    private TextureRegion[] frames;
    private Animation<TextureRegion> animation;
    private SpriteBatch batch;
    private float stateTime;

    public CharactersScreen(final TurnQuest game) {
        this.game = game;

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        setStage(new Stage(getViewport()));
        Gdx.input.setInputProcessor(getStage());

        setStage(new Stage(getViewport()));
    }

        @Override
    public void show() {


        spriteSheet= new Texture(Gdx.files.internal("Necromancer_creativekind-Sheet.png"));
          //attempt to resize texture
            /*int newWidth = 256; // new width in pixels
            int newHeight = 256; // new height in pixels

            Pixmap pixmap = new Pixmap(newWidth, newHeight, spriteSheet.getTextureData().getFormat());
            pixmap.drawPixmap(spriteSheet.getTextureData().consumePixmap(), 0, 0, spriteSheet.getWidth(), spriteSheet.getHeight(), 0, 0, newWidth, newHeight);
            Texture resizedTexture = new Texture(pixmap);

            spriteSheet=resizedTexture;*/


        int frameWidth=spriteSheet.getWidth() / FRAME_COLS;
        int frameHeight=spriteSheet.getHeight() / FRAME_ROWS;
        //splits the spriteSheet into separate frames
        //&& creates temporary array of frames to use in animation
        TextureRegion[][] tmpFrames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / FRAME_COLS, spriteSheet.getHeight() / FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index] = tmpFrames[i][j];
                index++;
            }
        }
        animation = new Animation<>(FRAME_DURATION, frames);
        batch = new SpriteBatch();

        createBackTable();

        }

        private void createBackTable(){
            TextButton bBack = new TextButton("Back",getSkin());
            bBack.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                }
            });

            Table backTable= new Table();
            backTable.defaults().expand().pad(50);
            backTable.setFillParent(true);
            backTable.add(bBack);
            getStage().addActor(backTable);
        }




    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.
        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        getBatch().begin();
        getBatch().draw(currentFrame, getDisplayMode().width/3, getDisplayMode().height/2);
        getFont().getData().setScale(4); //Changes font size.
        getFont().draw(getBatch(), "The button doesnt work! :D", getVirtualWidth()*35/100, getVirtualHeight()*85/100);

        getBatch().end();

        getStage().act();
        getStage().draw();
    }

        @Override
        public void resize(int width, int height) {
            getViewport().update(width, height, true);
        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void hide() {

        }

        @Override
        public void dispose() {
            getStage().dispose();
            getBackgroundTexture().dispose();
        }
}
