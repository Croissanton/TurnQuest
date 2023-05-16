package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.*;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.entities.Player;

import static com.gdx.turnquest.TurnQuest.*;

public class InventoryScreen extends BaseScreen {
    private ObjectMap<String, Integer> inventory = new ObjectMap<>();
    private Player player;
    private static final int CELL_WIDTH =100;
    private static final int CELL_HEIGHT =80;

    public InventoryScreen(final TurnQuest game) {
        super(game);
        player = game.getCurrentPlayer();
    }

    @Override
    public void show() {
        Assets.loadFor(InventoryScreen.class);
        Assets.ASSET_MANAGER.finishLoading();
        Assets.setBackgroundTexture(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG));

        Image backgroundImage = new Image(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG));
        backgroundImage.setSize(getVirtualWidth(), getVirtualHeight());
        stage.addActor(backgroundImage);

        game.getStage().addActor(createUIComponents());
        getViewport().apply();
        super.show();
    }

    @Override
    public Table createUIComponents() {
        Label inventoryLabel = new Label("Inventory", Assets.getSkin());
        inventoryLabel.setFontScale(2f);

        // table buttons
        TextButton bReturn = new TextButton("Return", Assets.getSkin());
        TextButton bLeftArrow = new TextButton("<-", Assets.getSkin());
        TextButton bRightArrow = new TextButton("->", Assets.getSkin());
        TextButton bInventory = new TextButton("Inventory", Assets.getSkin());

        // table for return
        Table table = new Table();
        // add some padding and expand each cell
        table.defaults().expand().pad(50);
        table.setFillParent(true);

        table.add(inventoryLabel).colspan(3).padBottom(40);
        table.row();

        // order the buttons of the table
        table.row().pad(50,20,50,20);

        table.add(ReadPlayerInventory());
        table.row();
        table.add(bLeftArrow).left();
        table.add();
        table.add(bRightArrow).right();
        table.row();
        table.add();
        table.add(bReturn).center().bottom();
        table.add(bInventory).right().center();



        // if an arrow is clicked, go to abilities screen
        bRightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new AbilitiesScreen(game));
            }
        });

        bLeftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.pushScreen(new AbilitiesScreen(game));
            }
        });

        // return to GameScreen when pressed return button
        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popScreen();
            }
        });

        bInventory.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event,float x, float y){
                ReadPlayerInventory();
            }
        });
        return table;
    }

    @Override
        public void render ( float delta){
            ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

            getCamera().update();
            game.getBatch().setProjectionMatrix(getCamera().combined);
            game.getStage().act();
            game.getStage().draw();

            handleKeyboardInput();
        }

        private Table ReadPlayerInventory () {
            Table table = new Table();
            table.defaults().size(getVirtualWidth() * 0.85f, getVirtualHeight() * 0.70f).pad(20);
            table.setFillParent(true);

            inventory = player.getInventory();

            table.defaults().pad(30).width(CELL_WIDTH).height(CELL_HEIGHT);
            table.columnDefaults(0).align(Align.center).width(CELL_WIDTH);
            table.columnDefaults(1).width(CELL_WIDTH);
            table.columnDefaults(2).width(CELL_WIDTH);
            table.columnDefaults(3).padLeft(50).padRight((float) CELL_WIDTH / 2).width(CELL_WIDTH);
            table.columnDefaults(4).padRight((float) CELL_WIDTH / 2).width(CELL_WIDTH);

            String jsonString = Gdx.files.internal("../Data/items.json").readString();

            // Parse the JSON string using JsonReader
            JsonReader jsonReader = new JsonReader();
            JsonValue root = jsonReader.parse(jsonString);
            System.out.println(jsonString);

            int cont =1;

            for (ObjectMap.Entry<String, Integer> entry : inventory.entries()) {
                String itemName = entry.key;
                int qty = entry.value;
                System.out.println("Item Name: " + itemName + ": " + qty);
                System.out.println("Inside ITEMS "+root.get(itemName)+" \n"+root.get(itemName)+" "+root.child.get("imagePath"));


                if(cont>=3){
                    table.row();
                    cont=1;
                }


                Table descriptionTable = new Table();
                ImageButton itemButton = createItemButton(String.valueOf(root.get(itemName)),
                        String.valueOf(root.child.get("value")),
                        String.valueOf(root.child.get("type")),
                        String.valueOf(root.child.get("type")),
                        String.valueOf(root.child.getString("imagePath")),
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
            drawable.setMinSize(CELL_WIDTH, CELL_HEIGHT);

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
            Label labelName = new Label(name, Assets.getSkin());
            labelName.setFontScale(2.5f);
            descriptionTable.add(labelName).align(Align.top).padTop(60f).fill().row();

            Label priceLabel = new Label("Price " + price, Assets.getSkin());
            priceLabel.setFontScale(1.7f);
            descriptionTable.add(priceLabel).padTop(100f).row();

            Label attackLabel = new Label("Attack " + attack, Assets.getSkin());
            attackLabel.setFontScale(1.7f);
            descriptionTable.add(attackLabel).padTop(30f).row();

            Label defenceLable = new Label("Defence " + defence, Assets.getSkin());
            defenceLable.setFontScale(1.7f);
            descriptionTable.add(defenceLable).padTop(30f).padBottom(100f);
        }
    }