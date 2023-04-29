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
            damage = attacker.getSTR() - defender.getDEF();
            if(damage < 0){
                damage = 0;
            }
        }
        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }
    }

    public static void magicAttack(Character attacker, Character defender) {
        int damage = attacker.getINT() - defender.getINT();
        if (damage < 0) {
            damage = 0;
        }
        defender.setHP(defender.getHP() - damage);
    }

    public static void heal(Character healer, Character target) {
        int heal = healer.getINT();
        target.setHP(target.getHP() + heal);
    }

    public static void levelUp(Player character, int exp, int level, int hp, int mp, int str, int def, int spd, int intel, int luk) {
        character.setExp(character.getExp() + exp);
        //Increases 1 or more levels if the exp is greater than or equal to the exp needed to level up, and then sets the exp to the remainder.
        while (character.getExp() >= character.expNeeded()) {
            character.increaseLevel(level);
            character.setExp(character.getExp() - character.expNeeded());
            character.setHP(character.getHP() + hp);
            character.setMP(character.getMP() + mp);
            character.setSTR(character.getSTR() + str);
            character.setDEF(character.getDEF() + def);
            character.setSPD(character.getSPD() + spd);
            character.setINT(character.getINT() + intel);
            character.setLUK(character.getLUK() + luk);
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
