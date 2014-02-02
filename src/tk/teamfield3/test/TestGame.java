package tk.teamfield3.test;

import tk.teamfield3.jTTD.Game;
import tk.teamfield3.jTTD.core.GameObject;
import tk.teamfield3.jTTD.display.*;
import tk.teamfield3.jTTD.util.math.Vector2f;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestGame implements Game {

    private Camera camera;
    private GameObject root;

    @Override
    public void init() {
        root = new GameObject();
        camera = new TestCamera();

        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int indices[] = { 0, 1, 2,
                2, 1, 3};

        // Mesh mesh = new Mesh("sphere.obj");
        Mesh mesh = new Mesh(vertices, indices, true);
        Material material = new Material(new Texture("test.png"), new Vector3f(1, 1, 1), 1, 8);

        TestComponent component = new TestComponent(mesh, material);
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
