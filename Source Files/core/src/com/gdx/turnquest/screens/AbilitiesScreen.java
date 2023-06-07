package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.assets.Assets;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.utils.PlayerManager;

import java.io.IOException;

import static com.gdx.turnquest.TurnQuest.*;

/**
 * A class for the abilities:
 * create abilities for the player,
 * manage ability points,
 * information about the abilities,
 * upgrade abilities and show level,
 * load and store level of each ability.
 *
 * @author Pablo
 * @author Ignacy
 */
public class AbilitiesScreen extends BaseScreen {

    // set all buttons to not clicked, need to be global
    boolean clicked1 = false;
    boolean clicked2 = false;
    boolean clicked3 = false;
    boolean clicked4 = false;

    // times each abilitie has been upgraded
    static int times1 = 0;
    static int times2 = 0;
    static int times3 = 0;
    static int times4 = 0;

    public AbilitiesScreen(final TurnQuest game) {
        super(game);
    }

    /**
     * A method that creates a table to show the abilities and informatio about them depending on the class of the player.
     * @return the abilities table
     */
    private Table createAbilitiesTable() throws IOException {
        Player player = game.getCurrentPlayer();
        int[] abilities = player.getAbilities();
        PlayerManager playerManager = new PlayerManager();

        // load player's abilities level
        times1 = abilities[0];
        times2 = abilities[1];
        times3 = abilities[2];
        times4 = abilities[3];

        // Set the position of the label relative to the size of the stage
        // calculate where the labels will be
        float centerX = getVirtualWidth() / 2f;
        float centerY = getVirtualHeight() * 0.65f;

        // tooltip labels that will be shown when hovering buttons when locked
        Label tt1 = new Label("", Assets.getSkin());
        Label tt2 = new Label("", Assets.getSkin());
        Label tt3 = new Label("", Assets.getSkin());
        Label tt4 = new Label("", Assets.getSkin());

        // set to not be shown while not  hovering
        tt1.setVisible(false);
        tt2.setVisible(false);
        tt3.setVisible(false);
        tt4.setVisible(false);

        // show the labels
        game.getStage().addActor(tt1);
        game.getStage().addActor(tt2);
        game.getStage().addActor(tt3);
        game.getStage().addActor(tt4);

        // abilities buttons
        TextButton bAb1 = new TextButton("", Assets.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Pierce");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Root");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Critical");
        }

        TextButton bAb2 = new TextButton("", Assets.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Spin");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Poison");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Water Stab");
        }

        TextButton bAb3 = new TextButton("", Assets.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Fire Spin");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Multiple");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Wave");
        }

        TextButton bAb4 = new TextButton("", Assets.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Fire Sword");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Ray");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Tear Down");
        }

        // set color to locked
        bAb1.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb2.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb3.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb4.setColor(0.3f, 0.7f, 0.8f, 0.5f);

        // load former abilities
        if (0 <= times1 - 1) {
            clicked1 = true;
            bAb1.setChecked(true);
            bAb1.setColor(0.3f, 0.7f, 0.8f, 1);
        }

        if (0 <= times2 - 1) {
            clicked2 = true;
            bAb2.setChecked(true);
            bAb2.setColor(0.3f, 0.7f, 0.8f, 1);
        }

        if (0 <= times3 - 1) {
            clicked3 = true;
            bAb3.setChecked(true);
            bAb3.setColor(0.3f, 0.7f, 0.8f, 1);
        }

        if (0 <= times4 - 1) {
            clicked4 = true;
            bAb4.setChecked(true);
            bAb4.setColor(0.3f, 0.7f, 0.8f, 1);
        }

        // Abilities table
        Table abilitiesTable = new Table();
        abilitiesTable.defaults().size(getVirtualWidth() * 0.15f, getVirtualHeight() * 0.1f).pad(20);
        abilitiesTable.setFillParent(true);

        // order the buttons of the table
        abilitiesTable.add(bAb1);
        abilitiesTable.add(bAb2).row();
        abilitiesTable.add(bAb3);
        abilitiesTable.add(bAb4);

        abilitiesTable.padTop(100f); // add some padding at the top

        // buttons' listeners
        // listener of button one when hovered
        bAb1.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked1) {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Already unlocked. It increase the critical damage 0.5% each upgrade, total: " + (times1 - 1) * 0.5 + "%.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + (times1 - 1) * 0.5 + "%.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Already unlocked. It increase the critical damage 0.5% each upgrade, total: " + (times1 - 1) * 0.5 + "%.");
                    }
                } else {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Makes you next attack ignore enemy's defense.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Throw an rooting attack that reduce 25% the defense of the enemy.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Makes you next attack to be a critical one.");
                    }
                }

                tt1.setWidth(tt1.getText().length() * 12);
                tt1.setPosition(centerX - tt1.getWidth() / 2f, centerY - tt1.getHeight() / 2f);
                tt1.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
                tt1.setVisible(false);
            }
        });

        // listener of button one when clicked
        bAb1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.getAbilityPoints() > 0) {
                    // unlocked
                    bAb1.setColor(0.3f, 0.7f, 0.8f, 1);
                    clicked1 = true;
                    times1++;
                    player.increaseAbility(0);
                    player.decreaseAbilityPoints();
                    playerManager.savePlayer(player);
                }
            }
        });

        // listener of button two when hovered
        bAb2.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked2) {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + (times2 - 1) * 0.5 + "%.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + (times2 - 1) * 0.5 + "%.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + (times2 - 1) * 0.5 + "%.");
                    }
                } else {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Spin around you to inflict damage three times with 50% of your attack.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Throw a poison attack that inflicts posison to the enemy reducing its attack 10%.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Attack one enemy with a stab dealing 150% of the damage and a 15% chance of freezing.");
                    }
                }

                tt2.setWidth(tt2.getText().length() * 12);
                tt2.setPosition(centerX - tt2.getWidth() / 2f, centerY - tt2.getHeight() / 2f);
                tt2.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
                tt2.setVisible(false);
            }
        });

        // listener of button two when clicked
        bAb2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.getAbilityPoints() > 0) {
                    // unlocked
                    bAb2.setColor(0.3f, 0.7f, 0.8f, 1);
                    clicked2 = true;
                    times2++;
                    player.increaseAbility(1);
                    player.decreaseAbilityPoints();
                    playerManager.savePlayer(player);
                }
            }
        });

        // listener of button three when hovered
        bAb3.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked3) {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + (times3 - 1) * 0.5 + "%.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Already unlocked. It increase the damage 0.3% each upgrade, total: " + (times3 - 1) * 0.5 + "%.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + (times3 - 1) * 0.5 + "%.");
                    }
                } else {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Spin around you to inflict damage three times with 40% of your attack. It also burn enemies.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Throw a rain of arrows that damage three times with the 50% of the attack attack.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Throw a wave in front of you that deals damage three times with a 50% of the damage.");
                    }
                }

                tt3.setWidth(tt3.getText().length() * 12);
                tt3.setPosition(centerX - tt3.getWidth() / 2f, centerY - tt3.getHeight() / 2f);
                tt3.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
                tt3.setVisible(false);
            }
        });

        // listener of button three when clicked
        bAb3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.getAbilityPoints() > 0) {
                    // unlocked
                    bAb3.setColor(0.3f, 0.7f, 0.8f, 1);
                    clicked3 = true;
                    times3++;
                    player.increaseAbility(2);
                    player.decreaseAbilityPoints();
                    playerManager.savePlayer(player);
                }
            }
        });

        // listener of button four when hovered
        bAb4.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked4) {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Already unlocked. It increase the damage and fire damage 0.5% each upgrade, total: " + (times4 - 1) * 0.5 + "%.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + (times4 - 1) * 0.5 + "%.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + (times4 - 1) * 0.5 + "%.");
                    }
                } else {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Attack one enemy dealing 200% of your attack and burning it.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Invoke a green ray from the power of nature that makes 250% of the damage.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Invoke a giant tear that first makes 200% of the damage and then froze the enemy.");
                    }
                }

                tt4.setWidth(tt4.getText().length() * 12);
                tt4.setPosition(centerX - tt4.getWidth() / 2f, centerY - tt4.getHeight() / 2f);
                tt4.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
                tt4.setVisible(false);
            }
        });

        // listener of button four when clicked
        bAb4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (player.getAbilityPoints() > 0) {
                    // unlocked
                    bAb4.setColor(0.3f, 0.7f, 0.8f, 1);
                    clicked4 = true;
                    times4++;
                    player.increaseAbility(3);
                    player.decreaseAbilityPoints();
                    playerManager.savePlayer(player);
                }
            }
        });

        return abilitiesTable;
    }

    private Table createNavigationTable() {
        // table buttons
        TextButton bReturn = new TextButton("Return", Assets.getSkin());

        // table for navigation
        Table navigationTable = new Table();
        // add some padding and expand each cell
        navigationTable.defaults().expand().size(getVirtualWidth() *0.15f, getVirtualHeight() *.10f);
        navigationTable.setFillParent(true);

        BitmapFont font = Assets.getSubtitleFont();
        Label.LabelStyle style = new Label.LabelStyle(Assets.getSkin().get(Label.LabelStyle.class));
        style.font = font;
        Label title = new Label("Abilities", style);
        title.setFontScale(1.5f);
        title.setAlignment(Align.center);

        navigationTable.row().expandY();
        navigationTable.add(title).expandY();
        // order the buttons of the table
        navigationTable.row();
        navigationTable.add(bReturn).bottom().center();

        // return to GameScreen when pressed return button
        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.popScreen();
            }
        });

        return navigationTable;
    }

    public Table createUIComponents() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        // Abilities table
        Table abilitiesTable = null;
        try {
            abilitiesTable = createAbilitiesTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainTable.add(abilitiesTable).padTop(100f).expandY().top().row();

        // Navigation table
        Table navigationTable = createNavigationTable();
        mainTable.add(navigationTable).expandY().bottom();

        return mainTable;
    }

    @Override
    public void show() {
        Assets.loadFor(AbilitiesScreen.class);
        Assets.ASSET_MANAGER.finishLoading();
        Assets.setBackgroundTexture(new Texture(Gdx.files.internal(Assets.FOREST_BACKGROUND_PNG)));

        game.setStage(new Stage(getViewport()));

        try {
            game.getStage().addActor(createAbilitiesTable());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        game.getStage().addActor(createNavigationTable());
        //add a tutorial button to the top left of the screen
        game.getStage().addActor(tutorialButton("abilities"));
        getViewport().apply();
        super.show();
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(Assets.getBackgroundTexture(Assets.FOREST_BACKGROUND_PNG), 0, 0, getVirtualWidth(), getVirtualHeight());
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        handleKeyboardInput();
    }
}
