package tk.teamfield3.test;

import tk.teamfield3.jTTD.core.GameComponent;
import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Shader;
import tk.teamfield3.jTTD.display.Transform;

public class TestComponent extends GameComponent {

    public TestComponent(Mesh mesh, Material material) {
        super(mesh, material);
    }

    @Override
    public void input(Transform transform) {

    }

    @Override
    public void update(Transform transform) {

    }

    @Override
    public void render(Transform transform) {
        Shader shader = BasicShader.getInstance();

        shader.bind();
        shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
        mesh.draw();
    }

}
