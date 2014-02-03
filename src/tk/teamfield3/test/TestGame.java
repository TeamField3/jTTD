package tk.teamfield3.test;

import org.lwjgl.input.Keyboard;
import tk.teamfield3.jTTD.core.Game;
import tk.teamfield3.jTTD.core.GameEngine;
import tk.teamfield3.jTTD.core.GameObject;
import tk.teamfield3.jTTD.display.Camera;
import tk.teamfield3.jTTD.display.Transform;
import tk.teamfield3.jTTD.display.Window;

public class TestGame implements Game {

    private GameEngine engine;
    private Camera camera;
    private GameObject root;

    @Override
    public void init(GameEngine engine) {
        this.engine = engine;
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
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            if (Window.isFullscreen())
                Window.setFullscreen(false);
            else
                Window.setFullscreen(true);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
            engine.stop();
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
