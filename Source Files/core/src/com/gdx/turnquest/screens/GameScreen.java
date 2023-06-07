package com.gdx.turnquest.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.dialogs.ConfirmationDialog;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.*;

/**
 * This class is responsible for showing different options to the player.
 * There are buttons that allow the player to go to the player screen,
 * the inventory screen, the map screen, the shop screen or return to the main menu.
 * It also shows the player's character portrait and the energy.
 * 
 * @author Pablo
 * @author Ignacy
 */
public class GameScreen extends BaseScreen {

    public GameScreen(final TurnQuest game) {
        super(game);
    }

    @Override
    public void show() {
        Assets.loadFor(GameScreen.class);
        Assets.loadCharacterPortrait(game.getCurrentPlayer().getCharacterClass());
        Assets.ASSET_MANAGER.finishLoading();
        game.setStage(new Stage(getViewport()));
        game.getStage().addActor(createUIComponents());
        //add a tutorial button to the top left of the screen
        game.getStage().addActor(tutorialButton("game"));
        getViewport().apply();
        super.show();
    }

    public Table createUIComponents() {
        // create the table
        Table table = new Table();
        table.defaults().expand().size(getVirtualWidth() *0.15f, getVirtualHeight() *.10f);
        table.setFillParent(true);

        // options button
        TextButton bOptions = new TextButton("Options", Assets.getSkin());
        table.add(bOptions).right();

        // add another column
        table.add();
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(Assets.getSkin().get(TextButton.TextButtonStyle.class));
        Texture characterPortrait = Assets.getCharacterPortrait(game.getCurrentPlayer().getCharacterClass());
        style.imageUp = new TextureRegionDrawable(new TextureRegion(characterPortrait));

        float buttonSize = getVirtualWidth() * 0.10f;
        ImageButton bPlayerScreen = new ImageButton(style);
        bPlayerScreen.setSize(buttonSize, buttonSize);
        bPlayerScreen.getImageCell().expand().fill().center();
        bPlayerScreen.getImage().setScaling(Scaling.fit);

        table.defaults(); // Reset the table's default cell settings
        table.add(bPlayerScreen).size(buttonSize, buttonSize).left(); // Set the cell size specifically for the ImageButton
        table.defaults().expand().size(getVirtualWidth() *0.15f, getVirtualHeight() *.10f);
        // add another row
        table.row();

        // add another column
        table.add();

        // play button
        TextButton bPlay = new TextButton("Play", Assets.getSkin());
        table.add(bPlay).center();

        // add another column
        table.add();

        // add another row
        table.row();

        // abilities button
        TextButton bClan = new TextButton("Clan", Assets.getSkin());
        table.add(bClan).right();

        //return button
        TextButton bReturn = new TextButton("Return", Assets.getSkin());
        table.add(bReturn).bottom();

        // shop button
        TextButton bShop = new TextButton("Shop", Assets.getSkin());
        table.add(bShop).left();


        table.pad(20);

        bPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new MapScreen(game));
                //game.pushScreen(new CharactersScreen(game));
            }
        });

        bPlayerScreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new PlayerScreen(game));
            }
        });


        bClan.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new ClanScreen(game));
            }
        });

        bShop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new ShopScreen(game));
            }
        });

        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showQuitConfirmationDialog();
            }
        });

        bOptions.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.showPreferencesDialog();
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
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, getVirtualWidth(), getVirtualHeight());
        game.getBatch().draw(new Texture("images/energy.png"), getVirtualWidth() * 0.40f, getVirtualHeight() * 0.8f, getVirtualWidth() * 0.05f , getVirtualHeight() * 0.1f);
        Assets.getFont().draw(game.getBatch(), "Game Menu", getVirtualWidth() * 0.42f, getVirtualWidth() * 0.77f);
        Assets.getTitleFont().draw(game.getBatch(), "     (" + game.getCurrentPlayer().getEnergy() + "/5)", getVirtualWidth() * 0.3f, getVirtualWidth() * 0.5f);
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleKeyboardInput();
    }

    private void showQuitConfirmationDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog("Quit", "Are you sure you want to return to main menu? \n" +
                "You will have to enter your credentials again.", ()->{
            try {
                new PlayerManager().savePlayer(game.getCurrentPlayer());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            game.popScreen();
        }, Assets.getSkin());
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(game.getStage());
    }
}