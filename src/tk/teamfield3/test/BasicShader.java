package tk.teamfield3.test;

import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Shader;
import tk.teamfield3.jTTD.util.RenderUtil;
import tk.teamfield3.jTTD.util.math.Matrix4f;

public class BasicShader extends Shader {

    private static final BasicShader instance = new BasicShader();

    public static BasicShader getInstance() {
        return instance;
    }

    private BasicShader() {
        super();

        addVertexShaderFromFile("basicVertex.vs");
        addFragmentShaderFromFile("basicFragment.fs");
        compileShader();

        addUniform("transform");
        addUniform("color");
    }

    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniform("transform", projectedMatrix);
        setUniform("color", material.getColor());
    }

}
