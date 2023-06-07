package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.entities.Player;

import java.util.Objects;

import static com.gdx.turnquest.TurnQuest.*;

/**
 * This class is the player screen.
 * It shows the player's name, character class, clan, level and experience.
 * It also shows the character's animated sprite.
 * It also shows a button to go back to the main menu.
 *
 * @author Ignacy
 */
public class PlayerScreen extends BaseScreen {

    private Player player;
    private ProgressBar levelProgress;
    private Label playerNameLabel;
    private Label characterClassNameLabel;
    private Label playerStatLabel;
    private Label playerClanLabel;
    private Image characterImage;
    private Label playerLevelLabel;

    public PlayerScreen(final TurnQuest game) {
        super(game);
        player = game.getCurrentPlayer();
    }

    @Override
    public Table createUIComponents() {
        Table table = new Table(Assets.getSkin());
        table.setFillParent(true);

        BitmapFont font = Assets.getSubtitleFont();
        Label.LabelStyle style = new Label.LabelStyle(Assets.getSkin().get(Label.LabelStyle.class));
        style.font = font;
        // Player name label
        playerNameLabel = new Label(player.getPlayerName(), style);
        playerNameLabel.setFontScale(2f);
        playerNameLabel.setAlignment(Align.center);

        // Character class name label
        characterClassNameLabel = new Label(player.getCharacterClass(), Assets.getSkin());
        characterClassNameLabel.setFontScale(1.5f);
        characterClassNameLabel.setAlignment(Align.center);

        // Player clan label
        String playerClan = player.getClanName();
        if (Objects.equals(playerClan, "")) {
            playerClan = "not in a clan!";
        }
        playerClanLabel = new Label("Clan: " + playerClan, Assets.getSkin());
        playerClanLabel.setFontScale(1.5f);
        playerClanLabel.setAlignment(Align.center);
        // Character sprite
        Texture characterTexture = Assets.getCharacterTexture(player.getCharacterClass());
        float cutOffTopRatio = 0.5f; // The ratio of the image height you want to remove from the top
        float cutOffSideRatio = 0.4f; // The ratio of the image width you want to remove from the sides
        float sourceX = characterTexture.getWidth() * cutOffSideRatio;
        float sourceWidth = characterTexture.getWidth() * (1 - cutOffSideRatio - cutOffSideRatio);
        float sourceY = characterTexture.getHeight() * cutOffTopRatio;
        float sourceHeight = characterTexture.getHeight() * (1 - cutOffTopRatio);
        TextureRegion characterRegion = new TextureRegion(characterTexture, (int) sourceX, (int) sourceY, (int) sourceWidth, (int) sourceHeight);
        characterImage = new Image(characterRegion);
        float originalWidth = characterImage.getWidth() * (1-cutOffSideRatio - cutOffSideRatio);
        float originalHeight = characterImage.getHeight() * (1-cutOffTopRatio);
        characterImage.setBounds(0, 0, originalWidth, originalHeight);
        Actor characterActor = characterImage;

        // Level and XP progress bar
        levelProgress = new ProgressBar(0, 100, 1, false, Assets.getSkin());
        levelProgress.setValue((float) (player.getExp() * 100) / player.expNeeded());

        playerLevelLabel = new Label("Level: " + player.getLevel(), Assets.getSkin());
        playerLevelLabel.setFontScale(1.5f);
        playerLevelLabel.setAlignment(Align.center);

        // Player stat label
        playerStatLabel = new Label("", Assets.getSkin());
        playerStatLabel.setFontScale(1.2f);
        playerStatLabel.setAlignment(Align.center);
        updatePlayerStatLabel();

        TextButton bReturn = new TextButton("Return", Assets.getSkin());
        TextButton bInventory = new TextButton("Inventory", Assets.getSkin());
        TextButton bAbilities = new TextButton("Abilities", Assets.getSkin());

        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popScreen();
            }
        });

        bInventory.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new InventoryScreen(game));
            }
        });

        bAbilities.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new AbilitiesScreen(game));
            }
        });
        table.defaults().expand().size(getVirtualWidth() *0.15f, getVirtualHeight() *.10f);
        // Table layout
        table.row();
        table.add(playerNameLabel).pad(20).center().colspan(3);
        table.row();
        table.add(bInventory).pad(20).center();

        Table levelTable = new Table();
        levelTable.add(playerLevelLabel).pad(20).center().row();
        levelTable.add(levelProgress).width(400).pad(20).center().row();
        table.add(levelTable).pad(20).center();

        table.add(bAbilities).pad(20).center();
        table.row();

        Table characterTable = new Table();
        characterTable.add(characterActor).pad(20).fill().expand().height(Value.percentHeight(0.8f, characterTable)).row();
        characterTable.add(characterClassNameLabel).pad(20).center().height(Value.percentHeight(0.2f, characterTable)).row();
        table.add(characterTable).expandY().fillY().center().height(Value.percentHeight(0.5f, table));

        table.add(playerStatLabel).expandY().fillY().center().height(Value.percentHeight(0.5f, table));

        Table clanTable = new Table();
        clanTable.add(playerClanLabel).pad(20).center().row();
        table.add(clanTable).expandY().fillY().center();

        table.row();
        table.defaults().expand().size(getVirtualWidth() *0.15f, getVirtualHeight() *.10f);
        table.add(bReturn).pad(20).center().colspan(3);

        return table;
    }

    private void updatePlayerStatLabel() {
        StringBuilder statsText = new StringBuilder();
        for (String stat : player.getStats().keys()) {
            statsText.append(stat).append(": ").append(player.getStats().get(stat)).append("\n");
        }
        playerStatLabel.setText(statsText.toString());
    }

    @Override
    public void show() {
        Assets.loadFor(PlayerScreen.class);
        Assets.loadCharacterTexture(player.getCharacterClass());
        Assets.ASSET_MANAGER.finishLoading();
        Assets.setBackgroundTexture(new Texture(Gdx.files.internal(Assets.FOREST_BACKGROUND_PNG)));
        game.setStage(new Stage(getViewport()));
        game.getStage().addActor(createUIComponents());
        //add a tutorial button to the top left of the screen
        game.getStage().addActor(tutorialButton("player"));
        getViewport().apply();
        super.show();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1);
        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, getVirtualWidth(), getVirtualHeight());
        game.getBatch().end();
        game.getStage().act();
        game.getStage().draw();
        handleKeyboardInput();
    }
}