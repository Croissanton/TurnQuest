package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Clan;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.screens.ClanScreen;
import com.gdx.turnquest.utils.ClanManager;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.hasInternetConnection;

/**
 * A class to modify the json file to leave a clan:
 * leave the clan that you are already in.
 *
 * @author Pablo
 * @author Cristian
 */
public class LeaveClanDialog extends Dialog {

    private final TurnQuest game;
    private final ClanManager clanManager = new ClanManager();
    private final Label errorLabel;
    private final Player player;

    public LeaveClanDialog(String title, String message, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;
        player = game.getCurrentPlayer();
        text(message);

        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1); // set the color to red
        getContentTable().add(errorLabel).colspan(2);

        button("Yes", true);
        button("No", false);
    }

    @Override
    protected void result(Object object) {
        // Handle the result of the dialog
        boolean result = (boolean) object;

        if (result) {
            if (!hasInternetConnection()) {
                errorLabel.setText("Connection Error: Could not connect to the server.");
            } else {
                if (!clanManager.checkClanName(player.getClanName())) {
                    errorLabel.setText("Invalid clan name.");
                } else {
                    Clan clan = clanManager.getClan(player.getClanName());
                    clan.removeMember(player.getPlayerName());
                    player.setClanName("");
                    try {
                        new PlayerManager().savePlayer(player);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    hide();
                    game.popScreen();
                }
            }
        }
        else super.hide();
    }

    @Override
    public void hide() {
        // Only hide the dialog if the credentials are valid or the cancel button is clicked
        if (clanManager.checkClanName(player.getClanName())) {
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