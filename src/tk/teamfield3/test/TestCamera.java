package tk.teamfield3.test;

import tk.teamfield3.jTTD.Input;
import tk.teamfield3.jTTD.display.Camera;
import tk.teamfield3.jTTD.display.Window;
import tk.teamfield3.jTTD.util.TimeUtil;
import tk.teamfield3.jTTD.util.math.Vector2f;

public class TestCamera extends Camera {

    public TestCamera() {
        super();
    }

    @Override
    public void input() {
        float sensitivity = 0.5f;
        float movAmt = (float) (10 * TimeUtil.getDelta());
//		float rotAmt = (float)(100 * Time.getDelta());

        if (Input.getKey(Input.KEY_ESCAPE)) {
            Input.setCursor(true);
            mouseLocked = false;
        }

        if (Input.getMouseDown(0)) {
            Input.setMousePosition(centerPosition);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if (Input.getKey(Input.KEY_W))
            move(getForward(), movAmt);
        if (Input.getKey(Input.KEY_S))
            move(getForward(), -movAmt);
        if (Input.getKey(Input.KEY_A))
            move(getLeft(), movAmt);
        if (Input.getKey(Input.KEY_D))
            move(getRight(), movAmt);

        if (mouseLocked) {
            Vector2f deltaPos = Input.getMousePosition().subtract(centerPosition);

            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;

            if (rotY)
                rotateY(deltaPos.getX() * sensitivity);
            if (rotX)
                rotateX(-deltaPos.getY() * sensitivity);

            if (rotY || rotX)
                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
        }
    }

}
