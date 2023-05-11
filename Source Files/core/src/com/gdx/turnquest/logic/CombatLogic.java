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
    public static void pierceAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 + attacker.getSTR() * attacker.getAbility(0) * 0.05);
        }
        else{
            damage = (int) (attacker.getSTR() + attacker.getSTR() * attacker.getAbility(0) * 0.05);
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

    public static void spinAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 0.5 * 3 + attacker.getSTR() * attacker.getAbility(1) * 0.05 - defender.getDEF()/2);
        }
        else{
            damage = (int) (attacker.getSTR() * 0.5 * 3 + attacker.getSTR() * attacker.getAbility(1) * 0.05 - defender.getDEF()/2);
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

    public static void fireSpinAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 0.4 * 3 + attacker.getSTR() * attacker.getAbility(2) * 0.05 + attacker.getSTR() * 0.2 * 3 - defender.getDEF()/2);
        }
        else {
            damage = (int) (attacker.getSTR() * 0.4 * 3 + attacker.getSTR() * attacker.getAbility(2) * 0.05 + attacker.getSTR() * 0.2 * 3 - defender.getDEF()/2);
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        if (attacker.getMP() >= 10) {
            attacker.setMP(attacker.getMP() - 10);
        } else {
            attacker.setMP(0);
        }
    }

    public static void fireSwordAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 2 + attacker.getSTR() * attacker.getAbility(3) * 0.05 + attacker.getSTR() * 0.2) - defender.getDEF()/2;
        }
        else {
            damage = (int) (attacker.getSTR() * 2 + attacker.getSTR() * attacker.getAbility(4) * 0.05 + attacker.getSTR() * 0.2) - defender.getDEF()/2;
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }
        if (attacker.getMP() >= 15) {
            attacker.setMP(attacker.getMP() - 15);
        } else {
            attacker.setMP(0);
        }
    }

    // archer abilities
    public static void rootAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 + attacker.getSTR() * attacker.getAbility(0) * 0.05)  - defender.getDEF()/2;
        }
        else{
            damage = (int) (attacker.getSTR() + attacker.getSTR() * attacker.getAbility(0) * 0.05) - defender.getDEF()/2;
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        defender.setDEF((int) (defender.getDEF() * 0.25));

        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        if (attacker.getMP() >= 5) {
            attacker.setMP(attacker.getMP() - 5);
        } else {
            attacker.setMP(0);
        }
    }

    public static void poisonAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 + attacker.getSTR() * attacker.getAbility(1) * 0.05 + attacker.getSTR() * 0.1 * 3) - defender.getDEF()/2;
        }
        else {
            damage = (int) (attacker.getSTR() + attacker.getSTR() * attacker.getAbility(1) * 0.05 + attacker.getSTR() * 0.1 * 3) - defender.getDEF()/2;
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        defender.setSTR((int) (defender.getSTR() * 0.9));
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        if (attacker.getMP() >= 8) {
            attacker.setMP(attacker.getMP() - 8);
        } else {
            attacker.setMP(0);
        }
    }

    public static void multipleAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 0.5 * 3 + attacker.getSTR() * attacker.getAbility(2) * 0.05 - defender.getDEF()/2);
        }
        else{
            damage = (int) (attacker.getSTR() * 0.5 * 3 + attacker.getSTR() * attacker.getAbility(2) * 0.05 - defender.getDEF()/2);
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        if (attacker.getMP() >= 10) {
            attacker.setMP(attacker.getMP() - 10);
        } else {
            attacker.setMP(0);
        }
    }

    public static void rayAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() + attacker.getSTR() * attacker.getAbility(3) * 0.05 - defender.getLUK()/2f) {
            damage = (int) (attacker.getSTR() * 1.5 * 2.5) - defender.getDEF()/2;
        }
        else{
            damage = (int) (attacker.getSTR() * 2.5 + attacker.getSTR() * attacker.getAbility(3) * 0.05 - defender.getDEF()/2);
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        if (attacker.getMP() >= 15) {
            attacker.setMP(attacker.getMP() - 15);
        } else {
            attacker.setMP(0);
        }
    }

    // mage abilities
    public static void criticalAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int damage;

        damage = (int) (attacker.getINT() * 1.5 + attacker.getINT() * attacker.getAbility(0) - defender.getDEF()/2);

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        if (attacker.getMP() >= 5) {
            attacker.setMP(attacker.getMP() - 5);
        } else {
            attacker.setMP(0);
        }
    }

    public static void waterStabAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;

        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getINT() * 1.5 * 1.5 + attacker.getINT() * attacker.getAbility(1) * 0.05 - defender.getDEF()/2);
            // ice attack
            critChance = (int) (Math.random() * 100);
            if (critChance <= attacker.getLUK() - defender.getLUK()/1.5) {
                damage += (int) (attacker.getINT() * 0.5);
            }
        }
        else{
            damage = (int) (attacker.getINT() * 1.5 - defender.getDEF()/2);
            // ice attack
            critChance = (int) (Math.random() * 100);
            if (critChance <= attacker.getLUK() + attacker.getINT() * attacker.getAbility(1) * 0.05 - defender.getLUK()/1.5) {
                damage += (int) (attacker.getINT() * 0.5);
            }
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        if (attacker.getMP() >= 8) {
            attacker.setMP(attacker.getMP() - 8);
        } else {
            attacker.setMP(0);
        }
    }

    public static void waveAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getINT() * 1.5 * 0.5 * 3 + attacker.getINT() * attacker.getAbility(2) * 0.05 - defender.getDEF()/2);
        }
        else{
            damage = (int) (attacker.getINT() * 0.5 * 3 + attacker.getINT() * attacker.getAbility(2) * 0.05 - defender.getDEF()/2);
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        if (attacker.getMP() >= 10) {
            attacker.setMP(attacker.getMP() - 10);
        } else {
            attacker.setMP(0);
        }
    }

    public static void tearDownAttack(Player attacker, Enemy defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;

        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getINT() * 1.5 * 2.0 + attacker.getINT() * attacker.getAbility(3) * 0.05) - defender.getDEF()/2;
            // ice attack
            critChance = (int) (Math.random() * 100);
            if (critChance <= attacker.getLUK() - defender.getLUK()/1.5) {
                damage += (int) (attacker.getINT() * 0.5);
            }
        }
        else{
            damage = (int) (attacker.getINT() * 1.5 * 2.0 - defender.getDEF()/2);
            // ice attack
            critChance = (int) (Math.random() * 100);
            if (critChance <= attacker.getLUK() - defender.getLUK()/1.5) {
                damage += (int) (attacker.getINT() * 0.5 + attacker.getINT() * attacker.getAbility(3) * 0.05 );
            }
            if(damage <= 0){
                damage = 1; //Always do at least 1 damage
            }
        }

        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }

        if (attacker.getMP() >= 15) {
            attacker.setMP(attacker.getMP() - 15);
        } else {
            attacker.setMP(0);
        }
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
        if (escapeChance <= character.getSPD() - enemy.getSPD()/15) {
            return true;
        }
        else{
            return false;
        }
    }

    public static void defeat(Player player, Enemy enemy) {
        // YOU LOST :( Do whatever is necessary maybe game over screen or something.
    }

    public static void victory(Player player, Enemy enemy) {
        increaseEXP(player, enemy.getExp());
        player.addGold(enemy.getGold());
    }
}
