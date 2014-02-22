package tk.teamfield3.test;

import tk.teamfield3.jTTD.core.GameEngine;
import tk.teamfield3.jTTD.util.LibraryUtil;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            LibraryUtil.loadLwjgl();
        } catch (IOException e) {
            e.printStackTrace();
        }

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