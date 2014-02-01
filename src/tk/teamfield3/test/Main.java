package tk.teamfield3.test;

import tk.teamfield3.jTTD.GameEngine;

public class Main {

    public static void main(String[] args) {
        GameEngine engine = new GameEngine(800, 450, "Test", 60, new TestGame());
        engine.createWindow();
        engine.start();
    }

}