package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
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


    static Hashtable<String, Hashtable<String, String>> inventory;

    private Table descriptionTable;


    public ShopScreen(final TurnQuest game) {

        this.game = game;
        game.setStage(new Stage(getViewport()));
        game.setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        // Load inventory
        inventory = new Hashtable<>();
        readShopItems();

        // Create row of labels
        Table firstTable = new Table();
        setFirstRow(firstTable);


        // Create the table to hold the items
        Table itemTable = new Table(game.getSkin());
        setItemTable(itemTable);


        // Create a scroll pane to hold the item table
        ScrollPane scrollPane = new ScrollPane(itemTable, game.getSkin());
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSmoothScrolling(true);


        // Create stats desription table
        this.descriptionTable = new Table(game.getSkin());  //this will be the table for the description of the stats of each item, after being clicked
        Label label = new Label("Click on the image to show statistics of the item", game.getSkin());
        label.setWrap(true);
        label.setAlignment(Align.center);
        this.descriptionTable.add(label).width(350f).top();

        // Create right table
        Table rightTable = new Table();
        TextButton backButton = createBackButton();
        rightTable.add(this.descriptionTable).expand().height(150f).top().padTop(300f);
        rightTable.row();
        rightTable.add(backButton).padBottom(100f);


        // Create root table
        Table rootTable = new Table(game.getSkin());

        rootTable.row();

        rootTable.setFillParent(true);
        rootTable.add(firstTable).left().padLeft(200f);//.padRight(30f);
        rootTable.add(new Label("Statistics", game.getSkin())).expandX().align(Align.center).colspan(5);
        rootTable.row();
        rootTable.add(scrollPane).left().padLeft(200f);//.padRight(30f);
        rootTable.add(rightTable).center().expand().fill();

        rootTable.setDebug(true);

        game.getStage().addActor(rootTable);
    }

    private void setFirstRow(Table firstTable)
    {
        firstTable.defaults().pad(30).width(CellWidth).height(CellHeight);
        firstTable.columnDefaults(0).align(Align.center).width(CellWidth);
        firstTable.columnDefaults(1).width(CellWidth);
        firstTable.columnDefaults(2).padLeft(50).padRight((float) CellWidth / 2).width(CellWidth);
        firstTable.columnDefaults(3).padLeft(50).padRight((float) CellWidth / 2).width(CellWidth);


        firstTable.add(new Label("Price", game.getSkin()));
        firstTable.add(new Label("Name", game.getSkin()));
        firstTable.add(new Label("Image", game.getSkin()));
        firstTable.add(new Label("Buy", game.getSkin()));
        firstTable.add(new Label("Sell", game.getSkin()));
//        firstTable.add(backButton).width(200f).pad(50f);
    }

    public TextButton createBackButton()
    {
        TextButton backButton = new TextButton("Return", game.getSkin());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        return backButton;
    }

    private void setItemTable(Table itemTable)
    {
        itemTable.defaults().pad(30).width(CellWidth).height(CellHeight);
        itemTable.columnDefaults(0).align(Align.center).width(CellWidth);
        itemTable.columnDefaults(1).width(CellWidth);
        itemTable.columnDefaults(2).width(CellWidth);
        itemTable.columnDefaults(3).padLeft(50).padRight((float) CellWidth / 2).width(CellWidth);
        itemTable.columnDefaults(4).padLeft(50).padRight((float) CellWidth / 2).width(CellWidth);

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

            Label priceLabel = new Label(price, game.getSkin());
            Label nameLabel = new Label(name, game.getSkin());

            // Load item texture
            Texture itemTexture = new Texture(Gdx.files.internal(imagePath));
            double aspectRatio = (double) itemTexture.getWidth() / itemTexture.getHeight();
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
            TextButton buyButton = new TextButton("Buy", game.getSkin());
            TextButton sellButton = new TextButton("Sell", game.getSkin());

            // Add item components to the item table
            itemTable.add(priceLabel);
            itemTable.add(nameLabel);
            itemTable.add(itemButton);
            itemTable.add(buyButton);
            itemTable.add(sellButton);
            itemTable.row();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(game.getStage());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1);

        game.getBatch().begin();
        game.getBatch().draw(game.getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        TurnQuest.getViewport().update(width, height, true);
    }

    private void showStats(String name, String price, String attack, String defence)
    {
        this.descriptionTable.clear();
        this.descriptionTable.add(new Label(name, game.getSkin())).row();
        this.descriptionTable.add(new Label("Price " + price, game.getSkin())).row();
        this.descriptionTable.add(new Label("Attack " + attack, game.getSkin())).row();
        this.descriptionTable.add(new Label("Defence " + defence, game.getSkin()));
    }

    private void readShopItems()
    {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("../shop.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            final JSONArray inventory = (JSONArray) obj;

            for (Object thing : inventory) {
                this.parseInventory( (JSONObject) thing );
            }

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
        game.getStage().dispose();
    }
}