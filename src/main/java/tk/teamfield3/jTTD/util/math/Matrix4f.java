package tk.teamfield3.jTTD.util.math;

public class Matrix4f {

    private float[][] matrix;

    public Matrix4f() {
        matrix = new float[4][4];
    }

    public Matrix4f initIdentity() {
        // 1, 0, 0, 0
        // 0, 1, 0, 0
        // 0, 0, 1, 0
        // 0, 0, 0, 1

        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 1;
        matrix[1][2] = 0;
        matrix[1][3] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
        matrix[2][3] = 0;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;

        return this;
    }

    public Matrix4f initTranslation(float x, float y, float z) {
        // 1, 0, 0, x
        // 0, 1, 0, y
        // 0, 0, 1, z
        // 0, 0, 0, 1
        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = x;
        matrix[1][0] = 0;
        matrix[1][1] = 1;
        matrix[1][2] = 0;
        matrix[1][3] = y;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
        matrix[2][3] = z;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;

        return this;
    }

    public Matrix4f initRotation(float x, float y, float z) {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        x = (float) Math.toRadians(x);
        y = (float) Math.toRadians(y);
        z = (float) Math.toRadians(z);

        // cos(z), -sin(z), 0, 0
        // sin(z),  cos(z), 0, 0
        //      0,       0, 1, 0
        //      0,       0, 0, 1
        rz.matrix[0][0] = (float) Math.cos(z);
        rz.matrix[0][1] = -(float) Math.sin(z);
        rz.matrix[0][2] = 0;
        rz.matrix[0][3] = 0;
        rz.matrix[1][0] = (float) Math.sin(z);
        rz.matrix[1][1] = (float) Math.cos(z);
        rz.matrix[1][2] = 0;
        rz.matrix[1][3] = 0;
        rz.matrix[2][0] = 0;
        rz.matrix[2][1] = 0;
        rz.matrix[2][2] = 1;
        rz.matrix[2][3] = 0;
        rz.matrix[3][0] = 0;
        rz.matrix[3][1] = 0;
        rz.matrix[3][2] = 0;
        rz.matrix[3][3] = 1;

        // 1,      0,      0, 0
        // 0, cos(x),      0, 0
        // 0, sin(x), cos(x), 0
        // 0,      0,      0, 1
        rx.matrix[0][0] = 1;
        rx.matrix[0][1] = 0;
        rx.matrix[0][2] = 0;
        rx.matrix[0][3] = 0;
        rx.matrix[1][0] = 0;
        rx.matrix[1][1] = (float) Math.cos(x);
        rx.matrix[1][2] = -(float) Math.sin(x);
        rx.matrix[1][3] = 0;
        rx.matrix[2][0] = 0;
        rx.matrix[2][1] = (float) Math.sin(x);
        rx.matrix[2][2] = (float) Math.cos(x);
        rx.matrix[2][3] = 0;
        rx.matrix[3][0] = 0;
        rx.matrix[3][1] = 0;
        rx.matrix[3][2] = 0;
        rx.matrix[3][3] = 1;

        // cos(y), 0, -sin(y), 0
        //      0, 1,       0, 0
        // sin(y), 0,  cos(y), 0
        //      0, 0,       0, 1
        ry.matrix[0][0] = (float) Math.cos(y);
        ry.matrix[0][1] = 0;
        ry.matrix[0][2] = -(float) Math.sin(y);
        ry.matrix[0][3] = 0;
        ry.matrix[1][0] = 0;
        ry.matrix[1][1] = 1;
        ry.matrix[1][2] = 0;
        ry.matrix[1][3] = 0;
        ry.matrix[2][0] = (float) Math.sin(y);
        ry.matrix[2][1] = 0;
        ry.matrix[2][2] = (float) Math.cos(y);
        ry.matrix[2][3] = 0;
        ry.matrix[3][0] = 0;
        ry.matrix[3][1] = 0;
        ry.matrix[3][2] = 0;
        ry.matrix[3][3] = 1;

        matrix = rz.multiply(ry.multiply(rx)).getMatrix();

        return this;
    }

    public Matrix4f initScale(float x, float y, float z) {
        // x, 0, 0, 0
        // 0, y, 0, 0
        // 0, 0, z, 0
        // 0, 0, 0, 1
        matrix[0][0] = x;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = y;
        matrix[1][2] = 0;
        matrix[1][3] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = z;
        matrix[2][3] = 0;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;

        return this;
    }

    public Matrix4f initProjection(float fov, float width, float height, float zNear, float zFar) {
        float ar = width / height;
        float tanHalfFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float zRange = zNear - zFar;

        // 1 / (tanHalfFOV * ar),              0,                      0, 0
        //                     0, 1 / tanHalfFOV,                      0, 0
        //                     0,              0, (-zNear - zFar)/zRange, 2 * zFar * zNear / zRange
        // 0, 0, 1, 0
        matrix[0][0] = 1.0f / (tanHalfFOV * ar);
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 1.0f / tanHalfFOV;
        matrix[1][2] = 0;
        matrix[1][3] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = (-zNear - zFar) / zRange;
        matrix[2][3] = 2 * zFar * zNear / zRange;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 1;
        matrix[3][3] = 0;

        return this;
    }

    public Matrix4f initCamera(Vector3f forward, Vector3f up) {
        Vector3f f = forward.getNormalized();

        Vector3f r = up.getNormalized();
        r = r.cross(f);

        Vector3f u = f.cross(r);

        // r.getX, r.getY, r.getZ, 0
        // u.getX, u.getY, u.getZ, 0
        // f.getX, f.getY, f.getZ, 0
        //      0,      0,      0, 1

        matrix[0][0] = r.getX();
        matrix[0][1] = r.getY();
        matrix[0][2] = r.getZ();
        matrix[0][3] = 0;
        matrix[1][0] = u.getX();
        matrix[1][1] = u.getY();
        matrix[1][2] = u.getZ();
        matrix[1][3] = 0;
        matrix[2][0] = f.getX();
        matrix[2][1] = f.getY();
        matrix[2][2] = f.getZ();
        matrix[2][3] = 0;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;

        return this;
    }

    public Matrix4f multiply(Matrix4f r) {
        Matrix4f res = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res.set(i, j, matrix[i][0] * r.get(0, j) +
                        matrix[i][1] * r.get(1, j) +
                        matrix[i][2] * r.get(2, j) +
                        matrix[i][3] * r.get(3, j));
            }
        }

        return res;
    }

    public float[][] getMatrix() {
        float[][] res = new float[4][4];

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                res[i][j] = matrix[i][j];

        return res;
    }

    public float get(int x, int y) {
        return matrix[x][y];
    }

    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;
    }

    public void set(int x, int y, float value) {
        matrix[x][y] = value;
    }

}
