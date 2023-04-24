package com.gdx.turnquest.entities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Player {

    private static String playerName;

    private static final int N_STATS = 7; // Number of stats, TO BE CHANGED WHEN WE CONCLUDE THE NUMBER OF STATS.
    private static String characterClass = "";
    private static int gold;
    private static int exp;
    private static int level;
    private static int stats[] = new int[N_STATS]; //0 is HP, 1 is MP, 2 is STR, 3 is DEF, 4 is SPD, 5 is INT, 6 is LUK

    public Player(String fileName) {
        try {
            Scanner file = new Scanner(new FileReader("../" + fileName + ".txt"));
            playerName = file.nextLine();
            file.nextLine();
            characterClass = file.nextLine();
            gold = Integer.parseInt(file.nextLine());
            exp = Integer.parseInt(file.nextLine());
            level = Integer.parseInt(file.nextLine());
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File not found.");
        }
    }

    public static void setCharacterClass(String cc) {
        characterClass = cc;
    }

    public static String getCharacterClass() {
        return characterClass;
    }

    public static void addGold(int g) {
        gold =+ g;
    }

    public static int removeGold(int g) {
        if (gold < g) {
            return -1;
        }
        gold -= g;
        return 0;
        // 0 means success, -1 means not enough gold.
    }

    public static int getGold() {
        return gold;
    }

    public static void addExp(int e) {
        exp += e;
    }

    public static int getExp() {
        return exp;
    }

    public static void setLevel(int l) {level = l;}

    public static void increaseLevel(int l) {level += l;}

    public static int getLevel() {return level;}

    public static int getHP(){
        return stats[0];
    }

    public static void increaseHP(int hp){
        stats[0] += hp;
    }

    public static int getMP(){
        return stats[1];
    }

    public static void increaseMP(int mp){
        stats[1] += mp;
    }

    public static int getSTR(){
        return stats[2];
    }

    public static int getDEF(){
        return stats[3];
    }

    public static int getSPD(){
        return stats[4];
    }

    public static int getINT(){
        return stats[5];
    }

    public static int getLUK(){
        return stats[6];
    }

    public String getUsername() {
        return playerName;
    }
}
