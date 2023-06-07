package com.gdx.turnquest.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gdx.turnquest.assets.Assets;


/**
 * This class is responsible for showing a tutorial dialog.
 * It shows a tutorial for each screen in our game.
 *
 * @author Ignacy
 */

public class TutorialDialog extends Dialog {


    public TutorialDialog(String title, Skin skin, String tutorial) {
        super(title, skin);
        Table table = createTutorial(tutorial);
        setMovable(false);
        setResizable(false);
        getContentTable().add(table);
        button("Close");
    }

    private Table createTutorial(String tutorial) {
        Table table = new Table(Assets.getSkin());
        switch (tutorial) {
            case "mainmenu":
                table.add("Welcome to TurnQuest!").center().row();
                table.add("This is the main menu. From here you can access the login and signup dialogs.").center().row();
                table.add("If you haven't already, you can create an account by clicking the signup button.").center().row();
                table.add("There, you will be able to chose your character class and name.").center().row();
                table.add("Once you have created your account, you can login and start playing!").center().row();
                break;
            case "combat":
                table.add("This is the combat screen.").center().row();
                table.add("When fighting together with your clan, you can see your clanmate's character as well.").center().row();
                table.add("Each character on the screen will take turns attacking.").center().row();
                table.add("Your HP will decrease when the enemy attacks. However, both you and the opponent can miss, resulting in no HP loss.").center().row();
                table.add("Using abilities will use up your mana points. You can use items from your inventory to restore your HP or mana.").center().row();
                table.add("If you feel like you will lose the battle, you can try running.").center().row();
                table.add("Winning awards you with XP and gold. There is no penalty for losing.").center().row();
                break;
            case "abilities":
                table.add("This is the abilities screen. Here you can see and upgrade your character's abilities.").center().row();
                table.add("Each ability has a level. The higher the level, the more powerful the ability.").center().row();
                table.add("You can upgrade your abilities using ability points. You get one ability point every 5 levels").center().row();
                break;
            case "inventory":
                table.add("This is the inventory screen. Here you can see your character's inventory.").center().row();
                table.add("You can use items from your inventory to restore your HP or mana.").center().row();
                table.add("Items also increase your character's stats.").center().row();
                table.add("You can also sell items for gold in the shop.").center().row();
                break;
            case "clan":
                table.add("This is the clan screen. Here you can see your clan's name and members.").center().row();
                table.add("Clans are groups of players that can fight together against strong enemies").center().row();
                table.add("You can join a clan by clicking the join clan button.").center().row();
                table.add("You can also create a clan by clicking the create clan button.").center().row();
                table.add("You can only be in one clan at a time.").center().row();
                break;
            case "shop":
                table.add("This is the shop screen. Here you can buy and sell items.").center().row();
                table.add("Items are useful in combat, as they can restore your HP/Mana or increase the stats of you character.").center().row();
                table.add("You get gold by winning battles.").center().row();
                table.add("You can also sell items that you don't need for gold.").center().row();
                break;
            case "boss":
                table.add("This is the boss screen.").center().row();
                table.add("Bosses are strong enemies that you have to fight together with your clan.").center().row();
                table.add("Bosses have a lot of HP and can deal a lot of damage.").center().row();
                table.add("Defeating a boss awards you with way more XP and gold than defeating normal enemies.").center().row();
                break;
            case "game":
                table.add("This is the game menu screen.").center().row();
                table.add("From here you can access the combat, the shop, your clan or the player menu.").center().row();
                table.add("The battery represents your energy. You need energy to fight enemies.").center().row();
                table.add("Each battle consumes one energy, doesn't matter if you win, lose or run. The energy is restored after 24 hours.").center().row();
                break;
            case "map":
                table.add("This is the combat menu.").center().row();
                table.add("Click on the 'Enemy' button to start a battle.").center().row();
                table.add("Click on the 'Boss' button to start a boss fight. (Keep in mind you can't fight bosses alone. Join a clan to have someone to fight with)").center().row();
                table.add("Remember! Each fight will decrease your energy level. After reaching 0, you will not be able to fight for the rest of the day.").center().row();
                break;
            case "player":
                table.add("This is the player menu.").center().row();
                table.add("Here you can see your character's stats, your level, and the clan you are in.").center().row();
                table.add("Win battles to get XP and level up.").center().row();
                table.add("Access the abilities screen to upgrade your abilities.").center().row();
                table.add("Access the inventory screen to see your items.").center().row();
                break;
        }
        return table;
    }

    @Override
    protected void result(Object object) {
        hide();
    }
}
