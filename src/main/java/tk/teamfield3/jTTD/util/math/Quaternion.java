package tk.teamfield3.jTTD.util.math;

public class Quaternion {

    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Quaternion getNormalized() {
        float length = length();

        return new Quaternion(x / length, y / length, z / length, w / length);
    }

    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion multiply(Quaternion r) {
        float wNew = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
        float xNew = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
        float yNew = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
        float zNew = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternion(xNew, yNew, zNew, wNew);
    }

    public Quaternion multiply(Vector3f r) {
        float wNew = -x * r.getX() - y * r.getY() - z * r.getZ();
        float xNew = w * r.getX() + y * r.getZ() - z * r.getY();
        float yNew = w * r.getY() + z * r.getX() - x * r.getZ();
        float zNew = w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternion(xNew, yNew, zNew, wNew);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

}
