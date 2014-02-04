package tk.teamfield3.test;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import tk.teamfield3.jTTD.display.Camera;
import tk.teamfield3.jTTD.display.Window;
import tk.teamfield3.jTTD.util.TimeUtil;
import tk.teamfield3.jTTD.util.math.Vector2f;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestCamera extends Camera {

    public TestCamera() {
        super();
    }

    @Override
    public void input() {
        float sensitivity = 0.5f;
        float movAmt = (float) (10 * TimeUtil.getDelta());
//		float rotAmt = (float)(100 * Time.getDelta());

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Mouse.setGrabbed(false);
            mouseLocked = false;
        }

        if (Mouse.isButtonDown(0)) {
            Mouse.setGrabbed(true);
            mouseLocked = true;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W))
            move(getForward(), movAmt);
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
            move(getForward(), -movAmt);
        if (Keyboard.isKeyDown(Keyboard.KEY_A))
            move(getLeft(), movAmt);
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
            move(getRight(), movAmt);
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            move(new Vector3f(0, 1, 0), movAmt); // getUp() is relative
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            move(new Vector3f(0, -1, 0), movAmt);

        if (mouseLocked) {
            Vector2f deltaPos = new Vector2f(Mouse.getX(), Mouse.getY()).subtract(centerPosition);

            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;

            if (rotY)
                rotateY(deltaPos.getX() * sensitivity);
            if (rotX)
                rotateX(-deltaPos.getY() * sensitivity);

            if (rotY || rotX)
                Mouse.setCursorPosition((int) Window.getWidth() / 2, (int) Window.getHeight() / 2);
        }
    }

}
