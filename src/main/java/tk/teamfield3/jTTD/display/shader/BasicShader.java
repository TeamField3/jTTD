package tk.teamfield3.jTTD.display.shader;

import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Transform;
import tk.teamfield3.jTTD.util.math.Matrix4f;

public class BasicShader extends Shader {

    private static final BasicShader instance = new BasicShader();

    public static BasicShader getInstance() {
        return instance;
    }

    private BasicShader() {
        super();

        addVertexShaderFromFile("basicVertex.vs", true);
        addFragmentShaderFromFile("basicFragment.fs", true);
        compileShader();

        addUniform("transform");
        addUniform("color");
    }

    @Override
    public void updateUniforms(Transform transform, Material material) {
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = getRenderingEngine().getCamera().getViewProjection().multiply(worldMatrix);
        material.getTexture().bind();

        setUniform("transform", projectedMatrix);
        setUniform("color", material.getColor());
    }

}
