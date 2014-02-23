package tk.teamfield3.jTTD.display;

import tk.teamfield3.jTTD.util.math.Matrix4f;
import tk.teamfield3.jTTD.util.math.Vector2f;
import tk.teamfield3.jTTD.util.math.Vector3f;

public abstract class Camera {

    public static final Vector3f yAxis = new Vector3f(0, 1, 0);

    protected Vector3f pos;
    protected Vector3f forward;
    protected Vector3f up;
    protected Matrix4f projection;
    protected Vector2f centerPosition = new Vector2f((int) Window.getWidth() / 2, (int) Window.getHeight() / 2);

    protected Camera(float fov, float aspect, float zNear, float zFar) {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0), fov, aspect, zNear, zFar);
    }

    protected Camera(Vector3f pos, Vector3f forward, Vector3f up, float fov, float aspect, float zNear, float zFar) {
        this.pos = pos;
        this.forward = forward.getNormalized();
        this.up = up.getNormalized();
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
        System.out.println(fov + ", " + aspect + ", " + zNear + ", " + zFar);
    }

    public Matrix4f getViewProjection() {
        Matrix4f cameraRotation = new Matrix4f().initRotation(forward, up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-pos.getX(), -pos.getY(), -pos.getZ());

        return projection.multiply(cameraRotation.multiply(cameraTranslation));
    }

    public abstract void input(float delta);

    public void updateCenterPosition() {
        centerPosition = new Vector2f((int) (Window.getWidth() / 2), (int) (Window.getHeight() / 2));
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

    public void setParameters(float fov, float aspect, float zNear, float zFar) {
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
    }

}
