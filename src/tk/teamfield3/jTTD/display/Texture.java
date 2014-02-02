package tk.teamfield3.jTTD.display;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Texture {

    private int id;

    public Texture(String fileName) {
        this(loadTexture(fileName));
    }

    public Texture(int id) {
        this.id = id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getID() {
        return id;
    }

    private static int loadTexture(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try {
            int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/textures/" + fileName))).getTextureID();

            return id;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
