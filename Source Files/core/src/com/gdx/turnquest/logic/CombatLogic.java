package com.gdx.turnquest.logic;

import com.gdx.turnquest.entities.Character;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.entities.Ally;
import com.gdx.turnquest.screens.AbilitiesScreen;

public class CombatLogic {
    public static void attack(Character attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5) - defender.getDEF()/2;
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

    // abilities attacks
    // warrior abilities
    public static void pierceAttack(Character attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 + attacker.getSTR() * AbilitiesScreen.getTimes1() * 0.05);
        }
        else{
            damage = (int) (attacker.getSTR() + attacker.getSTR() * AbilitiesScreen.getTimes1() * 0.05);
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        attacker.setMP(attacker.getMP() - 5);
    }

    public static void spinAttack(Character attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 0.5 * 3 + attacker.getSTR() * AbilitiesScreen.getTimes2() * 0.05 - defender.getDEF()/2);
        }
        else{
            damage = (int) (attacker.getSTR() * 0.5 * 3 + attacker.getSTR() * AbilitiesScreen.getTimes2() * 0.05 - defender.getDEF()/2);
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        attacker.setMP(attacker.getMP() - 8);
    }

    public static void fireSpinAttack(Character attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 0.4 * 3 + attacker.getSTR() * AbilitiesScreen.getTimes3() * 0.05 + attacker.getSTR() * 0.2 - defender.getDEF()/2);
        }
        else {
            damage = (int) (attacker.getSTR() * 0.4 * 3 + attacker.getSTR() * AbilitiesScreen.getTimes3() * 0.05 + attacker.getSTR() * 0.2 - defender.getDEF()/2);
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        attacker.setMP(attacker.getMP() - 10);
    }

    public static void fireSwordAttack(Character attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 2 + attacker.getSTR() * AbilitiesScreen.getTimes4() * 0.05 + attacker.getSTR() * 0.2) - defender.getDEF()/2;
        }
        else {
            damage = (int) (attacker.getSTR() * 2 + attacker.getSTR() * AbilitiesScreen.getTimes4() * 0.05 + attacker.getSTR() * 0.2) - defender.getDEF()/2;
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        attacker.setMP(attacker.getMP() - 15);
    }

    // archer abilities
    public static void rootAttack(Character attacker, Character defender) {}

    public static void poisonAttack(Character attacker, Character defender) {}

    public static void multipleAttack(Character attacker, Character defender) {}

    public static void rayAttack(Character attacker, Character defender) {}

    // mage abilities
    public static void criticalAttack(Character attacker, Character defender) {}

    public static void waterStabAttack(Character attacker, Character defender) {}

    public static void waveAttack(Character attacker, Character defender) {}

    public static void tearDownAttack(Character attacker, Character defender) {}

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
