package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.dialogs.InformationDialog;
import com.gdx.turnquest.entities.Player;


import java.util.*;


import static com.gdx.turnquest.TurnQuest.*;

/**
 * This class is responsible for displaying the inventory of the player.
 * It shows the items that the player has collected and the gold that he has.
 * It also shows the statistics of the items.
 * The player can click on the item to see the statistics.
 * 
 * @author Mikolaj
 * @author Cristian
 */
public class InventoryScreen extends BaseScreen {
    private static final int CellWidth=100;
    private static final int CellHeight=80;
    static HashMap<String, LinkedHashMap<String, String>> inventoryItems;
    private Label statsText;
    private float elapsed_time;
    private Animation<TextureRegion> goldCoin;

    public InventoryScreen(final TurnQuest game) {
        super(game);
    }

    private void setDescriptionLabel(Table descriptionTable)
    {
        // Create stats description table
        Label label = new Label("Click on the image to show statistics of the item", Assets.getSkin());
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
        rightTable.add(backButton).padBottom(100f).align(Align.bottom);
    }

    private void setRootTable(Table rootTable, Table firstTable, ScrollPane scrollPane, Table rightTable){
        rootTable.row();

        rootTable.setFillParent(true);
        rootTable.add(firstTable).left().padLeft(200f);
        statsText  = new Label("      "+game.getCurrentPlayer().getGold()+"c.", Assets.getSkin());
        statsText.setFontScale(2.4f);
        rootTable.add(statsText).expandX().align(Align.center).colspan(5);
        rootTable.row();
        rootTable.add(scrollPane).left().padLeft(200f);
        rootTable.add(rightTable).expand().fill();

    }

    private void setFirstRow(Table firstTable)
    {
        firstTable.defaults().pad(30).width(CellWidth).height(CellHeight);
        firstTable.columnDefaults(0).align(Align.center).width(CellWidth*2);
        firstTable.columnDefaults(1).width(CellWidth);
        firstTable.columnDefaults(2).padRight((float) CellWidth / 2).width(CellWidth);
        firstTable.columnDefaults(3).padRight((float) CellWidth / 2).width(CellWidth);

        Label nameLabel = new Label("Name", Assets.getSkin());
        nameLabel.setFontScale(1.6f);
        firstTable.add(nameLabel);

        Label emptyLabel = new Label("", Assets.getSkin());
        firstTable.add(emptyLabel);

        Label priceLabel = new Label("Price", Assets.getSkin());
        priceLabel.setFontScale(1.6f);
        firstTable.add(priceLabel);

        Label quantityLabel = new Label("Quantity", Assets.getSkin());
        quantityLabel.setFontScale(1.6f);
        firstTable.add(quantityLabel);

        firstTable.add(emptyLabel);
    }

    public TextButton createBackButton()
    {
        TextButton bReturn = new TextButton("Return", Assets.getSkin());
        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popScreen();
            }
        });
        return bReturn;
    }

    private void setItemTable(Table itemTable, Table descriptionTable)
    {
        itemTable.defaults().pad(30).width(CellWidth).height(CellHeight);
        itemTable.columnDefaults(0).align(Align.center).width(CellWidth*2);
        itemTable.columnDefaults(1).width(CellWidth);
        itemTable.columnDefaults(2).width(CellWidth);
        itemTable.columnDefaults(3).padLeft(50).padRight((float) CellWidth / 2).width(CellWidth);
        itemTable.columnDefaults(4).padRight((float) CellWidth / 2).width(CellWidth);

        addItems(itemTable, descriptionTable);
    }

    private void addItems(Table itemTable, Table descriptionTable)
    {
        for (Map.Entry<String, LinkedHashMap<String, String>> set : inventoryItems.entrySet())
        {
            if(game.getCurrentPlayer().getInventory().containsKey(set.getKey())) {
                String name = set.getValue().get("name");
                String price = set.getValue().get("value");
                int quantity = game.getCurrentPlayer().getInventory().get(set.getKey());


                // Create label for item price
                Label priceLabel = new Label(price, Assets.getSkin());
                priceLabel.setAlignment(Align.center);
                priceLabel.setFontScale(1.2f);
                Label nameLabel = new Label(name, Assets.getSkin());
                nameLabel.setFontScale(1.2f);
                Label quantityLabel = new Label(String.valueOf(quantity), Assets.getSkin());
                quantityLabel.setFontScale(1.2f);

                // Create image button
                ImageButton itemButton = createItemButton(set, descriptionTable);

                // Create sell buttons for item
                //TextButton sellButton = createSellButton(name, price);

                // Add item components to the item table
                itemTable.add(nameLabel);
                itemTable.add(itemButton);
                itemTable.add(priceLabel);
                itemTable.add(quantityLabel);
                //itemTable.add(sellButton);
                itemTable.row();
            }
        }
    }
    private TextButton createSellButton(String name, String price)
    {
        TextButton sellButton = new TextButton("Sell", Assets.getSkin());

        sellButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                sellItem(name, price);
            }
        });
        return sellButton;
    }

    private ImageButton createItemButton(Map.Entry<String, LinkedHashMap<String, String>> set, Table descriptionTable)
    {
        String name = set.getValue().get("name");
        String imagePath = set.getValue().get("imagePath");

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
                showStats(name, set.getValue(), descriptionTable);
            }
        });
        return itemButton;
    }

    private void showStats(String name, Map<String, String > stats, Table descriptionTable)
    {
        descriptionTable.clear();
        Label labelName = new Label(name, Assets.getSkin());
        labelName.setFontScale(2.5f);
        labelName.setAlignment(Align.center);
        descriptionTable.add(labelName).align(Align.top).padTop(60f).fill().row();

        for (Map.Entry<String, String> statistic: stats.entrySet()) {
            if (!(statistic.getKey().equals("imagePath") || statistic.getKey().equals("name"))) {
                Label priceLabel;
                if (statistic.getKey().equals("description"))
                    priceLabel = new Label(statistic.getValue(), Assets.getSkin());
                else
                    priceLabel = new Label(statistic.getKey() + " " + statistic.getValue(), Assets.getSkin());


                priceLabel.setFontScale(1.3f);
                descriptionTable.add(priceLabel).padTop(50f).row();
            }
        }
    }

    private void sellItem(String name, String price)
    {
        Player player = game.getCurrentPlayer();
        int priceInt = Integer.parseInt(price);

        if (player.removeItem(name, 1) < 0)
        {
            shopInformationDialog("Error", "Item is not in the inventory");
        }
        else
        {
            player.addGold(priceInt);
            playSfx("coinsplash.ogg");
            shopInformationDialog("Congratulations", "Item successfully sold");
        }

    }

    private void shopInformationDialog(String title, String message)
    {
        InformationDialog dialog = new InformationDialog(title, message, Assets.getSkin());
        dialog.setColor(Color.LIGHT_GRAY);
        dialog.show(game.getStage());
    }

    private void readInventoryItems()
    {
        //FileHandle file = Gdx.files.internal("../Data/players.json");


        String jsonString = Gdx.files.internal("../Data/items.json").readString();

        // Parse the JSON string using JsonReader
        JsonReader jsonReader = new JsonReader();
        JsonValue rootJson = jsonReader.parse(jsonString);
        System.out.println(jsonString);

        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        //JsonValue rootJson = new JsonReader().parse(file.readString());

        for (JsonValue childJson : rootJson) {
            parseInventoryItems(childJson);
        }
    }

    private void parseInventoryItems(JsonValue item)
    {
        LinkedHashMap<String, String> stats = new LinkedHashMap<>();
        JsonValue child = item.child;

        while (!child.name.equals("stats")) {
            stats.put(child.name, item.getString(child.name));
            child = child.next;
        }

        JsonValue statsToExtract = item.get("stats");
        JsonValue grandchild = statsToExtract.child;

        for (int i = 0; i < statsToExtract.size; i++) {

            stats.put(grandchild.name, statsToExtract.getString(grandchild.name));
            grandchild = grandchild.next;
        }
        InventoryScreen.inventoryItems.put(stats.get("name"), stats);
    }


    @Override
    public Table createUIComponents() {

        // Load inventory
        inventoryItems = new HashMap<>();
        readInventoryItems();

        // Create description of the item table
        Table firstTable = new Table();
        setFirstRow(firstTable);

        // Create the table to hold the items
        Table descriptionTable = new Table(Assets.getSkin());  //this will be the table for the description of the stats of each item, after being clicked
        Table itemTable = new Table(Assets.getSkin());
        setItemTable(itemTable, descriptionTable);

        // Create a scroll pane to hold the item table
        ScrollPane scrollPane = new ScrollPane(itemTable, Assets.getSkin());
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSmoothScrolling(true);

        // Setting default description label
        setDescriptionLabel(descriptionTable);

        // Create right table for descriptions of the items
        Table rightTable = new Table();
        setRightTable(rightTable, descriptionTable);

        // Create root table that combains all the tables
        Table rootTable = new Table(Assets.getSkin());
        setRootTable(rootTable, firstTable, scrollPane, rightTable);

        TextureAtlas charset = new TextureAtlas(Gdx.files.internal("animations/gold_coin/gold_coin.atlas"));
        goldCoin = new Animation<TextureRegion>(1/9f, charset.getRegions());

        return rootTable;
    }

    @Override
    public void show() {
        Assets.loadFor(InventoryScreen.class);
        Assets.ASSET_MANAGER.finishLoading();
        Assets.setBackgroundTexture(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG));

        game.setStage(new Stage(getViewport()));
        game.getStage().addActor(createUIComponents());
        //add a tutorial button to the top left of the screen
        game.getStage().addActor(tutorialButton("inventory"));
        getViewport().apply();
        super.show();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1);

        statsText.setText(("      " + game.getCurrentPlayer().getGold()+"c."));
        elapsed_time += delta;
        TextureRegion currentFrame = goldCoin.getKeyFrame(elapsed_time, true);

        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, getVirtualWidth(), getVirtualHeight());
        game.getBatch().draw(currentFrame, getVirtualWidth()*0.70f, getVirtualHeight() * 0.885f, currentFrame.getRegionWidth()*3.5f, currentFrame.getRegionHeight()*3.5f);
        game.getBatch().end();
        game.getStage().act();
        game.getStage().draw();

        handleKeyboardInput();
    }
}