package tk.teamfield3.test;

import tk.teamfield3.jTTD.core.GameEngine;

public class Main {

    public static void main(String[] args) {
        GameEngine engine = new GameEngine(800, 450, "Test", 60, 60, false, false, new TestGame());
        engine.start();
    }

}