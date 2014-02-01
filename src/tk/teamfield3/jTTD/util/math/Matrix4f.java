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
