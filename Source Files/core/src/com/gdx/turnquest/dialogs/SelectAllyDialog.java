package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Clan;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.screens.BossScreen;
import com.gdx.turnquest.screens.ClanScreen;
import com.gdx.turnquest.utils.ClanManager;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.hasInternetConnection;

/**
 * This class is responsible for showing a dialog to select an ally.
 * It reads the name of the ally from the text field.
 * It checks if the ally exists and if the player is in the same clan.
 * If so, they can battle together.
 * If not, it shows an error.
 * This class is used in the BossScreen.
 *
 * @author Pablo
 */
public class SelectAllyDialog extends Dialog {

    private final TurnQuest game;
    private final TextField allyNameField;
    private String allyName;
    private final PlayerManager playerManager = new PlayerManager();
    private final ClanManager clanManager = new ClanManager();
    private final Label errorLabel;
    private final Player player;

    public SelectAllyDialog(String title, String text, Skin skin, TurnQuest game) throws IOException {
        super(title, skin);
        this.game = game;
        this.allyNameField = new TextField("", skin);
        player = game.getCurrentPlayer();

        getContentTable().defaults().expand().pad(10);
        getContentTable().add("Ally name:");
        getContentTable().add(allyNameField).width(400).row();

        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1); // set the color to red
        getContentTable().add(errorLabel).colspan(2);

        button("Select", true);
        button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        // Handle the result of the dialog
        boolean result = (boolean) object;

        if (result) {
            // Check credentials
            allyName = allyNameField.getText();

            // If correct, change screen
            // Check if the credentials are valid
            if (!hasInternetConnection()) {
                errorLabel.setText("Connection Error: Could not connect to the server.");
            } else {
                if (!playerManager.checkPlayerName(allyName)) {
                    // If the name does not exist, show an error
                    errorLabel.setText("Invalid player name, it does not exist.");
                } else {
                    Player ally = playerManager.getPlayer(allyName);
                    if (!player.getClanName().equalsIgnoreCase(ally.getClanName())) {
                        errorLabel.setText("Error: Player not in your clan.");
                    } else {
                        game.getCurrentPlayer().decreaseEnergy();
                        game.pushScreen(new BossScreen(game, ally));
                    }

                    hide();
                }
            }
        }
        else super.hide();
    }

    @Override
    public void hide() {
        Player ally = null;
        if (playerManager.checkPlayerName(allyName)) {
            ally = playerManager.getPlayer(allyName);
        }

        // Only hide the dialog if the credentials are valid or the cancel button is clicked
        if (ally != null && player.getClanName().equalsIgnoreCase(ally.getClanName())) {
            super.hide();
        }
    }

    @Override
    public float getPrefWidth() {
        // Set the preferred width of the dialog
        return 800f;
    }

    @Override
    public float getPrefHeight() {
        // Set the preferred height of the dialog
        return 500f;
    }
}