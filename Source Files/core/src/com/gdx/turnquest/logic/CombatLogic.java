package com.gdx.turnquest.logic;

import com.gdx.turnquest.entities.Character;
import com.gdx.turnquest.entities.Enemy;
import com.gdx.turnquest.entities.Player;

/**
 * This class contains the logic for the combat system.
 * It contains the methods to calculate the damage dealt by the characters with normal attacks.
 * This method can also be called by the enemies.
 * It also contains the methods to calculate the damage dealt by the abilities.
 * The method bossAttack is used by the boss to attack all the players.
 * This class is used in the CombatScreen and BossScreen classes.
 *
 * @author Cristian
 * @author Pablo
 */
public class CombatLogic {
    public static int attack(Character attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5) - defender.getDEF()/2;
        }
        else{
            damage = (attacker.getSTR() - defender.getDEF()/2);
        }
        dealDamage(defender, damage);
        //Return 1 if critical hit, 0 if not.
        return critChance <= attacker.getLUK() - defender.getLUK()/2 ? 1 : 0;
    }

    public static void bossAttack(Character attacker, Character[] defenders) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        for(Character defender: defenders){
            attack(attacker, defender);
        }
    }

    // abilities attacks
    public static void useAbility(Player attacker, Character defender, int ability, int MPcost) {
        attacker.setMP(attacker.getMP() - MPcost);
        switch (attacker.getCharacterClass()) {
            case "Warrior":
                switch (ability) {
                    case 0:
                        dealDamage(defender, pierceAttack(attacker, defender));
                        break;
                    case 1:
                        dealDamage(defender, spinAttack(attacker, defender));
                        break;
                    case 2:
                        dealDamage(defender, fireSpinAttack(attacker, defender));
                        break;
                    case 3:
                        dealDamage(defender, fireSwordAttack(attacker, defender));
                        break;
                    default:
                        break;
                }
                break;
            case "Archer":
                switch (ability) {
                    case 0:
                        dealDamage(defender, rootAttack(attacker, defender));
                        break;
                    case 1:
                        dealDamage(defender, poisonAttack(attacker, defender));
                        break;
                    case 2:
                        dealDamage(defender, multipleAttack(attacker, defender));
                        break;
                    case 3:
                        dealDamage(defender, rayAttack(attacker, defender));
                        break;
                    default:
                        break;
                }
                break;
            case "Mage":
                switch (ability) {
                    case 0:
                        dealDamage(defender, criticalAttack(attacker, defender));
                        break;
                    case 1:
                        dealDamage(defender, waterStabAttack(attacker, defender));
                        break;
                    case 2:
                        dealDamage(defender, waveAttack(attacker, defender));
                        break;
                    case 3:
                        dealDamage(defender, tearDownAttack(attacker, defender));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
    // warrior abilities
    private static int pierceAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 + attacker.getSTR() * attacker.getAbility(0) * 0.05);
        }
        else{
            damage = (int) (attacker.getSTR() + attacker.getSTR() * attacker.getAbility(0) * 0.05);
        }
        return damage;
    }

    private static int spinAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 0.5 * 3 + attacker.getSTR() * attacker.getAbility(1) * 0.05 - defender.getDEF()/2);
        }
        else{
            damage = (int) (attacker.getSTR() * 0.5 * 3 + attacker.getSTR() * attacker.getAbility(1) * 0.05 - defender.getDEF()/2);
        }
        return damage;
    }

    private static int fireSpinAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 0.4 * 3 + attacker.getSTR() * attacker.getAbility(2) * 0.05 + attacker.getSTR() * 0.2 * 3 - defender.getDEF()/2);
        }
        else {
            damage = (int) (attacker.getSTR() * 0.4 * 3 + attacker.getSTR() * attacker.getAbility(2) * 0.05 + attacker.getSTR() * 0.2 * 3 - defender.getDEF()/2);
        }
        return damage;
    }

    private static int fireSwordAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 2 + attacker.getSTR() * attacker.getAbility(3) * 0.05 + attacker.getSTR() * 0.2) - defender.getDEF()/2;
        }
        else {
            damage = (int) (attacker.getSTR() * 2 + attacker.getSTR() * attacker.getAbility(3) * 0.05 + attacker.getSTR() * 0.2) - defender.getDEF()/2;
        }
        return damage;
    }

    // archer abilities
    private static int rootAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 + attacker.getSTR() * attacker.getAbility(0) * 0.05)  - defender.getDEF()/2;
        }
        else{
            damage = (int) (attacker.getSTR() + attacker.getSTR() * attacker.getAbility(0) * 0.05) - defender.getDEF()/2;
        }
        defender.setDEF((int) (defender.getDEF() * 0.75));
        return damage;
    }

    private static int poisonAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 + attacker.getSTR() * attacker.getAbility(1) * 0.05 + attacker.getSTR() * 0.1 * 3) - defender.getDEF()/2;
        }
        else {
            damage = (int) (attacker.getSTR() + attacker.getSTR() * attacker.getAbility(1) * 0.05 + attacker.getSTR() * 0.1 * 3) - defender.getDEF()/2;
        }

        defender.setSTR((int) (defender.getSTR() * 0.9));
        return damage;
    }

    private static int multipleAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getSTR() * 1.5 * 0.5 * 3 + attacker.getSTR() * attacker.getAbility(2) * 0.05 - defender.getDEF()/2);
        }
        else{
            damage = (int) (attacker.getSTR() * 0.5 * 3 + attacker.getSTR() * attacker.getAbility(2) * 0.05 - defender.getDEF()/2);
        }
        return damage;
    }

    private static int rayAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() + attacker.getSTR() * attacker.getAbility(3) * 0.05 - defender.getLUK()/2f) {
            damage = (int) (attacker.getSTR() * 1.5 * 2.5) - defender.getDEF()/2;
        }
        else{
            damage = (int) (attacker.getSTR() * 2.5 + attacker.getSTR() * attacker.getAbility(3) * 0.05 - defender.getDEF()/2);
        }
        return damage;
    }

    // mage abilities
    private static int criticalAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int damage;

        damage = (int) (attacker.getINT() * 1.5 + attacker.getINT() * attacker.getAbility(0) - defender.getDEF()/2);
        return damage;
    }

    private static int waterStabAttack(Player attacker, Character defender) {
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
        }
        return damage;
    }

    private static int waveAttack(Player attacker, Character defender) {
        //generate random number based in LUK, between 0 and 100 (if luk is higher it increases)
        int critChance = (int) (Math.random() * 100);
        int damage;
        if (critChance <= attacker.getLUK() - defender.getLUK()/2) {
            damage = (int) (attacker.getINT() * 1.5 * 0.5 * 3 + attacker.getINT() * attacker.getAbility(2) * 0.05 - defender.getDEF()/2);
        }
        else{
            damage = (int) (attacker.getINT() * 0.5 * 3 + attacker.getINT() * attacker.getAbility(2) * 0.05 - defender.getDEF()/2);
        }
        return damage;
    }

    private static int tearDownAttack(Player attacker, Character defender) {
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
        }
        return damage;
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
        switch (item){
            case "Potion":
                character.setHP(character.getHP() + 50);
                break;
            case "Potion +":
                character.setHP(character.getHP() + 100);
                break;
            case "Potion ++":
                character.setHP(character.getHP() + 9999);
                break;
            case "Ether":
                character.setMP(character.getMP() + 50);
                break;
            case "Ether +":
                character.setMP(character.getMP() + 100);
                break;
            case "Ether ++":
                character.setMP(character.getMP() + 9999);
                break;
        }
        //This may be further developed by creating an Item class and then doing, if item.usable(), then use item and add stats of the item to the character.
    }

    public static boolean run(Character character, Character enemy) {
        //generate random number based in SPD, between 0 and 100 (if luk is higher it increases)
        int escapeChance = (int) (Math.random() * 100);
        return escapeChance <= 20 + (character.getSPD() - enemy.getSPD() / 15);
    }

    public static void defeat(Player player, Enemy enemy) {
        // YOU LOST :( Do whatever is necessary maybe game over screen or something.
    }

    public static void victory(Player player, Enemy enemy) {
        increaseEXP(player, enemy.getExp());
        player.addGold(enemy.getGold());
    }
    private static void dealDamage(Character defender, int damage){
        if(damage <= 0){
            damage = 1; //Always do at least 1 damage.
        }
        defender.setHP(defender.getHP() - damage);
        if(defender.getHP() < 0){
            defender.setHP(0);
        }
    }
}
