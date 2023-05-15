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
import com.gdx.turnquest.dialogs.VictoryDialog;
import com.gdx.turnquest.entities.Character;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.utils.AnimationHandler;
import com.gdx.turnquest.utils.EnemyManager;

import com.gdx.turnquest.logic.CombatLogic;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.*;
import static java.lang.Thread.sleep;

public class BossScreen extends BaseScreen {
    private Player player;
    private Player ally;
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
    private Label allyMPLabel;
    private Label allyHPLabel;
    private ProgressBar allyHPBar;
    private ProgressBar allyMPBar;
    private AnimationHandler animationHandlerPlayer;
    private AnimationHandler animationHandlerAlly;
    private final String A_IDLE = "idle";
    private final String A_ATTACK = "1_atk";
    private final String A_CRIT = "2_atk";
    private final String A_HURT = "take_hit";
    private final String A_DEATH = "death";
    private int whoseTurn = 0; //0 means player, 1 means ally, 2 means enemy
    private boolean combatFinished = false;
    private Character[] players;

    public BossScreen(final TurnQuest game) {
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
                if(whoseTurn < 2) {
                    if(whoseTurn == 0){
                        if (CombatLogic.attack(player, enemy) == 1) {
                            animationHandlerPlayer.setCurrent(A_CRIT);
                        }
                        else animationHandlerPlayer.setCurrent(A_ATTACK);
                    }
                    else if(whoseTurn == 1){
                        if (CombatLogic.attack(ally, enemy) == 1) {
                            animationHandlerAlly.setCurrent(A_CRIT);
                        }
                        else animationHandlerAlly.setCurrent(A_ATTACK);
                    }
                    evaluateCombat();
                    ++whoseTurn;
                }
            }
        });

        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(whoseTurn < 2) {
                    if(whoseTurn == 0) showAbilitiesDialog(player);
                    else if(whoseTurn == 1) showAbilitiesDialog(ally);
                    //Fetch abilities and use them accordingly
                    evaluateCombat();
                    ++whoseTurn;
                }
            }
        });

        itemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(whoseTurn < 2) {
                    if(whoseTurn == 0){

                    }
                    else if(whoseTurn == 1){

                    }
                    // Fetch inventory
                    // Display inventory
                    // Select item
                    // Use item with CombatLogic.useItem(player, itemID)
                    evaluateCombat();
                    ++whoseTurn;
                }
            }
        });

        runButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player character = null;
                if (whoseTurn < 2) {
                    if (whoseTurn == 0) character = player;
                    else if (whoseTurn == 1) character = ally;
                    assert character != null;
                    if (CombatLogic.run(character, enemy)) {
                        game.setMusic("intro.ogg");
                        game.popScreen();
                    }
                    evaluateCombat();
                }
            }
        });

        return optionsTable;
    }

    private AnimationHandler createPlayerAnimations(Player player) {
        AnimationHandler animationHandler = new AnimationHandler();
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
        return animationHandler;
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

    private Table createAllyTable() {
        Table AllyTable = new Table(Assets.getSkin());
        AllyTable.setPosition(getVirtualWidth() * 0.09f, getVirtualHeight() * 0.9f);
        AllyTable.defaults().space(3f);
        AllyTable.add(allyHPBar).width(300f).row();
        AllyTable.add(allyMPBar).width(300f).row();

        return AllyTable;
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
            ally = new PlayerManager().getPlayer("Croissanton"); // Provisionally I am your ally.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        players = new Player[]{player, ally};
        ObjectMap<String, Integer> initialStatsPlayer = player.getStats();
        ObjectMap<String, Integer> initialStatsAlly = ally.getStats();
        try {
            playerManager = new PlayerManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enemy = new EnemyManager().getEnemy("enemy1_01");
        ObjectMap<String, Integer> initialStatsEnemy = enemy.getStats();
        animationHandlerPlayer = createPlayerAnimations(player);
        animationHandlerAlly = createPlayerAnimations(ally);

        game.setStage(new Stage(getViewport()));

        // Load the enemy textures
        enemyTexture = new Texture(Gdx.files.internal("enemies/Fantasy Battlers - Free/x2 size/03.png"));

        createPlayerAnimations(player);
        createPlayerAnimations(ally);

        enemySprite = createEnemySprite();

        // Add the table to the stage
        game.getStage().addActor(createUIComponents());

        playerHPLabel = new Label("HP: " + initialStatsPlayer.get("HP"), Assets.getSkin());
        playerMPLabel = new Label("MP: " + initialStatsPlayer.get("MP"), Assets.getSkin());

        allyHPLabel = new Label("HP: " + initialStatsAlly.get("HP"), Assets.getSkin());
        allyMPLabel = new Label("MP: " + initialStatsAlly.get("MP"), Assets.getSkin());

        enemyHPLabel = new Label("HP: " + initialStatsEnemy.get("HP"), Assets.getSkin());
        enemyMPLabel = new Label("MP: " + initialStatsEnemy.get("MP"), Assets.getSkin());

        ProgressBar.ProgressBarStyle progressBarStyleHP = createProgressBarStyleHP();
        ProgressBar.ProgressBarStyle progressBarStyleMP = createProgressBarStyleMP();

        playerHPBar = new ProgressBar(0, initialStatsPlayer.get("HP"), 1, false, progressBarStyleHP);
        playerMPBar = new ProgressBar(0, initialStatsPlayer.get("MP"), 1, false, progressBarStyleMP);

        allyHPBar = new ProgressBar(0, initialStatsAlly.get("HP"), 1, false, progressBarStyleHP);
        allyMPBar = new ProgressBar(0, initialStatsAlly.get("MP"), 1, false, progressBarStyleMP);

        enemyHPBar = new ProgressBar(0, initialStatsEnemy.get("HP"), 1, false, progressBarStyleHP);
        enemyMPBar = new ProgressBar(0, initialStatsEnemy.get("MP"), 1, false, progressBarStyleMP);


        game.getStage().addActor(createPlayerTable());

        //Space these two out in the X axis (I didn't manage to do that, if you can, please do)

        game.getStage().addActor(createEnemyTable());

        game.getStage().addActor(createAllyTable());

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


        if(animationHandlerPlayer.isFinished() && animationHandlerAlly.isFinished() && whoseTurn == 2 && !combatFinished){
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            CombatLogic.bossAttack(enemy, players);
            whoseTurn = 0;

            if(player.getHP() == 0) animationHandlerPlayer.setCurrent(A_DEATH);
            else animationHandlerPlayer.setCurrent(A_HURT);

            if (ally.getHP() == 0) animationHandlerAlly.setCurrent(A_DEATH);
            else animationHandlerAlly.setCurrent(A_HURT);

            if(!combatFinished) evaluateCombat();
        }
        if(animationHandlerPlayer.isFinished() && player.getHP() != 0) {
            animationHandlerPlayer.setCurrent(A_IDLE, true);
        }
        if(animationHandlerAlly.isFinished() && ally.getHP() != 0) {
            animationHandlerAlly.setCurrent(A_IDLE, true);
        }
        if(player.getHP() == 0 && whoseTurn == 0){
            whoseTurn = 1;
        }
        if(ally.getHP() == 0 && whoseTurn == 1){
            whoseTurn = 2;
        }

        updateBarsAndTags();
        TextureRegion framePlayer = animationHandlerPlayer.getFrame();
        TextureRegion frameAlly = animationHandlerAlly.getFrame();

        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, TurnQuest.getVirtualWidth(), TurnQuest.getVirtualHeight());
        enemySprite.draw(game.getBatch());
        game.getBatch().draw(framePlayer, -getVirtualWidth()*0.355f, getVirtualHeight() * 0.38f, framePlayer.getRegionWidth() * 8f, framePlayer.getRegionHeight() * 8f);
        game.getBatch().draw(frameAlly, -getVirtualWidth()*0.355f, getVirtualHeight() * 0.575f, framePlayer.getRegionWidth() * 6f, framePlayer.getRegionHeight() * 6f);
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

    private void showAbilitiesDialog(Player player){
        AbilitiesDialog dialog = new AbilitiesDialog("", () -> {
            // Handle abilities here
        }, Assets.getSkin(), player);
        dialog.show(game.getStage());
    }

    private void evaluateCombat(){
        if(player.getHP() <= 0 && ally.getHP() <= 0){
            combatFinished = true;
            CombatLogic.defeat(player, enemy);
            if(playerManager.savePlayer(player) == 0){
                System.out.println("Player saved");
            }
            else{
                System.out.println("Player not saved");
            }
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
        allyHPLabel.setText("MP: " + ally.getHP());
        allyMPLabel.setText("MP: " + ally.getMP());
        enemyHPLabel.setText("HP: " + enemy.getHP());
        enemyMPLabel.setText("MP: " + enemy.getMP());

        playerHPBar.setValue(player.getHP());
        playerMPBar.setValue(player.getMP());
        allyHPBar.setValue(ally.getHP());
        allyMPBar.setValue(ally.getMP());
        enemyHPBar.setValue(enemy.getHP());
        enemyMPBar.setValue(enemy.getMP());
    }
}