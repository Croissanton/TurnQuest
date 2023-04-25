package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.screens.GameScreen;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.gdx.turnquest.TurnQuest.hasInternetConnection;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


public class LoginDialog extends Dialog {
    private final TextField usernameField;
    private final TextField passwordField;
    private final Label errorLabel;
    private final TurnQuest game;

    public LoginDialog(String title, Runnable runnable, Skin skin, TurnQuest game) {
        super(title, skin);
        this.game = game;

        getContentTable().defaults().expand().pad(10);
        getContentTable().add("Username:");
        usernameField = new TextField("", skin);
        getContentTable().add(usernameField).width(400);
        getContentTable().row();
        getContentTable().add("Password:");
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        getContentTable().add(passwordField).width(400);
        getContentTable().row();
        errorLabel = new Label("", skin);
        errorLabel.setColor(1, 0, 0, 1); // set the color to red
        getContentTable().add(errorLabel).colspan(2);
        button("Login", true);
        button("Cancel", false);
    }

    @Override
    protected void result(Object object) {
        // Handle the result of the dialog
        boolean result = (boolean) object;
        if (result) {
            // Check credentials
            String username = usernameField.getText();
            String password = passwordField.getText();
            // If correct, change screen
            // Check if the credentials are valid
            if (!hasInternetConnection()) {
                errorLabel.setText("Connection Error: Could not connect to the server.");
            } else {
                if (!isValidCredentials(username, password)) {
                    // If the credentials are not valid, display an error message
                    errorLabel.setText("Invalid username or password.");
                } else {
                    updateLoginTimer(username,"loginTime");
                    // If the credentials are valid, check loginCounter
                    if (!checkLoginCount(username)){
                        errorLabel.setText("No logins left");/*TODO: Dialog goes back to main screen without user interaction
                                                                         has to be fixed*/
                    }
                    else{

                        hide();
                        game.setScreen(new GameScreen(game));
                    }

                }
            }
        }
        else super.hide();
    }

    @Override
    public void hide() {
        // Only hide the dialog if the credentials are valid, this makes it so  that the dialog is not closed whenever a button is pressed but when it needs to.
        if (isValidCredentials(usernameField.getText(), passwordField.getText())) {
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

    // Helper method to check if the credentials are valid
    private boolean isValidCredentials(String username, String password) {
        try {
            FileHandle file = Gdx.files.internal("../Data/" + "players.json"); /*TODO: we need to change this to create a file for each player
                                                                                    , instead of having 1 file for all players to ease the database*/
            String json = file.readString();
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(json);
            JsonValue players = root.get("players");
            for (JsonValue player : players) {
                if(username.equals(player.getString("username"))){
                    if (hashPassword(password).equals(player.getString("password"))) {
                        return true;
                    }
                    else return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    protected static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Method that checks the number of logins and updates the value of the loginCounter after succesfull login
    private boolean checkLoginCount(String username) {
        JSONParser parser = new JSONParser();
        try {

            JSONObject playerData = (JSONObject) parser.parse(new FileReader("../Data/" + username +".json"));
            long loginCount =(long)playerData.get("loginCount");
            if (loginCount < 5) {
                loginCount = loginCount + 1;
                playerData.put("loginCount", loginCount);
                // Write the updated JSONObject back to the JSON file
                FileWriter fileWriter = new FileWriter("../Data/" + username +".json");
                fileWriter.write(playerData.toJSONString());
                fileWriter.flush();
                fileWriter.close();

                return true;
            } else {
                return false;
            }

        }catch (IOException | ParseException e) {
        e.printStackTrace();
        }
        return false;
    }

    private float getTimeDiff(String username,String timetype) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject playerData = (JSONObject) parser.parse(new FileReader("../Data/" + username + ".json"));

            //Get the saved first login time as a Calender Object
            String loginTime = (String) playerData.get(timetype);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                Date firstLoginTime = formatter.parse(loginTime);
                Calendar FirstLoginTime = Calendar.getInstance();
                // set the calendar object to the given time
                FirstLoginTime.setTime(firstLoginTime);
                long diff = System.currentTimeMillis()-FirstLoginTime.getTimeInMillis();
                //Time differnce as a float in hours
                float diffHours=diff / (60.0f * 60.0f * 1000.0f);

                return diffHours;

            } catch (java.text.ParseException e) {
                return 0;
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private boolean updateLoginTimer(String username,String timetype) {

        JSONParser parser = new JSONParser();
        try {
            JSONObject playerData = (JSONObject) parser.parse(new FileReader("../Data/" + username + ".json"));
            if (getTimeDiff(username,timetype)>24){
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String formattedDateTime = now.format(formatter);

                playerData.put("loginCount", 0);
                playerData.put("loginTimer", formattedDateTime);

                // Write the updated JSONObject back to the JSON file
                FileWriter fileWriter = new FileWriter("../Data/" + username +".json");
                fileWriter.write(playerData.toJSONString());
                fileWriter.flush();
                fileWriter.close();

                return true;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
