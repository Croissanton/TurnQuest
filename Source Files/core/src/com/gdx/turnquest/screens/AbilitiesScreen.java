package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;

import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.entities.Player.*;

public class AbilitiesScreen implements Screen {

    final TurnQuest game;

    // set all buttons to not clicked, need to be global
    boolean clicked1 = false;
    boolean clicked2 = false;
    boolean clicked3 = false;
    boolean clicked4 = false;

    // times each abilitie has been upgraded
    int times1 = -1;
    int times2 = -1;
    int times3 = -1;
    int times4 = -1;

    public AbilitiesScreen (final TurnQuest game) {
        this.game = game;

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        setStage(new Stage(getViewport()));

        // tooltip that will be shown when hovering buttons when locked
        Label tt1 = new Label("Your next attack will be a critic one.", getSkin());
        Label tt2 = new Label("What r u searching 4?", getSkin());
        Label tt3 = new Label("What r u searching 4?", getSkin());
        Label tt4 = new Label("What r u searching 4?", getSkin());

        // tooltip that will be shown when hovering buttons when unlocked
        Label tt1u = new Label("Increase the critical attack damage by 0.5% each upgrade: " + times1 * 0.5, getSkin());
        Label tt2u = new Label("What r u searching 4? unlocked", getSkin());
        Label tt3u = new Label("What r u searching 4? unlocked", getSkin());
        Label tt4u = new Label("What r u searching 4? unlocked", getSkin());

        // set position
        tt1.setPosition(getVirtualWidth() * 38f / 100f, getVirtualHeight() * 70f / 100f);
        tt2.setPosition(getVirtualWidth() * 43f / 100f, getVirtualHeight() * 70f / 100f);
        tt3.setPosition(getVirtualWidth() * 43f / 100f, getVirtualHeight() * 70f / 100f);
        tt4.setPosition(getVirtualWidth() * 43f / 100f, getVirtualHeight() * 70f / 100f);
        tt1u.setPosition(getVirtualWidth() * 30f / 100f, getVirtualHeight() * 70f / 100f);
        tt2u.setPosition(getVirtualWidth() * 43f / 100f, getVirtualHeight() * 70f / 100f);
        tt3u.setPosition(getVirtualWidth() * 43f / 100f, getVirtualHeight() * 70f / 100f);
        tt4u.setPosition(getVirtualWidth() * 43f / 100f, getVirtualHeight() * 70f / 100f);

        // set to not be shown while not  hovering
        tt1.setVisible(false);
        tt2.setVisible(false);
        tt3.setVisible(false);
        tt4.setVisible(false);
        tt1u.setVisible(false);
        tt2u.setVisible(false);
        tt3u.setVisible(false);
        tt4u.setVisible(false);

        // abilities buttons
        TextButton bAb1 = new TextButton("Critical", getSkin());
        TextButton bAb2 = new TextButton("Ab2", getSkin());
        TextButton bAb3 = new TextButton("Ab3", getSkin());
        TextButton bAb4 = new TextButton("Ab4", getSkin());

        // set color to locked
        bAb1.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb2.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb3.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb4.setColor(0.3f, 0.7f, 0.8f, 0.5f);

        // Abilities table
        Table abilitiesTable = new Table();
        abilitiesTable.defaults().size(getVirtualWidth() * 12f / 100f, getVirtualHeight() * 10f / 100f).pad(20);
        abilitiesTable.setFillParent(true);

        // order the buttons of the table
        abilitiesTable.add(bAb1);
        abilitiesTable.add(bAb2).row();
        abilitiesTable.add(bAb3);
        abilitiesTable.add(bAb4).row();

        abilitiesTable.padTop(100f); // add some padding at the top

        getStage().addActor(abilitiesTable);

        bAb1.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked1) {
                    tt1u.setVisible(true);
                } else {
                    tt1.setVisible(true);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
                tt1.setVisible(false);
                tt1u.setVisible(false);
            }
        });

        // show it
        getStage().addActor(tt1);
        getStage().addActor(tt1u);

        bAb1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb1.setColor(0.3f, 0.7f, 0.8f, 1);
                clicked1 = true;
                times1++;
                tt1u.setText("Increase the critical attack damage by 0.5% each upgrade: " + times1 * 0.5 + "%");
            }
        });

        bAb2.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked2) {
                    tt2u.setVisible(true);
                } else {
                    tt2.setVisible(true);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
                tt2.setVisible(false);
                tt2u.setVisible(false);
            }
        });

        // show it
        getStage().addActor(tt2);
        getStage().addActor(tt2u);

        bAb2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb2.setColor(0.3f, 0.7f, 0.8f, 1);
                times2++;
            }
        });

        bAb3.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked3) {
                    tt3u.setVisible(true);
                } else {
                    tt3.setVisible(true);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
                tt3.setVisible(false);
                tt3u.setVisible(false);
            }
        });

        // show it
        getStage().addActor(tt3);
        getStage().addActor(tt3u);

        bAb3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb3.setColor(0.3f, 0.7f, 0.8f, 1);
                times3++;
            }
        });

        bAb4.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked4) {
                    tt4u.setVisible(true);
                } else {
                    tt4.setVisible(true);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
                tt4.setVisible(false);
                tt4u.setVisible(false);
            }
        });

        // show it
        getStage().addActor(tt4);
        getStage().addActor(tt4u);

        bAb4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // unlocked
                bAb4.setColor(0.3f, 0.7f, 0.8f, 1);
                times4++;
            }
        });

        // table buttons
        TextButton bReturn = new TextButton("Return", getSkin());
        TextButton bLeftArrow = new TextButton("<-", getSkin());
        TextButton bRightArrow = new TextButton("->", getSkin());

        // table for return
        Table table = new Table();
        // add some padding and expand each cell
        table.defaults().expand().pad(50);
        table.setFillParent(true);

        // order the buttons of the table
        table.add(bLeftArrow).left();
        table.add();
        table.add(bRightArrow).right();
        table.row();
        table.add();
        table.add(bReturn).bottom();

        getStage().addActor(table);

        // if an arrow is clicked, go to abilities screen
        bRightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });

        bLeftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });

        // return to GameScreen when pressed return button
        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        getViewport().apply();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        getBatch().setProjectionMatrix(getCamera().combined);

        getBatch().begin();
        getBatch().draw(getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        getFont().getData().setScale(4); //Changes font size.
        getFont().draw(getBatch(), "Abilities", getVirtualWidth() * 45f / 100f, getVirtualHeight() * 85f / 100f);
        getFont().draw(getBatch(), getCharacterClass(), getVirtualWidth() * 45f / 100f, getVirtualHeight() * 78f / 100f);
        getBatch().end();

        getStage().act();
        getStage().draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
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
