package com.gdx.turnquest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static com.gdx.turnquest.LoginDialog.*;

public class Player {

    private static String characterClass = "";

    public Player(String fileName) {
        try {
            Scanner file = new Scanner(new FileReader("../" + fileName + ".txt"));
            file.nextLine();
            file.nextLine();
            characterClass = file.nextLine();
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File not found.");
        }
    }

    public static String getCharacterClass() {
        return characterClass;
    }
}
