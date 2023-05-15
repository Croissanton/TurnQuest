package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.screens.GameScreen;
import com.gdx.turnquest.utils.PlayerManager;
import com.gdx.turnquest.utils.UserManager;

import java.io.IOException;



import static com.gdx.turnquest.TurnQuest.hasInternetConnection;

public class LoginDialog extends Dialog {
    private final TextField usernameField;
    private final TextField passwordField;
    private final Label errorLabel;
    private final TurnQuest game;
    private final UserManager userManager = new UserManager();
    private String username;
    private String password;

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
            username = usernameField.getText();
            password = passwordField.getText();
            // If correct, change screen
            // Check if the credentials are valid
            if (!hasInternetConnection()) {
                errorLabel.setText("Connection Error: Could not connect to the server.");
            } else {
                if (!userManager.checkUser(username, password)) {
                    // If the credentials are not valid, display an error message
                    errorLabel.setText("Invalid username or password.");
                } else {

//                    updateTimer(username, "login");
                    PlayerManager playerManager;
                    try {
                        playerManager = new PlayerManager();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Player player = playerManager.getPlayer(username);
                    player.checkRefresh();
//                    if (!checkLoginCount(username)) {
//                        errorLabel.setText("No logins left");/*TODO: Dialog goes back to main screen without user interaction, fix this */

                        // If the credentials are valid, proceed with the login process
                        PlayerManager playerManager = new PlayerManager();
                        Player player = playerManager.getPlayer(username);

                        game.setCurrentPlayer(player);
                        hide();
                        game.pushScreen(new GameScreen(game));
                    }

            }
        }
        else super.hide();
    }

    @Override
    public void hide() {
        // Only hide the dialog if the credentials are valid or the cancel button is clicked
        if (username != null && password != null && userManager.checkUser(username, password)) {
            super.hide();
        } else if (username == null && password == null) {
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

//    private boolean checkLoginCount(String username) {
//        JSONParser parser = new JSONParser();
//        try {
//            JSONObject loginData = (JSONObject) parser.parse(new FileReader("../Data/users.json"));
//
//            JSONObject loginInfo = (JSONObject) loginData.get(username);
//            long loginCount = (long) loginInfo.get("loginCount");
//
//            if (loginCount < 5000) {   /*TODO: The Counter should be set to 5 for the actual game, this is just for testing */
//                loginCount++;
//                loginInfo.put("loginCount", loginCount);
//
//                // Write the updated JSONObject back to the JSON file
//                FileWriter fileWriter = new FileWriter("../Data/users.json");
//                fileWriter.write(loginData.toJSONString());
//                fileWriter.flush();
//                fileWriter.close();
//
//                return true;
//            } else {
//                return false;
//            }
//        } catch (IOException | ParseException ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }




//    private float getTimeDiff(String username,String timetype) {
//        JSONParser parser = new JSONParser();
//        try {
//            JSONObject Users = (JSONObject) parser.parse(new FileReader("../Data/users.json"));
//
//            JSONObject user = (JSONObject) Users.get(username);
//            //Get the saved first login time as a Calender Object
//            String fTime = (String) user.get(timetype+"Time");
//
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//            try {
//                Date firstTime = formatter.parse(fTime);
//                Calendar FirstTime = Calendar.getInstance();
//                // set the calendar object to the given time
//                FirstTime.setTime(firstTime);
//                long diff = System.currentTimeMillis()-FirstTime.getTimeInMillis();
//                //Time differnce as a float in hours
//
//                return diff / (60.0f * 60.0f * 1000.0f);
//
//            } catch (java.text.ParseException e) {
//                return 0;
//            }
//
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//    public void updateTimer(String username,String timetype) {
//
//        JSONParser parser = new JSONParser();
//        try {
//            JSONObject Users = (JSONObject) parser.parse(new FileReader("../Data/users.json"));
//
//            JSONObject user = (JSONObject) Users.get(username);
//            //Get the saved first login time as a Calender Object
//
//            if (getTimeDiff(username,timetype)>24){
//                LocalDateTime now = LocalDateTime.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//                String formattedDateTime = now.format(formatter);
//
//                user.put(timetype+"Time", formattedDateTime);
//                user.put(timetype+"Count", 0);
//
//                // Write the updated JSONObject back to the JSON file
//                FileWriter fileWriter = new FileWriter("../Data/users.json");
//                fileWriter.write(Users.toJSONString());
//                fileWriter.flush();
//                fileWriter.close();
//            }
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
//    }
}
