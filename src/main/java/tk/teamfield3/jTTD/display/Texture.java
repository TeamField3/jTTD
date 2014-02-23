package tk.teamfield3.jTTD.display;

import tk.teamfield3.jTTD.util.ImageUtil;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Texture {

    private int id;

    public Texture(String fileName, Class<?> classInJar) {
        this(loadTexture(fileName, classInJar));
    }

    public Texture(String filePath) {
        this(loadTexture(filePath));
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

    private static int loadTexture(String fileName, Object classInJar) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try {
            return ImageUtil.getTextureID(ImageIO.read(classInJar.getClass().getResourceAsStream("/textures/" + fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int loadTexture(String filePath) {
        try {
            return ImageUtil.getTextureID(ImageIO.read(new File(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
