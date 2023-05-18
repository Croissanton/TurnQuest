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

public class JoinClanDialog extends Dialog {

    private final TurnQuest game;
    private final TextField clanNameField;
    private String clanName;
    private final ClanManager clanManager = new ClanManager();
    private final Label errorLabel;
    private final Player player;

    public JoinClanDialog(String title, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;
        this.clanNameField = new TextField("", skin);
        player = game.getCurrentPlayer();

        getContentTable().defaults().expand().pad(10);
        getContentTable().add("Clan name:");
        getContentTable().add(clanNameField).width(400);

        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1); // set the color to red
        getContentTable().add(errorLabel).colspan(2);

        button("Join", true);
        button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        // Handle the result of the dialog
        boolean result = (boolean) object;

        if (result) {
            // Check credentials
            clanName = clanNameField.getText();

            // If correct, change screen
            // Check if the credentials are valid
            if (!hasInternetConnection()) {
                errorLabel.setText("Connection Error: Could not connect to the server.");
            } else {
                if (!clanManager.checkClanName(clanName)) {
                    // If the name exists, show an error
                    errorLabel.setText("Invalid clan name, it does not exist.");
                } else {
                    Clan clan = clanManager.getClan(clanName);
                    player.setClanName(clanName);
                    clan.addMember(player.getPlayerName());
                    try {
                        new PlayerManager().savePlayer(player);
                        clanManager.save(clan);
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
        if (clanName != null && clanManager.checkClanName(clanName)) {
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