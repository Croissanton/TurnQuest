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
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.dialogs.AbilitiesDialog;
import com.gdx.turnquest.dialogs.GameOverDialog;
import com.gdx.turnquest.dialogs.UseItemDialog;
import com.gdx.turnquest.dialogs.VictoryDialog;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.animations.AnimationHandler;
import com.gdx.turnquest.utils.EnemyManager;

import com.gdx.turnquest.logic.CombatLogic;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.*;
import static java.lang.Thread.sleep;

/**
 * This class shows the combat screen.
 * The combat screen is shown when the player clicks on an enemy in the map screen.
 * It has 4 buttons, "Attack", "Item", "Ability" and "Run".
 * The "Attack" button allows the player to attack the enemy.
 * The "Item" button allows the player to use an item.
 * The "Ability" button allows the player to use an ability.
 * The "Run" button allows the player to run away from the enemy.
 * The combat screen also shows the player and enemy stats.
 * @author Cristian
 * @author Pablo
 * @author Ignacy
 */
public class CombatScreen extends BaseScreen {
    private Player player;
    private static Enemy enemy = null;
    private Texture enemyTexture;
    private Sprite enemySprite;
    private Label playerHPLabel;
    private Label playerMPLabel;
    private Label enemyMPLabel;
    private Label enemyHPLabel;

    private PlayerManager playerManager;

    private ProgressBar playerHPBar;
    private ProgressBar playerMPBar;
    private ProgressBar enemyHPBar;
    private ProgressBar enemyMPBar;
    private AnimationHandler animationHandler;
    private final String A_IDLE = "idle";
    private final String A_ATTACK = "1_atk";
    private final String A_HURT = "take_hit";
    private final String A_DEATH = "death";
    private boolean playerTurn = true;
    private boolean combatFinished = false;
    private static ObjectMap<String, Integer> initialStatsPlayer;

    public CombatScreen(final TurnQuest game) {
        super(game);
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
                    playSfx("hit.ogg");
                    CombatLogic.attack(player, enemy);
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
                }
            }
        });

        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(playerTurn) {
                    showUseItemDialog();
                }
            }
        });

        runButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerTurn) {
                    if (CombatLogic.run(player, enemy)) {
                        game.getCurrentPlayer().calculateStats();
                        game.setMusic("intro.ogg");
                        game.popScreen();
                    }
                }
            }
        });

        return optionsTable;
    }

    private void createPlayerAnimations() {
        animationHandler = new AnimationHandler();
        TextureAtlas charset = null;
        if (player.getCharacterClass().equalsIgnoreCase("warrior")) {
            charset = new TextureAtlas(Gdx.files.internal("animations/warrior/warrior.atlas"));
        } else if (player.getCharacterClass().equalsIgnoreCase("archer")) {
            charset = new TextureAtlas(Gdx.files.internal("animations/archer/archer.atlas"));
        } else if (player.getCharacterClass().equalsIgnoreCase("mage")) {
            charset = new TextureAtlas(Gdx.files.internal("animations/mage/mage.atlas"));
        }
        float FRAME_TIME = 1 / 10f;
        assert charset != null;
        animationHandler.add(A_IDLE, new Animation<TextureRegion>(FRAME_TIME, charset.findRegions(A_IDLE)));
        animationHandler.add(A_ATTACK, new Animation<TextureRegion>(FRAME_TIME, charset.findRegions(A_ATTACK)));
        animationHandler.add("air_atk", new Animation<TextureRegion>(FRAME_TIME, charset.findRegions("air_atk")));
        animationHandler.add("2_atk", new Animation<TextureRegion>(FRAME_TIME, charset.findRegions("2_atk")));
        animationHandler.add("3_atk", new Animation<TextureRegion>(FRAME_TIME, charset.findRegions("3_atk")));
        animationHandler.add("sp_atk", new Animation<TextureRegion>(FRAME_TIME, charset.findRegions("sp_atk")));
        animationHandler.add(A_HURT, new Animation<TextureRegion>(FRAME_TIME, charset.findRegions(A_HURT)));
        animationHandler.add(A_DEATH, new Animation<TextureRegion>(FRAME_TIME, charset.findRegions(A_DEATH)));
        animationHandler.setCurrent(A_IDLE, true);
    }

    private void createEnemyAnimations() {

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
        game.setMusic("battle.ogg");
        player = game.getCurrentPlayer();
        initialStatsPlayer = new ObjectMap<>();
        initialStatsPlayer.putAll(player.getStats());
        try {
            playerManager = new PlayerManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enemy = new EnemyManager().getEnemy("enemy1_01");
        ObjectMap<String, Integer> initialStatsEnemy = enemy.getStats();

        game.setStage(new Stage(getViewport()));

        // Load the enemy textures
        enemyTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/02.png"));

        createPlayerAnimations();

        enemySprite = createEnemySprite();

        createEnemyAnimations();

        // Add the table to the stage
        game.getStage().addActor(createUIComponents());

        playerHPLabel = new Label("HP: " + initialStatsPlayer.get("HP"), Assets.getSkin());
        playerMPLabel = new Label("MP: " + initialStatsPlayer.get("MP"), Assets.getSkin());
        enemyHPLabel = new Label("HP: " + initialStatsEnemy.get("HP"), Assets.getSkin());
        enemyMPLabel = new Label("MP: " + initialStatsEnemy.get("MP"), Assets.getSkin());

        ProgressBar.ProgressBarStyle progressBarStyleHP = createProgressBarStyleHP();
        ProgressBar.ProgressBarStyle progressBarStyleMP = createProgressBarStyleMP();

        playerHPBar = new ProgressBar(0, initialStatsPlayer.get("HP"), 1, false, progressBarStyleHP);

        playerMPBar = new ProgressBar(0, initialStatsPlayer.get("MP"), 1, false, progressBarStyleMP);

        enemyHPBar = new ProgressBar(0, initialStatsEnemy.get("HP"), 1, false, progressBarStyleHP);

        enemyMPBar = new ProgressBar(0, initialStatsEnemy.get("MP"), 1, false, progressBarStyleMP);


        game.getStage().addActor(createPlayerTable());

        //Space these two out in the X axis (I didn't manage to do that, if you can, please do)

        game.getStage().addActor(createEnemyTable());

        //create the table
        Table table = new Table();
        table.defaults().expand();
        table.setFillParent(true);

        game.getStage().addActor(table);
        //add a tutorial button to the top left of the screen
        game.getStage().addActor(tutorialButton("combat"));
        // apply
        getViewport().apply();

        super.show();
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.
        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        if(animationHandler.isFinished()) {
            updateBarsAndTags();
            if (!combatFinished) evaluateCombat();
            if(player.getHP() != 0) {
                animationHandler.setCurrent(A_IDLE, true);
            }
        }

            if(!playerTurn && !combatFinished && animationHandler.isFinished()){
                updateBarsAndTags();
                if (!combatFinished) evaluateCombat();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                playSfx("hurt.ogg");
                CombatLogic.attack(enemy, player);
                playerTurn = true;

                if(player.getHP() == 0) animationHandler.setCurrent(A_DEATH);

                else animationHandler.setCurrent(A_HURT);

            }

        TextureRegion frame = animationHandler.getFrame();

        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
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
        enemyTexture.dispose();
    }

    private void showAbilitiesDialog() {
        AbilitiesDialog dialog = new AbilitiesDialog("", () -> {
            playerTurn = false;
        }, Assets.getSkin(), player, animationHandler, enemy);
        dialog.show(game.getStage());
    }

    private void showUseItemDialog() {
        UseItemDialog dialog = new UseItemDialog("Inventory", ()->{
            if(player.getHP() > initialStatsPlayer.get("HP")) {
                player.setHP(initialStatsPlayer.get("HP"));
            }
            if(player.getMP() > initialStatsPlayer.get("MP")) {
                player.setMP(initialStatsPlayer.get("MP"));
            }
            playerTurn = false;
        }, Assets.getSkin(), game, player);
        dialog.show(game.getStage());
    }

    private void evaluateCombat(){
        if(player.getHP() <= 0){
            combatFinished = true;
            CombatLogic.defeat(player, enemy);
            new GameOverDialog(game, Assets.getSkin()).show(game.getStage());
        }
        else if(enemy.getHP() <= 0){
            combatFinished = true;
            int level = player.getLevel();
            CombatLogic.victory(player, enemy);
            if(playerManager.savePlayer(player) == 0){
                System.out.println("Player saved");
            }
            else{
                System.out.println("Player not saved");
            }
            //create dialog that says you won and when ok is pressed, go back to map screen

            new VictoryDialog(game, Assets.getSkin(), level).show(game.getStage());
        }
    }
    private void updateBarsAndTags(){
        playerHPLabel.setText("HP: " + player.getHP());
        playerMPLabel.setText("MP: " + player.getMP());
        enemyHPLabel.setText("HP: " + enemy.getHP());
        enemyMPLabel.setText("MP: " + enemy.getMP());
        playerHPBar.setValue(player.getHP());
        playerMPBar.setValue(player.getMP());
        enemyHPBar.setValue(enemy.getHP());
        enemyMPBar.setValue(enemy.getMP());
    }
}