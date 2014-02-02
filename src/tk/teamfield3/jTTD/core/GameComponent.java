package tk.teamfield3.jTTD.core;

import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Transform;

public abstract class GameComponent {

    protected Mesh mesh;
    protected Material material;

    public GameComponent(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public abstract void input(Transform transform);

    public abstract void update(Transform transform);

    public abstract void render(Transform transform);

}
