package tk.teamfield3.test;

import tk.teamfield3.jTTD.core.Game;
import tk.teamfield3.jTTD.core.GameObject;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Vertex;
import tk.teamfield3.jTTD.display.Window;
import tk.teamfield3.jTTD.util.math.Vector2f;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestGame extends Game {

    @Override
    public void init() {
        camera = new TestCamera(70f, Window.getWidth() / Window.getHeight(), 0.1f, 1000);

        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;
        Vertex[] vertices = new Vertex[]{new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};
        int indices[] = {0, 1, 2,
                2, 1, 3};
        TestComponent floor1 = new TestFloor(new Mesh(vertices, indices));

        getRootObject().addComponent(floor1);

        GameObject cube = new GameObject();
        cube.addComponent(new TestCube(new Vector3f(0, 10, 0)));
        getRootObject().addChild(cube);
    }

}
