package tk.teamfield3.jTTD.core;

import tk.teamfield3.jTTD.display.Window;
import tk.teamfield3.jTTD.util.TimeUtil;

import java.awt.*;

public class GameEngine implements Runnable {

    private static GameEngine instance;

    private double updateTime;
    private boolean isRunning;

    private Game game;
    private GameRenderer renderer;
    private Thread thread;

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }

        return instance;
    }

    private GameEngine() {
        isRunning = false;
        thread = new Thread(this, "_main");
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setUpdateRate(int updateRate) {
        updateTime = 1.0 / updateRate;
    }

    public void setFrameRate(int frameRate) {
        Window.setFrameRate(frameRate);
    }

    public void setParent(Canvas parent) {
        Window.setParent(parent);
    }

    public void setResizeable(boolean resizeable) {
        Window.setResizable(resizeable);
    }

    public void setTitle(String title) {
        Window.setTitle(title);
    }

    public void setSize(int width, int height) {
        Window.setSize(width, height);
    }

    public void setFullscreen(boolean fullscreen) {
        Window.setFullscreen(fullscreen);
    }

    public void createWindow() {
        Window.create();
        game.init(); // TODO: Maybe move this somewhere else
        renderer = new GameRenderer(game.getCamera());
        System.out.println(renderer.getOpenGLVersion());
    }

    public synchronized void start() {
        if (isRunning)
            return;

        isRunning = true;
        createWindow();
        thread.run();
    }

    public synchronized void stop() {
        if (!isRunning)
            return;

        isRunning = false;
    }

    @Override
    public void run() {
        int frames = 0;
        int updates = 0;
        long counter = 0;

        long lastTime = TimeUtil.getTime();
        double unprocessedTime = 0;

        while (isRunning) {
            long startTime = TimeUtil.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) TimeUtil.SECOND;
            counter += passedTime;

            while (unprocessedTime > updateTime) {
                unprocessedTime -= updateTime;

                if (Window.isCloseRequested())
                    stop();

                TimeUtil.setDelta(updateTime);

                game.update((float) TimeUtil.getDelta());
                renderer.input((float) TimeUtil.getDelta());
                Window.update();
                updates++;

                if (counter >= TimeUtil.SECOND) {
                    System.out.println("FPS: " + frames + ", UPS: " + updates);
                    frames = 0;
                    updates = 0;
                    counter = 0;
                }
            }
            renderer.render(game.getRootObject());
            Window.render();
            frames++;
        }
        cleanUp();
    }

    private void cleanUp() {
        Window.dispose();
    }

    public GameRenderer getRenderer() {
        return renderer;
    }

}
