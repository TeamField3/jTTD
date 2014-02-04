package tk.teamfield3.jTTD.core;

import tk.teamfield3.jTTD.display.Window;
import tk.teamfield3.jTTD.util.RenderUtil;
import tk.teamfield3.jTTD.util.TimeUtil;

import java.awt.*;

public class GameEngine implements Runnable {

    private double updateTime;
    private boolean isRunning;

    private Game game;
    private Thread thread;

    public GameEngine(int width, int height, String title, int frameRate, double updateRate, boolean fullscreen, boolean resizeable, Game game) {
        this.isRunning = false;
        this.updateTime = 1.0 / updateRate;
        this.game = game;
        this.thread = new Thread(this, "_main");

        setSize(width, height);
        setResizeable(resizeable);
        setTitle(title);
        setFrameRate(frameRate);
        setFullscreen(fullscreen);
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
        System.out.println(RenderUtil.getOpenGLVersion());
        RenderUtil.initGraphics();

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

        game.init(this);

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

                update();
                updates++;

                if (counter >= TimeUtil.SECOND) {
                    System.out.println("FPS: " + frames + ", UPS: " + updates);
                    frames = 0;
                    updates = 0;
                    counter = 0;
                }
            }
            render();
            frames++;
        }
        cleanUp();
    }

    private void render() {
        RenderUtil.clearScreen();
        game.render();
        Window.render();
    }

    private void update() {
        input();
        game.update();
        Window.update();
    }

    private void input() {
        game.input();
    }

    private void cleanUp() {
        Window.dispose();
    }

}
