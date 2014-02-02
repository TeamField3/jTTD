package tk.teamfield3.jTTD;

import tk.teamfield3.jTTD.display.Window;
import tk.teamfield3.jTTD.util.RenderUtil;
import tk.teamfield3.jTTD.util.TimeUtil;

public class GameEngine implements Runnable {

    private int width;
    private int height;
    private String title;
    private boolean isRunning;
    private double frameTime;

    private Game game;
    private Thread thread;

    public GameEngine(int width, int height, String title, double frameRate, Game game) {
        this.isRunning = false;
        this.width = width;
        this.height = height;
        this.title = title;
        this.frameTime = 1.0 / frameRate;
        this.game = game;
        this.thread = new Thread(this, "_main");
    }

    public void createWindow() {
        Window.createWindow(width, height, title);
        System.out.println(RenderUtil.getOpenGLVersion());
        RenderUtil.initGraphics();

    }

    public synchronized void start() {
        if (isRunning)
            return;

        isRunning = true;
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
        long frameCounter = 0;

        game.init();

        long lastTime = TimeUtil.getTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;

            long startTime = TimeUtil.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) TimeUtil.SECOND;
            frameCounter += passedTime;

            while (unprocessedTime > frameTime) {
                render = true;

                unprocessedTime -= frameTime;

                if (Window.isCloseRequested())
                    stop();

                TimeUtil.setDelta(frameTime);

                update();

                if (frameCounter >= TimeUtil.SECOND) {
                    System.out.println("FPS: " + frames);
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if (render) {
                render();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
    }

    private void input() {
        Input.update();
        game.input();
    }

    private void cleanUp() {
        Window.dispose();
    }

}
