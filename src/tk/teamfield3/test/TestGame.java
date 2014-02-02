package tk.teamfield3.test;

import tk.teamfield3.jTTD.core.Game;
import tk.teamfield3.jTTD.core.GameObject;
import tk.teamfield3.jTTD.display.Camera;
import tk.teamfield3.jTTD.display.Transform;
import tk.teamfield3.jTTD.display.Window;

public class TestGame implements Game {

    private Camera camera;
    private GameObject root;

    @Override
    public void init() {
        root = new GameObject();
        camera = new TestCamera();

        // Mesh mesh = new Mesh("sphere.obj");


        TestComponent component = new TestFloor();
        root.addComponent(component);

        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
        Transform.setCamera(camera);
    }

    @Override
    public void input() {
        camera.input();
        root.input();
    }

    @Override
    public void update() {
        root.update();
    }

    @Override
    public void render() {
        root.render();
    }

}
