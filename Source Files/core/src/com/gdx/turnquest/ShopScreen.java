package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static com.gdx.turnquest.TurnQuest.*;

public class ShopScreen implements Screen {
    final TurnQuest game;

    private static final int CellWidth=100;
    private static final int CellHeight=80;


    private static Hashtable<String, Hashtable<String, String>> inventory;


    public ShopScreen(final TurnQuest game) {
        this.game = game;

        setStage(new Stage(getViewport()));
        Gdx.input.setInputProcessor(getStage());

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        inventory = new Hashtable<>();
        readInventory();
        // Create the table to hold the items
        Table itemTable = new Table(getSkin());
        setItemTable(itemTable);

        ImageButton itemButton = null;

        // Create a scroll pane to hold the item table
        ScrollPane scrollPane = new ScrollPane(itemTable, getSkin());
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSmoothScrolling(true);

        // Add the scroll pane to the root table of the stage
        Table rootTable = new Table(getSkin());
        rootTable.setFillParent(true);
        rootTable.add(scrollPane).expand().fill();
        getStage().addActor(rootTable);

        // Create back button
        TextButton backButton = new TextButton("Back", getSkin());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });



//        itemButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//
//            }
//        });
        // Add back button to a separate table
        Table descriptionTable = new Table(getSkin());  //this will be the table for the description of the stats of each item, after being clicked
        Table backButtonTable = new Table(getSkin());
        backButtonTable.add(backButton).pad(40);
        // Add back button table to the root table
        rootTable.add(backButtonTable).top().left().expandX();
    }

    private void setItemTable(Table itemTable)
    {
        itemTable.defaults().pad(30).width(CellWidth).height(CellHeight);
        itemTable.columnDefaults(0).align(Align.center).width(CellWidth);
        itemTable.columnDefaults(1).width(CellWidth);
        itemTable.columnDefaults(2).padLeft(50).padRight(CellWidth / 2).width(CellWidth);
        itemTable.columnDefaults(3).padLeft(50).padRight(CellWidth / 2).width(CellWidth);

        //DEBUG
        itemTable.add("priceLabel");
        itemTable.add("itemImage");
        itemTable.add("buyButton");
        itemTable.add("sellButton");
        itemTable.row();

        addItems(itemTable);
    }

    private void addItems(Table itemTable)
    {
        // Add items to the table
        ImageButton itemButton = null;

        for (Map.Entry<String, Hashtable<String, String>> set : inventory.entrySet())
        {

            String name = set.getKey();
            String price = set.getValue().get("price");
            String attack = set.getValue().get("attack");
            String defence = set.getValue().get("defence");
            String imagePath = set.getValue().get("imagePath");

            // Create label for item price

            Label priceLabel = new Label(price, getSkin());

            // Load item texture
            Texture itemTexture = new Texture(Gdx.files.internal(imagePath));
            double aspectRatio = itemTexture.getWidth() / itemTexture.getHeight();
            int newWidth = (int) (aspectRatio * itemTexture.getWidth());
            TextureRegion itemRegion = new TextureRegion(itemTexture, newWidth, CellHeight);
            TextureRegionDrawable drawable = new TextureRegionDrawable(itemRegion);

            //create button with item image
            itemButton = new ImageButton(drawable);
            itemButton.setWidth(CellWidth);
            itemButton.setHeight(CellHeight);

            itemButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y){
                    showStats(name, price, attack, defence);
                }
            });

            // Create buy/sell buttons for item
            TextButton buyButton = new TextButton("Buy", getSkin());
            TextButton sellButton = new TextButton("Sell", getSkin());

            // Add item components to the item table
            itemTable.add(priceLabel);
            itemTable.add(itemButton);
            itemTable.add(buyButton);
            itemTable.add(sellButton);
            itemTable.row();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1);

        getBatch().begin();
        getBatch().draw(getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        getBatch().end();

        getStage().act();
        getStage().draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            TurnQuest.toggleFullscreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        TurnQuest.getViewport().update(width, height, true);
    }

    private void showStats(String name, String price, String attack, String defence)
    {

    }

    private void readInventory()
    {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("../shop.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            final JSONArray inventory = (JSONArray) obj;

            inventory.forEach( thing -> this.parseInventory( (JSONObject) thing ) );

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseInventory(JSONObject inventory)
    {
        String name = (String) inventory.get("name");
        String price = (String) inventory.get("price");
        String attack = (String) inventory.get("attack");
        String defence = (String) inventory.get("defence");
        String path = (String) inventory.get("image_path");
        Hashtable<String, String> stats = new Hashtable<>();
        stats.put("attack", attack);
        stats.put("defence", defence);
        stats.put("price", price);
        stats.put("imagePath", path);
        ShopScreen.inventory.put(name, stats);
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        getStage().dispose();
    }
}