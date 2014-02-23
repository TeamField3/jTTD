package tk.teamfield3.jTTD.core;

import tk.teamfield3.jTTD.display.Camera;
import tk.teamfield3.jTTD.display.shader.BasicShader;
import tk.teamfield3.jTTD.display.shader.Shader;
import tk.teamfield3.jTTD.util.math.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

public class GameRenderer {

    private Camera camera;

    public GameRenderer(Camera camera) {
        this.camera = camera;

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);
    }

    public void input(float delta) {
        camera.input(delta);
    }

    public void render(GameObject object) {
        clearScreen();

        Shader shader = BasicShader.getInstance();
        shader.setRenderingEngine(this);

        object.render(BasicShader.getInstance());
    }

    private void clearScreen() {
        //TODO: Stencil Buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private void setTextures(boolean enabled) {
        if (enabled)
            glEnable(GL_TEXTURE_2D);
        else
            glDisable(GL_TEXTURE_2D);
    }

    private void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private void setClearColor(Vector3f color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    public String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

    public Camera getCamera() {
        return camera;
    }

}
