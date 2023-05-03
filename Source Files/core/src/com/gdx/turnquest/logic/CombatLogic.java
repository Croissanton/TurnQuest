package com.gdx.turnquest.logic;

import com.gdx.turnquest.entities.Character;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.entities.Ally;

public class CombatLogic {
    public static void attack(Character attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = attacker.getSTR();
        }
        else{
            damage = (attacker.getSTR() - defender.getDEF()/2);
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }
        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }
    }

    public static void magicAttack(Character attacker, Character defender) {
        int damage = attacker.getINT() - defender.getINT()/2;
        attacker.setMP(attacker.getMP() - damage/2);
        if (damage <= 0) {
            damage = 1;
            attacker.setMP(attacker.getMP() - 1);
        }
        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }
        //THIS IS AN EXAMPLE, WE SHOULD DO ABILITIES INSTEAD OF THIS.
    }

    public static void heal(Character healer, Character target) {
        int heal = healer.getINT();
        target.setHP(target.getHP() + heal);
    }

    public static void increaseEXP(Player player, int exp) {
        player.setExp(player.getExp() + exp);
        //Increases 1 or more levels if the exp is greater than or equal to the exp needed to level up, and then sets the exp to the remainder.
        while (player.getExp() >= player.expNeeded()) {
            player.setExp(player.getExp() - player.expNeeded());
            player.increaseLevel();
        }
    }
    public static void useItem(Character character, String item) {
//        if (item.equals("Potion")) {
//            character.setHP(character.getHP() + 50);
//        }
//        if (item.equals("Ether")) {
//            character.setMP(character.getMP() + 50);
//        }
        //This may be further developed by creating an Item class and then doing, if item.usable(), then use item and add stats of the item to the character.
    }

    public static boolean run(Character character, Enemy enemy) {
        //generate random number based in SPD, between 0 and 100 (if luk is higher it increases)
        int escapeChance = (int) (Math.random() * 100);
        if (escapeChance <= character.getSPD() - enemy.getSPD()/2) {
            return true;
        }
        else{
            return false;
        }
    }
}
