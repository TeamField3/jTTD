package tk.teamfield3.jTTD.util;

import java.io.*;

public class LibraryUtil {

    public static void loadLwjgl() throws IOException {
        String path = "/" + System.mapLibraryName("lwjgl");

        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("The path to be absolute (start with '/').");
        }

        String[] parts = path.split("/");
        String filename = (parts.length > 1) ? parts[parts.length - 1] : null;

        String prefix = "";
        if (filename != null) {
            parts = filename.split("\\.", 2);
            prefix = parts[0];
        }

        if (filename == null || prefix.length() < 3) {
            throw new IllegalArgumentException("The filename has to be at least 3 characters long.");
        }

        File temp = new File(System.getProperty("java.io.tmpdir") + path);
        temp.deleteOnExit();
        temp.createNewFile();

        if (!temp.exists()) {
            throw new FileNotFoundException("File " + temp.getAbsolutePath() + " does not exist.");
        }

        byte[] buffer = new byte[1024];
        int readBytes;

        InputStream is = LibraryUtil.class.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException("File " + path + " was not found inside JAR.");
        }

        OutputStream os = new FileOutputStream(temp);
        try {
            while ((readBytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }
        } finally {
            os.close();
            is.close();
        }

        System.setProperty("org.lwjgl.librarypath", System.getProperty("java.io.tmpdir"));
    }

}
