package tk.teamfield3.jTTD.core;

import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Transform;
import tk.teamfield3.jTTD.util.math.Vector3f;

public abstract class GameComponent {

    protected Mesh mesh;
    protected Material material;
    protected Transform transform;
    protected Vector3f position;

    public GameComponent(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
        this.transform = new Transform();
    }

    public GameComponent(Mesh mesh, Material material, Vector3f position) {
        this.mesh = mesh;
        this.material = material;
        this.position = position;
        this.transform = new Transform();
    }

    public abstract void input();

    public void update() {
        transform.setTranslation(position);
    }

    public abstract void render();

    public void move(Vector3f move) {
        position.incX(move.getX());
        position.incY(move.getY());
        position.incZ(move.getZ());
    }

    public Vector3f getPosition() {
        return position;
    }

    public Transform getTransform() {
        return transform;
    }

}
