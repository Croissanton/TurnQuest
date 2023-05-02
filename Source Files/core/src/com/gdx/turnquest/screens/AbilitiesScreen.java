package com.gdx.turnquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.turnquest.TurnQuest;
import com.gdx.turnquest.entities.Player;

import static com.gdx.turnquest.TurnQuest.*;

public class AbilitiesScreen implements Screen {

    final TurnQuest game;


    // set all buttons to not clicked, need to be global
    boolean clicked1 = false;
    boolean clicked2 = false;
    boolean clicked3 = false;
    boolean clicked4 = false;

    // times each abilitie has been upgraded
    int times1 = -1;
    int times2 = -1;
    int times3 = -1;
    int times4 = -1;

    public AbilitiesScreen (final TurnQuest game) {
        this.game = game;
        Player player = game.getCurrentPlayer();

        game.setBackgroundTexture(new Texture(Gdx.files.internal("Pixel art forest/Preview/Background.png")));

        game.setStage(new Stage(getViewport()));

        // Set the position of the label relative to the size of the stage
        // calculate where the labels will be
        float centerX = getVirtualWidth() / 2f;
        float centerY = getVirtualHeight() * 0.65f;

        // tooltip labels that will be shown when hovering buttons when locked
        Label tt1 = new Label("", game.getSkin());
        Label tt2 = new Label("", game.getSkin());
        Label tt3 = new Label("", game.getSkin());
        Label tt4 = new Label("", game.getSkin());

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
        TextButton bAb1 = new TextButton("", game.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Critical");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Root");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb1.setText("Critical");
        }

        TextButton bAb2 = new TextButton("", game.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Spin");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Poison");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb2.setText("Water Stab");
        }

        TextButton bAb3 = new TextButton("", game.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Fire Spin");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Multiple");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb3.setText("Wave");
        }

        TextButton bAb4 = new TextButton("", game.getSkin());
        if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Fire Sword");
        } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Green Ray");
        } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
            bAb4.setText("Tear Down");
        }

        // set color to locked
        bAb1.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb2.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb3.setColor(0.3f, 0.7f, 0.8f, 0.5f);
        bAb4.setColor(0.3f, 0.7f, 0.8f, 0.5f);

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

        game.getStage().addActor(abilitiesTable);

        // buttons' listeners
        // listener of button one when hovered
        bAb1.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked1) {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Already unlocked. It increase the critical damage 0.5% each upgrade, total: " + times1 * 0.5 + "%.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + times1 * 0.5 + "%.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Already unlocked. It increase the critical damage 0.5% each upgrade, total: " + times1 * 0.5 + "%.");
                    }
                } else {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Makes you next attack to be a critical one.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Throw an rooting attack that stuns the enemy for one turn.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt1.setText("Makes you next attack to be a critical one.");
                    }
                }

                tt1.setWidth(tt1.getText().length() * 12);
                tt1.setPosition(centerX - tt1.getWidth() / 2f, centerY - tt1.getHeight() / 2f);
                System.out.println(centerX + "-" + tt1.getWidth() / 2f + "=" + (centerX - tt1.getWidth() / 2f));
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
                // unlocked
                bAb1.setColor(0.3f, 0.7f, 0.8f, 1);
                clicked1 = true;
                times1++;
            }
        });

        // listener of button two when hovered
        bAb2.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked2) {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + times2 * 0.5 + "%.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Already unlocked. It increase the poison damage 0.5% each upgrade, total: " + times2 * 0.5 + "%.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + times2 * 0.5 + "%.");
                    }
                } else {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Spin around you to inflict damage to all enemies with 60% of your attack.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Throw a poison attack that inflicts posison to the enemy for three turns.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt2.setText("Attack one enemy with a stab dealing 140% of the damage and a 15% chance of freezing.");
                    }
                }

                tt2.setWidth(tt2.getText().length() * 12);
                tt2.setPosition(centerX - tt2.getWidth() / 2f, centerY - tt2.getHeight() / 2f);
                System.out.println(centerX + "-" + tt2.getWidth() / 2f + "=" + (centerX - tt2.getWidth() / 2f));
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
                // unlocked
                bAb2.setColor(0.3f, 0.7f, 0.8f, 1);
                clicked2 = true;
                times2++;
            }
        });

        // listener of button three when hovered
        bAb3.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked3) {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + times3 * 0.5 + "%.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Already unlocked. It increase the damage 0.3% each upgrade, total: " + times3 * 0.3 + "%.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + times3 * 0.5 + "%.");
                    }
                } else {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Spin around you to inflict damage to all enemies with 50% of your attack. It also burn enemies.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Throw a rain of arrows that damage all the enemies with the 60% of the attack attack.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt3.setText("Throw a wave in front of you that deals damage to all enemies with a 60% of the damage.");
                    }
                }

                tt3.setWidth(tt3.getText().length() * 12);
                tt3.setPosition(centerX - tt3.getWidth() / 2f, centerY - tt3.getHeight() / 2f);
                System.out.println(centerX + "-" + tt3.getWidth() / 2f + "=" + (centerX - tt3.getWidth() / 2f));
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
                // unlocked
                bAb3.setColor(0.3f, 0.7f, 0.8f, 1);
                clicked3 = true;
                times3++;
            }
        });

        // listener of button four when hovered
        bAb4.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
                if (clicked4) {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Already unlocked. It increase the damage and fire damage 0.5% each upgrade, total: " + times4 * 0.5 + "%.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + times4 * 0.5 + "%.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Already unlocked. It increase the damage 0.5% each upgrade, total: " + times4 * 0.5 + "%.");
                    }
                } else {
                    if ("Warrior".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Attack one enemy dealing 150% of your attack and burning it.");
                    } else if ("Archer".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Invoke a green ray from the power of nature that makes 180% of the damage.");
                    } else if ("Mage".equalsIgnoreCase(player.getCharacterClass())) {
                        tt4.setText("Invoke a giant tear that first makes 70% of the damage and then has a 80% of freezing.");
                    }
                }

                tt4.setWidth(tt4.getText().length() * 12);
                tt4.setPosition(centerX - tt4.getWidth() / 2f, centerY - tt4.getHeight() / 2f);
                System.out.println(centerX + "-" + tt4.getWidth() / 2f + "=" + (centerX - tt4.getWidth() / 2f));
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
                // unlocked
                bAb4.setColor(0.3f, 0.7f, 0.8f, 1);
                clicked4 = true;
                times4++;
            }
        });

        // table buttons
        TextButton bReturn = new TextButton("Return", game.getSkin());
        TextButton bLeftArrow = new TextButton("<-", game.getSkin());
        TextButton bRightArrow = new TextButton("->", game.getSkin());

        // table for return
        Table table = new Table();
        // add some padding and expand each cell
        table.defaults().expand().pad(50);
        table.setFillParent(true);

        // order the buttons of the table
        table.add(bLeftArrow).left();
        table.add();
        table.add(bRightArrow).right();
        table.row();
        table.add();
        table.add(bReturn).bottom();

        game.getStage().addActor(table);

        // if an arrow is clicked, go to abilities screen
        bRightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });

        bLeftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });

        // return to GameScreen when pressed return button
        bReturn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        getViewport().apply();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(game.getStage());
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.3f, 0.7f, 0.8f, 1); // You can also write a color here, this is the background.

        getCamera().update();
        game.getBatch().setProjectionMatrix(getCamera().combined);

        game.getBatch().begin();
        game.getBatch().draw(game.getBackgroundTexture(), 0, 0, getVirtualWidth(), getVirtualHeight());
        game.getFont().getData().setScale(4); //Changes font size.
        game.getFont().draw(game.getBatch(), "Abilities", getVirtualWidth() * 45f / 100f, getVirtualHeight() * 85f / 100f);
        game.getBatch().end();

        game.getStage().act();
        game.getStage().draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
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
