package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import static com.gdx.turnquest.TurnQuest.*;

public class InventoryScreen implements Screen {
    final TurnQuest game;

    public InventoryScreen(final TurnQuest game) {
        this.game = game;

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        game.setStage(new Stage(getViewport()));

        // table buttons
        TextButton bReturn = new TextButton("Return", getSkin());
        TextButton bLeftArrow = new TextButton("<-", getSkin());
        TextButton bRightArrow = new TextButton("->", getSkin());
        TextButton bInventory = new TextButton("Inventory", getSkin());

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
        table.add(bInventory);

        game.getStage().addActor(table);

        // if an arrow is clicked, go to abilities screen
        bRightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AbilitiesScreen(game));
            }
        });

        bLeftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AbilitiesScreen(game));
            }
        });

        // return to GameScreen when pressed return button
        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        bInventory.addListener(new ClickListener() {
        @Override
        public void clicked (InputEvent event,float x, float y){
            ReadPlayerInventory();
        }
    });
}


        @Override
        public void show () {
            Gdx.input.setInputProcessor(game.getStage());

        }

        @Override
        public void render ( float delta){
            ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

            getCamera().update();
            getBatch().setProjectionMatrix(getCamera().combined);

            getBatch().begin();
            getBatch().draw(getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
            getFont().getData().setScale(4); //Changes font size.
            getFont().draw(getBatch(), "Inventory", getVirtualWidth() * 0.45f, getVirtualHeight() * 0.85f);
            getBatch().end();

            game.getStage().act();
            game.getStage().draw();

            if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
                toggleFullscreen();
            }
        }

        @Override
        public void resize ( int width, int height){
            getViewport().update(width, height, true);
        }

        @Override
        public void pause () {

        }

        @Override
        public void resume () {

        }

        @Override
        public void hide () {

        }

        @Override
        public void dispose () {
            game.getStage().dispose();
        }


        private void ReadPlayerInventory () {


            // Load the players.json file into a FileHandle object
            FileHandle file = Gdx.files.internal("../Data/players.json");

            // Use a JsonReader object to parse the JSON data
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(file);

            // Get the inventory array for the specified player
            String playerName = getPlayer().getUsername();
            JsonValue playerData = root.get(playerName);
            JsonValue inventoryData = playerData.get("inventory");

            // Read the IDs of the items in the inventory array
            for (JsonValue itemData : inventoryData) {
                String itemId = itemData.asString();
                System.out.println("Player " + playerName + " owns item with ID " + itemId);
            }
        }
    }