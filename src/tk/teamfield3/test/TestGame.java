package tk.teamfield3.test;

import tk.teamfield3.jTTD.Game;
import tk.teamfield3.jTTD.display.Camera;

public class TestGame implements Game {

    private Camera camera;

    @Override
    public void init() {
        camera = new Camera();
    }

    @Override
    public void input() {
        camera.input();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

}
