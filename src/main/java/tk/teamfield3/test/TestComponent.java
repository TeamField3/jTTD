package tk.teamfield3.test;

import org.lwjgl.input.Keyboard;
import tk.teamfield3.jTTD.core.GameComponent;
import tk.teamfield3.jTTD.core.GameEngine;
import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Window;
import tk.teamfield3.jTTD.display.shader.Shader;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestComponent extends GameComponent {

    public TestComponent(Mesh mesh, Material material, Vector3f position) {
        super(mesh, material, position);
    }

    @Override
    public void input(float delta) {
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            if (Window.isFullscreen())
                GameEngine.getInstance().setFullscreen(false);
            else
                GameEngine.getInstance().setFullscreen(true);
        }
    }

    @Override
    public void render(Shader shader) {
        shader.bind();
        shader.updateUniforms(transform, material);
        mesh.draw();
    }

}
