package com.gdx.turnquest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.plaf.synth.Region;

import static com.gdx.turnquest.TurnQuest.*;
import static com.gdx.turnquest.TurnQuest.getVirtualHeight;
import static java.lang.Math.min;

public class ShopScreen implements Screen {
    final TurnQuest game;

    private Stage stage;
    private Table itemTable;
    private ScrollPane scrollPane;

    private int CellWidth=100;
    private int CellHeight=50;



    public ShopScreen(final TurnQuest game) {
        this.game = game;

        stage = new Stage(TurnQuest.getViewport());
        Gdx.input.setInputProcessor(stage);

        setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));


        // Create the table to hold the items
        itemTable = new Table(getSkin());
        itemTable.defaults().pad(30).width(CellWidth).height(CellHeight);
        itemTable.columnDefaults(0).align(Align.center).width(CellWidth);
        itemTable.columnDefaults(1).width(CellWidth);
        itemTable.columnDefaults(2).padLeft(50).padRight(CellWidth/2).width(CellWidth);
        itemTable.columnDefaults(3).padLeft(50).padRight(CellWidth/2).width(CellWidth);

        //DEBUG
        itemTable.add("priceLabel");
        itemTable.add("itemImage");
        itemTable.add("buyButton");
        itemTable.add("sellButton");
        itemTable.row();

        // Add items to the table
        for (int i = 1; i <= 24; i++) {
            // Create label for item price
            Label priceLabel = new Label(String.valueOf(i),getSkin());

            // Load item texture
            Texture itemTexture = new Texture(Gdx.files.internal("items/Icons_no_background/rings_and_necklaces/rpg_icons" + i + ".png"));
            double aspectRatio = itemTexture.getWidth()/itemTexture.getHeight();
            int newWidth = (int) (aspectRatio*itemTexture.getWidth());
            TextureRegion itemRegion = new TextureRegion(itemTexture,newWidth,CellHeight);
            Image itemImage = new Image(itemTexture);


            // Create buy/sell buttons for item
            TextButton buyButton = new TextButton("Buy", getSkin());
            TextButton sellButton = new TextButton("Sell", getSkin());

            // Add item components to the item table
            itemTable.add(priceLabel);
            itemTable.add(itemImage);
            itemTable.add(buyButton);
            itemTable.add(sellButton);
            itemTable.row();
        }

        // Create a scroll pane to hold the item table
        scrollPane = new ScrollPane(itemTable, getSkin());
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setSmoothScrolling(true);

        // Add the scroll pane to the root table of the stage
        Table rootTable = new Table(getSkin());
        rootTable.setFillParent(true);
        rootTable.add(scrollPane).expand().fill();
        stage.addActor(rootTable);

        // Create back button
        TextButton backButton = new TextButton("Back", getSkin());
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        // Add back button to a separate table
        Table backButtonTable = new Table(getSkin());
        backButtonTable.add(backButton).pad(40);
        // Add back button table to the root table
        rootTable.add(backButtonTable).top().left().expandX();
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

        stage.act();
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            TurnQuest.toggleFullscreen();
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
        stage.dispose();
    }
}