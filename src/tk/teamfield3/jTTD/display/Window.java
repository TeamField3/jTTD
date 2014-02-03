package tk.teamfield3.jTTD.display;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.*;

public class Window {

    private static int frameRate;
    private static DisplayMode displayMode = Display.getDesktopDisplayMode();

    public static void setFrameRate(int frameRate) {
        Window.frameRate = frameRate;
    }

    public static void setTitle(String title) {
        Display.setTitle(title);
    }

    public static void setSize(int width, int height) {
        try {
            displayMode = new DisplayMode(width, height);
            Display.setDisplayMode(displayMode);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void setResizable(boolean resizable) {
        Display.setResizable(resizable);
    }

    public static boolean isResizeable() {
        return Display.isResizable();
    }

    public static void setFullscreen(boolean fullscreen) {
        try {
            if (fullscreen) {
                displayMode = Display.getDisplayMode();
                Display.setDisplayMode(Display.getDesktopDisplayMode());
            } else {
                Display.setDisplayMode(displayMode);
            }
            Display.setFullscreen(fullscreen);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static boolean isFullscreen() {
        return Display.isFullscreen();
    }

    public static void setParent(Canvas canvas) {
        try {
            Display.setParent(canvas);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void create() {
        try {
            Display.create();
            Mouse.create();
            Keyboard.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void dispose() {
        Display.destroy();
        Mouse.destroy();
        Keyboard.destroy();
    }

    public static boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    public static void render() {
        Display.update();
        Display.sync(frameRate);
    }

    public static void update() {
        if (displayMode.getWidth() != Display.getWidth() || displayMode.getHeight() != Display.getHeight()) {
            displayMode = new DisplayMode(Display.getWidth(), Display.getHeight());
            Transform.setProjection(Transform.getFov(), Display.getWidth(), Display.getHeight(), Transform.getzNear(), Transform.getzFar());
        }
    }

    public static float getWidth() {
        return displayMode.getWidth();
    }

    public static float getHeight() {
        return displayMode.getHeight();
    }

}
