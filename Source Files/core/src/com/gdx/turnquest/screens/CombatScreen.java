package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.dialogs.AbilitiesDialog;
import com.gdx.turnquest.dialogs.GameOverDialog;
import com.gdx.turnquest.dialogs.VictoryDialog;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.utils.AnimationHandler;
import com.gdx.turnquest.utils.EnemyManager;

import com.gdx.turnquest.logic.CombatLogic;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.*;
import static java.lang.Thread.sleep;

public class CombatScreen extends BaseScreen {
    private Player player;
    private static Enemy enemy = null;
    private Texture playerTexture;
    private Texture enemyTexture;
    private Sprite playerSprite;
    private Sprite enemySprite;
    private Label playerHPLabel;
    private Label playerMPLabel;
    private Label enemyMPLabel;
    private Label enemyHPLabel;

    private PlayerManager playerManager;

    private int maxPlayerHP;
    private int maxPlayerMP;
    private int maxEnemyHP;
    private int maxEnemyMP;
    private boolean boss;

    private ProgressBar playerHPBar;
    private ProgressBar playerMPBar;
    private ProgressBar enemyHPBar;
    private ProgressBar enemyMPBar;
    private AnimationHandler animationHandler;
    private String A_IDLE = "idle";
    private String A_ATTACK = "1_atk";
    private String A_CRIT = "2_atk";
    private String A_HURT = "take_hit";
    private String A_DEATH = "death";

    private boolean playerTurn = true;

    public CombatScreen(final TurnQuest game, boolean boss) {
        super(game);
        this.boss = boss;
    }

    @Override
    public Table createUIComponents() {
        // Create UI components
        TextButton attackButton = new TextButton("Attack", Assets.getSkin());
        TextButton abilitiesButton = new TextButton("Abilities", Assets.getSkin());
        TextButton itemButton = new TextButton("Item", Assets.getSkin());
        TextButton runButton = new TextButton("Run", Assets.getSkin());

        // Create the table
        Table optionsTable = new Table(Assets.getSkin());
        optionsTable.setPosition(TurnQuest.getVirtualWidth() / 2f, 200f, Align.center);
        optionsTable.defaults().space(20f).width(200);
        optionsTable.add(attackButton);
        optionsTable.add(itemButton);
        optionsTable.row();
        optionsTable.add(abilitiesButton);
        optionsTable.add(runButton);

        // Add listeners to the buttons
        attackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(playerTurn) {
                    animationHandler.setCurrent(A_ATTACK);
                    CombatLogic.attack(player, enemy);
                    evaluateCombat();
                    playerTurn = false;
                }
            }
        });

        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(playerTurn) {
                    showAbilitiesDialog();
                    //Fetch abilities and use them accordingly
                    evaluateCombat();
                    playerTurn = false;
                }
            }
        });

        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(playerTurn) {
                    // Fetch inventory
                    // Display inventory
                    // Select item
                    // Use item with CombatLogic.useItem(player, itemID)
                    evaluateCombat();
                    playerTurn = false;
                }
            }
        });

        runButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerTurn) {
                    if (CombatLogic.run(player, enemy)) {
                        game.setMusic("intro.ogg");
                        game.popScreen();
                    }
                    evaluateCombat();
                }
            }
        });

        return optionsTable;
    }

    private Sprite createPlayerSprite() {
        animationHandler = new AnimationHandler();
        TextureAtlas charset = null;
        if (player.getCharacterClass().equalsIgnoreCase("warrior")) {
            charset = new TextureAtlas(Gdx.files.internal("Elementals_fire_knight_FREE_v1.1/animations/warrior.atlas"));
        } else if (player.getCharacterClass().equalsIgnoreCase("archer")) {
            charset = new TextureAtlas(Gdx.files.internal("Elementals_fire_knight_FREE_v1.1/animations/archer.atlas"));
        } else if (player.getCharacterClass().equalsIgnoreCase("mage")) {
            charset = new TextureAtlas(Gdx.files.internal("Elementals_fire_knight_FREE_v1.1/animations/mage.atlas"));
        }
        float FRAME_TIME = 1 / 10f;
        assert charset != null;
        animationHandler.add(A_IDLE, new Animation<TextureRegion>(FRAME_TIME, charset.findRegions(A_IDLE)));
        animationHandler.add(A_ATTACK, new Animation<TextureRegion>(FRAME_TIME, charset.findRegions(A_ATTACK)));
        animationHandler.add(A_CRIT, new Animation<TextureRegion>(FRAME_TIME, charset.findRegions(A_CRIT)));
        animationHandler.add(A_HURT, new Animation<TextureRegion>(FRAME_TIME, charset.findRegions(A_HURT)));
        animationHandler.add(A_DEATH, new Animation<TextureRegion>(FRAME_TIME, charset.findRegions(A_DEATH)));
        animationHandler.setCurrent(A_IDLE, true);
/*        playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition(getVirtualWidth() * 0.18f, getVirtualHeight() * 0.79f); // Set the position of the player sprite
//        playerSprite.setSize(200, 200); // Set the size of the player sprite
        playerSprite.setScale(8);*/
        return playerSprite;
    }

    private Sprite createEnemySprite() {
        enemySprite = new Sprite(enemyTexture);
        enemySprite.setPosition(getVirtualWidth() * 0.77f, getVirtualHeight() * 0.52f); // Set the position of the enemy sprite
//        enemySprite.setSize(200, 200); // Set the size of the enemy sprite
        enemySprite.setScale(4);
        return enemySprite;
    }

    private ProgressBar.ProgressBarStyle createProgressBarStyleMP() {
        ProgressBar.ProgressBarStyle progressBarStyleMP = new ProgressBar.ProgressBarStyle();
        progressBarStyleMP.background = Assets.getSkin().getDrawable("progress-bar-mana");
        progressBarStyleMP.knobBefore = Assets.getSkin().getDrawable("progress-bar-mana-knob");
        return progressBarStyleMP;
    }

    private ProgressBar.ProgressBarStyle createProgressBarStyleHP() {
        ProgressBar.ProgressBarStyle progressBarStyleHP = new ProgressBar.ProgressBarStyle();
        progressBarStyleHP.background = Assets.getSkin().getDrawable("progress-bar-health");
        progressBarStyleHP.knobBefore = Assets.getSkin().getDrawable("progress-bar-health-knob");
        return progressBarStyleHP;
    }

    private Table createPlayerTable() {
        Table playerTable = new Table(Assets.getSkin());
        playerTable.setPosition(getVirtualWidth() * 0.25f, getVirtualHeight() * 0.25f);
        playerTable.defaults().space(10f);
        playerTable.add(playerHPLabel).row();
        playerTable.add(playerHPBar).width(400f).row();
        playerTable.add(playerMPLabel).row();
        playerTable.add(playerMPBar).width(400f).row();

        return playerTable;
    }

    private Table createEnemyTable() {
        Table enemyTable = new Table(Assets.getSkin());
        enemyTable.setPosition(getVirtualWidth() * 0.8f, getVirtualHeight() * 0.25f);
        enemyTable.defaults().space(10f);
        enemyTable.add(enemyHPLabel).row();
        enemyTable.add(enemyHPBar).width(400f).row();
        enemyTable.add(enemyMPLabel).row();
        enemyTable.add(enemyMPBar).width(400f).row();
        return enemyTable;
    }

    @Override
    public void show() {
        Assets.loadFor(CombatScreen.class);
        Assets.ASSET_MANAGER.finishLoading();
        Assets.setBackgroundTexture(new Texture(Gdx.files.internal(Assets.FOREST_BACKGROUND_PNG)));
        game.setMusic("boss1.ogg");
        player = game.getCurrentPlayer();
        try {
            playerManager = new PlayerManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (boss) {
            enemy = new EnemyManager().getEnemy("boss1");
        } else {
            enemy = new EnemyManager().getEnemy("enemy1_01");
        }

        maxPlayerHP = player.getHP();
        maxPlayerMP = player.getMP();
        maxEnemyHP = enemy.getHP();
        maxEnemyMP = enemy.getMP();


        game.setStage(new Stage(getViewport()));

        // Load the player and enemy textures
        Texture warriorTexture = new Texture(Gdx.files.internal("Elementals_fire_knight_FREE_v1.1/png/fire_knight/01_idle/idle_1.png"));
        Texture archerTexture = new Texture(Gdx.files.internal("Elementals_Leaf_ranger_Free_v1.0/animations/PNG/1_atk/1_atk_1.png"));
        Texture mageTexture = new Texture(Gdx.files.internal("Elementals_water_priestess_FREE_v1.1/png/01_idle/idle_1.png"));

        if (player.getCharacterClass().equalsIgnoreCase("warrior")) {
            playerTexture = warriorTexture;
        } else if (player.getCharacterClass().equalsIgnoreCase("archer")) {
            playerTexture = archerTexture;
        } else if (player.getCharacterClass().equalsIgnoreCase("mage")) {
            playerTexture = mageTexture;
        }

        if (boss) {
            enemyTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/03.png"));
        } else {
            enemyTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/02.png"));
        }

        playerSprite = createPlayerSprite();

        enemySprite = createEnemySprite();

        // Add the table to the stage
        game.getStage().addActor(createUIComponents());

        playerHPLabel = new Label("HP: " + maxPlayerHP, Assets.getSkin());
        playerMPLabel = new Label("MP: " + maxPlayerMP, Assets.getSkin());
        enemyHPLabel = new Label("HP: " + maxEnemyHP, Assets.getSkin());
        enemyMPLabel = new Label("MP: " + maxEnemyMP, Assets.getSkin());

        ProgressBar.ProgressBarStyle progressBarStyleHP = createProgressBarStyleHP();
        ProgressBar.ProgressBarStyle progressBarStyleMP = createProgressBarStyleMP();

        playerHPBar = new ProgressBar(0, maxPlayerHP, 1, false, progressBarStyleHP);

        playerMPBar = new ProgressBar(0, maxPlayerMP, 1, false, progressBarStyleMP);

        enemyHPBar = new ProgressBar(0,maxEnemyHP, 1, false, progressBarStyleHP);

        enemyMPBar = new ProgressBar(0, maxEnemyMP, 1, false, progressBarStyleMP);


        game.getStage().addActor(createPlayerTable());

        //Space these two out in the X axis (I didn't manage to do that, if you can, please do)

        game.getStage().addActor(createEnemyTable());

        //create the table
        Table table = new Table();
        table.defaults().expand();
        table.setFillParent(true);

        game.getStage().addActor(table);
        // apply
        getViewport().apply();
        super.show();
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.
        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        if(animationHandler.isFinished() && !playerTurn){
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            CombatLogic.attack(enemy, player);
            animationHandler.setCurrent(A_HURT);
            playerTurn = true;
        }
        if(animationHandler.isFinished()) animationHandler.setCurrent(A_IDLE, true);



        TextureRegion frame = animationHandler.getFrame();

        playerHPLabel.setText("HP: " + player.getHP());
        playerMPLabel.setText("MP: " + player.getMP());
        enemyHPLabel.setText("HP: " + enemy.getHP());
        enemyMPLabel.setText("MP: " + enemy.getMP());

        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        playerHPBar.setValue(player.getHP());
        playerMPBar.setValue(player.getMP());
        enemyHPBar.setValue(enemy.getHP());
        enemyMPBar.setValue(enemy.getMP());
        //playerSprite.draw(game.getBatch());
        enemySprite.draw(game.getBatch());
        game.getBatch().draw(frame, -getVirtualWidth()*0.355f, getVirtualHeight() * 0.38f, frame.getRegionWidth() * 8f, frame.getRegionHeight() * 8f);
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleKeyboardInput();
    }


    @Override
    public void dispose() {
        game.getStage().dispose();
        playerTexture.dispose();
        enemyTexture.dispose();
    }

    private void showAbilitiesDialog() {
        AbilitiesDialog dialog = new AbilitiesDialog("", () -> {
            // Handle abilities here
        }, Assets.getSkin(), game);
        dialog.show(game.getStage());
    }

    public static Enemy getEnemy() {
        return enemy;
    }

    private void evaluateCombat(){
        if(player.getHP() <= 0){
            CombatLogic.defeat(player, enemy);
            if(playerManager.savePlayer(player) == 0){
                System.out.println("Player saved");
            }
            else{
                System.out.println("Player not saved");
            }
            new GameOverDialog(game, Assets.getSkin());
        }
        else if(enemy.getHP() <= 0){
            int level = player.getLevel();
            CombatLogic.victory(player, enemy);
            if(playerManager.savePlayer(player) == 0){
                System.out.println("Player saved");
            }
            else{
                System.out.println("Player not saved");
            }
            //create dialog that says you won and when ok is pressed, go back to map screen

            new VictoryDialog(game, Assets.getSkin()).show(game.getStage());
        }
    }
}