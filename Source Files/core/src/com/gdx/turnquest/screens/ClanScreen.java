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
import com.gdx.turnquest.dialogs.*;
import com.gdx.turnquest.entities.Player;

import static com.gdx.turnquest.TurnQuest.*;

public class ClanScreen extends BaseScreen {

    public ClanScreen(final TurnQuest game) {
        super(game);
    }

    @Override
    public void show() {
        Assets.loadFor(ClanScreen.class);
        Assets.ASSET_MANAGER.finishLoading();
        Assets.setBackgroundTexture(new Texture(Gdx.files.internal(Assets.FOREST_BACKGROUND_PNG)));
        game.setStage(new Stage(getViewport()));
        game.getStage().addActor(createUIComponents());

        // apply
        getViewport().apply();
        super.show();
    }

    @Override
    public Table createUIComponents() {
        Player player = game.getCurrentPlayer();

        // create or delete clan button
        TextButton bCreateOrDelete = new TextButton("Create Clan", Assets.getSkin());

        // join or leave clan button
        TextButton bJoinOrLeave = new TextButton("Join Clan", Assets.getSkin());

        // return button
        TextButton bReturn = new TextButton("Return", Assets.getSkin());

        // if the player is in a clan, change create clan to delete clan and join clan to leave clan
        if (!player.getClanName().isEmpty()) {
            bCreateOrDelete.setText("Delete Clan");
            bJoinOrLeave.setText("Leave Clan");
        }

        //create the table
        Table table = new Table();
        table.defaults();
        table.setFillParent(true);

        // add buttons to the table
        table.add(bCreateOrDelete).row();
        table.add(bJoinOrLeave).row();
        table.add(bReturn).bottom();

        // create or delete clan button
        bCreateOrDelete.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // if the player is in a clan, change create clan to delete clan
                if (!player.getClanName().isEmpty()) {
                    showDeleteClanDialog();
                } else {
                    showCreateClanDialog();
                }
            }
        });

        // join or leave clan button
        bCreateOrDelete.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // if the player is in a clan, change join clan to leave clan
                if (!player.getClanName().isEmpty()) {
                    showLeaveClanDialog();
                } else {
                    showJoinClanDialog();
                }
            }
        });

        // return to GameScreen when pressed return button
        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popScreen();
            }
        });

        return table;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        Assets.getFont().draw(game.getBatch(), "Clan", getVirtualWidth()*.48f, getVirtualHeight()*.85f);
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleKeyboardInput();
    }

    private void showCreateClanDialog() {
        CreateClanDialog dialog = new CreateClanDialog("Clan Creation", Assets.getSkin(), game);
        dialog.show(game.getStage());
    }

    private void showJoinClanDialog() {
        JoinClanDialog dialog = new JoinClanDialog("Clan Joining", Assets.getSkin(), game);
        dialog.show(game.getStage());
    }

    private void showDeleteClanDialog() {
        DeleteClanDialog dialog = new DeleteClanDialog("Clan Removal", "Are you sure you want to delete the clan?", Assets.getSkin(), game);
        dialog.show(game.getStage());
    }

    private void showLeaveClanDialog() {
        LeaveClanDialog dialog = new LeaveClanDialog("Clan Leaving", "Are you sure you want to leave the clan", Assets.getSkin(), game);
        dialog.show(game.getStage());
    }
}