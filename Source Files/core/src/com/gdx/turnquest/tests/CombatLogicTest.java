package com.gdx.turnquest.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.gdx.turnquest.entities.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gdx.turnquest.logic.CombatLogic;

import com.gdx.turnquest.entities.Character;

/**
 * This class tests the CombatLogic class.
 * It tests the attack and useAbility methods.
 * It tests the damage calculation and the critical hit calculation.
 * It tests the damage calculation for abilities.
 * It also tests the limit of the damage calculation.
 *
 * @author Cristian
 */
public class CombatLogicTest {
    private Character player;
    private Character enemy;
    Player warrior;

    //Both player and enemy are set as characters since Player and Enemy are both subclasses of Character. So this is a good way to test both.

    @BeforeEach
    public void setUp() {
        player = new Character();
        enemy = new Character();
        warrior = new Player("test", "Warrior");


        enemy.setHP(100);
    }

    @AfterEach
    public void tearDown() {
        player = null;
        enemy = null;
    }

    //ATTACK TESTS
    @Test
    public void testAttackMinDMG() {
        player.setSTR(0);
        CombatLogic.attack(player, enemy);
        assertEquals(99, enemy.getHP()); //At least 1 damage always.
    }

    @Test
    public void testAttackMaxDMG() {
        player.setSTR(Integer.MAX_VALUE);
        CombatLogic.attack(player, enemy);
        assertEquals(0, enemy.getHP()); //Damage does not exceed enemy HP.
    }

    @Test
    public void testAttackCrit() {
        player.setSTR(50);
        player.setLUK(10000);
        enemy.setLUK(0);
        enemy.setDEF(0);
        CombatLogic.attack(player, enemy);
        assertEquals(25, enemy.getHP()); //75 damage since damage is increased x1.5 when crit.
    }

    //ABILITIES TEST
    @Test
    public void testAbilityMinDMG() {
        warrior.setSTR(0);
        enemy.setDEF(Integer.MAX_VALUE);
        CombatLogic.useAbility(warrior, enemy, 1, 0);
        assertEquals(99, enemy.getHP()); //At least 1 damage always.
    }

    @Test
    public void testAbilityMaxDMG() {
        warrior.setSTR(Integer.MAX_VALUE);
        enemy.setDEF(0);
        CombatLogic.useAbility(warrior, enemy, 1, 0);
        CombatLogic.attack(player, enemy);
        assertEquals(0, enemy.getHP()); //Damage does not exceed enemy HP.
    }
}
