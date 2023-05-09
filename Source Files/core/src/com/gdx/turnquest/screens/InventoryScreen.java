package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Player;

import static com.gdx.turnquest.TurnQuest.*;

public class InventoryScreen extends BaseScreen {
    private ObjectMap<String, Integer> inventory = new ObjectMap<String, Integer>();
    private Player player;
    private static final int CellWidth=100;
    private static final int CellHeight=80;

    public InventoryScreen(final TurnQuest game) {
        super(game);
        player = game.getCurrentPlayer();

        game.setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        Image backgroundImage = new Image(game.getBackgroundTexture());
        backgroundImage.setSize(getVirtualWidth(), getVirtualHeight());
        stage.addActor(backgroundImage);

        Label inventoryLabel = new Label("Inventory", game.getSkin());
        inventoryLabel.setFontScale(2f);

        // table buttons
        TextButton bReturn = new TextButton("Return", game.getSkin());
        TextButton bLeftArrow = new TextButton("<-", game.getSkin());
        TextButton bRightArrow = new TextButton("->", game.getSkin());
        TextButton bInventory = new TextButton("Inventory", game.getSkin());

        // table for return
        Table table = new Table();
        // add some padding and expand each cell
        table.defaults().expand().pad(50);
        table.setFillParent(true);

        table.add(inventoryLabel).colspan(3).padBottom(40);
        table.row();

        // order the buttons of the table
        table.add(bLeftArrow).left();
        table.add();
        table.add(bRightArrow).right();
        table.row();
        table.add();
        table.add(ReadPlayerInventory());
        table.add(bReturn).center().bottom();
        table.add(bInventory).right().center();



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

        game.getStage().addActor(table);
}

    @Override
    protected void refreshScreen() {
        dispose();
        game.setScreen(new InventoryScreen(game));
    }


    @Override
        public void render ( float delta){
            ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

            getCamera().update();
            game.getBatch().setProjectionMatrix(getCamera().combined);

            game.getStage().act();
            game.getStage().draw();

            handleInput();
        }

        private Table ReadPlayerInventory () {
            Table table = new Table();
            table.defaults().size(getVirtualWidth() * 0.85f, getVirtualHeight() * 0.70f).pad(20);
            table.setFillParent(true);

            inventory = player.getInventory();

            table.defaults().pad(30).width(CellWidth).height(CellHeight);
            table.columnDefaults(0).align(Align.center).width(CellWidth);
            table.columnDefaults(1).width(CellWidth);
            table.columnDefaults(2).width(CellWidth);
            table.columnDefaults(3).padLeft(50).padRight((float) CellWidth / 2).width(CellWidth);
            table.columnDefaults(4).padRight((float) CellWidth / 2).width(CellWidth);

            String jsonString = Gdx.files.internal("../Data/items.json").readString();

            // Parse the JSON string using JsonReader
            JsonReader jsonReader = new JsonReader();
            JsonValue root = jsonReader.parse(jsonString);
            int cont =1;

            //iterate through the inventory and print out the items
            for (ObjectMap.Entry<String, Integer> entry : inventory.entries()) {
                System.out.println(entry.key + " " + entry.value);

                if(cont>=3){
                    table.row();
                    cont=1;
                }

                // Get the "item" object from the JSON
                JsonValue itemIterated = root.get(entry.key);

                Table descriptionTable = new Table();
                ImageButton itemButton = createItemButton(itemIterated.getString("name"),
                        itemIterated.getString("value"),
                        itemIterated.getString("type"),
                        itemIterated.getString("type"),
                        itemIterated.getString("imagePath"),
                        descriptionTable);
                table.add(itemButton);
                cont++;



            }
            return table;
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
    }