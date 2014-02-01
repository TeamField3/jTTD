package tk.teamfield3.jTTD.display;

import tk.teamfield3.jTTD.Input;
import tk.teamfield3.jTTD.util.TimeUtil;
import tk.teamfield3.jTTD.util.math.Vector2f;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class Camera {

    public static final Vector3f yAxis = new Vector3f(0, 1, 0);

    private Vector3f pos;
    private Vector3f forward;
    private Vector3f up;

    public Camera() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0));
    }

    public Camera(Vector3f pos, Vector3f forward, Vector3f up) {
        this.pos = pos;
        this.forward = forward.getNormalized();
        this.up = up.getNormalized();
    }

    boolean mouseLocked = false;
    Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);

    public void input() { // Lots of stuff here to change to configurable settings
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

    public void move(Vector3f dir, float amt) {
        pos = pos.add(dir.multiply(amt));
    }

    public void rotateY(float angle) {
        Vector3f hAxis = yAxis.cross(forward).getNormalized();

        forward = forward.rotate(angle, yAxis).getNormalized();

        up = forward.cross(hAxis).getNormalized();
    }

    public void rotateX(float angle) {
        Vector3f hAxis = yAxis.cross(forward).getNormalized();

        forward = forward.rotate(angle, hAxis).getNormalized();

        up = forward.cross(hAxis).getNormalized();
    }

    public Vector3f getLeft() {
        return forward.cross(up).getNormalized();
    }

    public Vector3f getRight() {
        return up.cross(forward).getNormalized();
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getForward() {
        return forward;
    }

    public void setForward(Vector3f forward) {
        this.forward = forward;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }

}
