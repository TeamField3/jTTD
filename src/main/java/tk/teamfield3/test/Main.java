package tk.teamfield3.test;

import tk.teamfield3.jTTD.core.GameEngine;

public class Main {

    public static void main(String[] args) {
        GameEngine engine = GameEngine.getInstance();
        engine.setSize(800, 450);
        engine.setTitle("Test");
        engine.setFrameRate(60);
        engine.setUpdateRate(60);
        engine.setFullscreen(false);
        engine.setResizeable(true);
        engine.setGame(new TestGame());
        engine.start();
    }

}