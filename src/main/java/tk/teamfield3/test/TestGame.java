package tk.teamfield3.test;

import org.lwjgl.input.Keyboard;
import tk.teamfield3.jTTD.core.Game;
import tk.teamfield3.jTTD.core.GameEngine;
import tk.teamfield3.jTTD.core.GameObject;
import tk.teamfield3.jTTD.display.*;
import tk.teamfield3.jTTD.util.math.Vector2f;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestGame extends Game {

    private GameEngine engine;
    private Camera camera;

    @Override
    public void init(GameEngine engine) {
        this.engine = engine;
        camera = new TestCamera();

        // Mesh mesh = new Mesh("sphere.obj");
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;
        Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};
        int indices[] = { 0, 1, 2,
                2, 1, 3};
        TestComponent floor1 = new TestFloor(new Mesh(vertices, indices, true));
        rootObject.addComponent(floor1);
        GameObject cube = new GameObject();
        cube.addComponent(new TestCube(new Vector3f(0, 10, 0)));
        cube.addComponent(new TestCube(new Vector3f(5, 10, 5)));
        rootObject.addChild(cube);

        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
        Transform.setCamera(camera);
    }

    @Override
    public void input() {
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            if (Window.isFullscreen())
                engine.setFullscreen(false);
            else
                engine.setFullscreen(true);
        }
        camera.input();
        rootObject.input();
    }

    @Override
    public void update() {
        rootObject.update();
    }

    @Override
    public void render() {
        rootObject.render();
    }

}
