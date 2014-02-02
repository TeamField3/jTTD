package tk.teamfield3.test;

import tk.teamfield3.jTTD.Game;
import tk.teamfield3.jTTD.display.*;

public class TestGame implements Game {

    private Camera camera;
    private Mesh mesh;
    private Transform transform;
    private Material material;

    @Override
    public void init() {
        camera = new TestCamera();
        mesh = new Mesh("cube.obj");
        transform = new Transform();
        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
        Transform.setCamera(camera);
        material = new Material(new Texture("test.png"));
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
        Shader shader = BasicShader.getInstance();

        shader.bind();
        shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
        mesh.draw();
    }

}
