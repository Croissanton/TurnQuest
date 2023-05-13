package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;

import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.TurnQuest.getVirtualHeight;

public class MapScreen extends BaseScreen {

    public MapScreen(final TurnQuest game) {
        super(game);
    }


    @Override
    public Table createUIComponents() {
        TextButton bEnemy = new TextButton("Enemy", Assets.getSkin());
        TextButton bBoss = new TextButton("Boss", Assets.getSkin());
        TextButton bReturn = new TextButton("Return", Assets.getSkin());

        final boolean[] boss = {false};

        bEnemy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CombatScreen(game, boss[0]));
            }
        });

        bBoss.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boss[0] = true;
                game.setScreen(new CombatScreen(game, boss[0]));
            }
        });

        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popScreen();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(bEnemy).center().padBottom(50f).row();
        table.add(bBoss).center().padBottom(50f).row();
        table.add(bReturn).center().padBottom(50f);

        table.padTop(100f); // add some padding at the top
        return table;
    }

    @Override
    public void show() {
        Assets.setBackgroundTexture(new Texture(Gdx.files.internal(Assets.FOREST_BACKGROUND_PNG)));
        game.setStage(new Stage(getViewport()));


        game.getStage().addActor(createUIComponents());

        getViewport().apply();
        super.show();
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, getVirtualWidth(), getVirtualHeight());
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleKeyboardInput();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
