package com.gdx.turnquest.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gdx.turnquest.logic.CombatLogic;
import com.gdx.turnquest.entities.Player;
import com.gdx.turnquest.entities.Enemy;

public class CombatLogicTest {
    private Player player;
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        player = new Player();
        enemy = new Enemy();

        player.setHP(100);
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
        player.setLUK(100);
        enemy.setDEF(Integer.MAX_VALUE);
        CombatLogic.attack(player, enemy);
        assertEquals(50, enemy.getHP()); //50 damage since crit ignores DEF.
    }
}
