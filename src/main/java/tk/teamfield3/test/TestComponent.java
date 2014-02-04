package tk.teamfield3.test;

import tk.teamfield3.jTTD.core.GameComponent;
import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Shader;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestComponent extends GameComponent {

    public TestComponent(Mesh mesh, Material material, Vector3f position) {
        super(mesh, material, position);
    }

    @Override
    public void input() {

    }

    @Override
    public void render() {
        Shader shader = BasicShader.getInstance();

        shader.bind();
        shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
        mesh.draw();
    }

}
