package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.dialogs.InformationDialog;
import com.gdx.turnquest.entities.Player;

import java.util.Hashtable;
import java.util.Map;

import static com.gdx.turnquest.TurnQuest.*;

public class ShopScreen implements Screen {
    final TurnQuest game;
    private static final int CellWidth=100;
    private static final int CellHeight=80;
    static Hashtable<String, Hashtable<String, String>> shopItems;

    public ShopScreen(final TurnQuest game) {

        this.game = game;
        game.setStage(new Stage(getViewport()));
        game.setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        // Load inventory
        shopItems = new Hashtable<>();
        readShopItems();

        // Create description of the item table
        Table firstTable = new Table();
        setFirstRow(firstTable);

        // Create the table to hold the items
        Table descriptionTable = new Table(game.getSkin());  //this will be the table for the description of the stats of each item, after being clicked
        Table itemTable = new Table(game.getSkin());
        setItemTable(itemTable, descriptionTable);

        // Create a scroll pane to hold the item table
        ScrollPane scrollPane = new ScrollPane(itemTable, game.getSkin());
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSmoothScrolling(true);

        // Setting default description label
        setDescritpionLabel(descriptionTable);

        // Create right table for descriptions of the items
        Table rightTable = new Table();
        setRightTable(rightTable, descriptionTable);

        // Create root table that combains all the tables
        Table rootTable = new Table(game.getSkin());
        setRootTable(rootTable, firstTable, scrollPane, rightTable);
        game.getStage().addActor(rootTable);
    }

    private void setDescritpionLabel(Table descriptionTable)
    {
        // Create stats desription table
        Label label = new Label("Click on the image to show statistics of the item", game.getSkin());
        label.setFontScale(1.5f);
        label.setWrap(true);
        label.setAlignment(Align.center);
        descriptionTable.add(label).width(450f).top();
    }

    private void setRightTable(Table rightTable, Table descriptionTable)
    {
        TextButton backButton = createBackButton();
        rightTable.add(descriptionTable).fill().expand();
        rightTable.row();
        rightTable.add(backButton).padBottom(100f);
    }

    private void setRootTable(Table rootTable, Table firstTable, ScrollPane scrollPane, Table rightTable){
        rootTable.row();

        rootTable.setFillParent(true);
        rootTable.add(firstTable).left().padLeft(200f);
        Label statsText  = new Label("Statistics", game.getSkin());
        statsText.setFontScale(2.4f);
        rootTable.add(statsText).expandX().align(Align.center).colspan(5);
        rootTable.row();
        rootTable.add(scrollPane).left().padLeft(200f);
        rootTable.add(rightTable).expand().fill();

    }

    private void setFirstRow(Table firstTable)
    {
        firstTable.defaults().pad(30).width(CellWidth).height(CellHeight);
        firstTable.columnDefaults(0).align(Align.center).width(CellWidth);
        firstTable.columnDefaults(1).width(CellWidth);
        firstTable.columnDefaults(2).padRight((float) CellWidth / 2).width(CellWidth);
        firstTable.columnDefaults(3).padRight((float) CellWidth / 2).width(CellWidth);

        Label nameLabel = new Label("Name", game.getSkin());
        nameLabel.setFontScale(1.6f);
        firstTable.add(nameLabel);

        Label imageLable = new Label("Image", game.getSkin());
        imageLable.setFontScale(1.6f);
        firstTable.add(imageLable);

        Label priceLabel = new Label("Price", game.getSkin());
        priceLabel.setFontScale(1.6f);
        priceLabel.setAlignment(Align.center);
        firstTable.add(priceLabel);

        Label buyLabel = new Label("Buy", game.getSkin());
        buyLabel.setFontScale(1.6f);
        buyLabel.setAlignment(Align.center);
        firstTable.add(buyLabel);

        Label sellLabel = new Label("Sell", game.getSkin());
        sellLabel.setFontScale(1.4f);
        sellLabel.setAlignment(Align.center);
        firstTable.add(sellLabel);
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

    private void setItemTable(Table itemTable, Table descriptionTable)
    {
        itemTable.defaults().pad(30).width(CellWidth).height(CellHeight);
        itemTable.columnDefaults(0).align(Align.center).width(CellWidth);
        itemTable.columnDefaults(1).width(CellWidth);
        itemTable.columnDefaults(2).width(CellWidth);
        itemTable.columnDefaults(3).padLeft(50).padRight((float) CellWidth / 2).width(CellWidth);
        itemTable.columnDefaults(4).padRight((float) CellWidth / 2).width(CellWidth);

        addItems(itemTable, descriptionTable);
    }

    private void addItems(Table itemTable, Table descriptionTable)
    {
        for (Map.Entry<String, Hashtable<String, String>> set : shopItems.entrySet())
        {
            String name = set.getKey();
            String price = set.getValue().get("price");
            String attack = set.getValue().get("attack");
            String defence = set.getValue().get("defence");
            String imagePath = set.getValue().get("imagePath");

            // Create label for item price

            Label priceLabel = new Label(price, game.getSkin());
            priceLabel.setAlignment(Align.center);
            priceLabel.setFontScale(1.2f);
            Label nameLabel = new Label(name, game.getSkin());
            nameLabel.setFontScale(1.2f);

            // Create image button
            ImageButton itemButton = createItemButton(name, price, attack, defence, imagePath, descriptionTable);

            // Create buy/sell buttons for item
            TextButton buyButton = createBuyButton(name, price, descriptionTable);
            TextButton sellButton = createSellButton(name, price, descriptionTable);

            // Add item components to the item table
            itemTable.add(nameLabel);
            itemTable.add(itemButton);
            itemTable.add(priceLabel);
            itemTable.add(buyButton);
            itemTable.add(sellButton);
            itemTable.row();
        }
    }

    private TextButton createBuyButton(String name, String price, Table descriptionTable)
    {
        TextButton buyButton = new TextButton("Buy", game.getSkin());

        buyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                buyItem(name, price);
            }
        });
        return buyButton;
    }

    private TextButton createSellButton(String name, String price, Table descriptionTable)
    {
        TextButton sellButton = new TextButton("Sell", game.getSkin());

        sellButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                sellItem(name, price);
            }
        });
        return sellButton;
    }

    private ImageButton createItemButton(String name, String price, String attack,
                                         String defence, String imagePath, Table descriptionTable)
    {
        // Load item texture
        Texture itemTexture = new Texture(Gdx.files.internal(imagePath));

        TextureRegion itemRegion = new TextureRegion(itemTexture, itemTexture.getWidth(), itemTexture.getHeight());
        TextureRegionDrawable drawable = new TextureRegionDrawable(itemRegion);
        drawable.setMinSize(CellWidth, CellHeight);

        //create button with item image
        ImageButton itemButton = new ImageButton(drawable);
        itemButton.getImage().setAlign(Align.bottom);

        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                showStats(name, price, attack, defence, descriptionTable);
            }
        });
        return itemButton;
    }

    private void showStats(String name, String price, String attack, String defence, Table descriptionTable)
    {
        descriptionTable.clear();
        Label labelName = new Label(name, game.getSkin());
        labelName.setFontScale(2.5f);
        descriptionTable.add(labelName).align(Align.top).padTop(60f).fill().row();

        Label priceLabel = new Label("Price " + price, game.getSkin());
        priceLabel.setFontScale(1.7f);
        descriptionTable.add(priceLabel).padTop(100f).row();

        Label attackLabel = new Label("Attack " + attack, game.getSkin());
        attackLabel.setFontScale(1.7f);
        descriptionTable.add(attackLabel).padTop(30f).row();

        Label defenceLable = new Label("Defence " + defence, game.getSkin());
        defenceLable.setFontScale(1.7f);
        descriptionTable.add(defenceLable).padTop(30f).padBottom(100f);
    }

    private void buyItem(String name, String price)
    {
        Player player = game.getCurrentPlayer();
        int priceInt = Integer.parseInt(price);

        if (player.removeGold(priceInt) < 0)
        {
            shoInformationDialog("Error", "Not enough gold to buy this item");
        }
        else
        {
            player.addItem(name, 1);
            shoInformationDialog("Congratulation", "Item successfully bought");
        }

    }

    private void sellItem(String name, String price)
    {
        Player player = game.getCurrentPlayer();
        int priceInt = Integer.parseInt(price);

        if (player.removeItem(name, 1) < 0)
        {
            shoInformationDialog("Error", "Item is not in the inventory");
        }
        else
        {
            player.addGold(priceInt);
            shoInformationDialog("Congratulation", "Item successfully sold");
        }

    }

    private void shoInformationDialog(String title, String message)
    {
        InformationDialog dialog = new InformationDialog(title, message, game.getSkin());
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(game.getStage());
    }

    private void readShopItems()
    {
        FileHandle file = Gdx.files.internal("../shop.json");
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        JsonValue rootJson = new JsonReader().parse(file.readString());

        for (JsonValue childJson : rootJson) {
            parseShopItems(childJson);
        }
    }

    private void parseShopItems(JsonValue item)
    {
        String name = item.name;
        String price = item.getString("price");
        String attack = item.getString("attack");
        String defence = item.getString("defence");
        String imagePath = item.getString("image_path");
        Hashtable<String, String> stats = new Hashtable<>();
        stats.put("attack", attack);
        stats.put("defence", defence);
        stats.put("price", price);
        stats.put("imagePath", imagePath);
        ShopScreen.shopItems.put(name, stats);
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