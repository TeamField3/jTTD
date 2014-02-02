package tk.teamfield3.test;

import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Texture;
import tk.teamfield3.jTTD.display.Transform;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestFloor extends TestComponent {

    public TestFloor() {
        super(new Mesh("floor.obj"), new Material(new Texture("test.png"), new Vector3f(1, 1, 1), 1, 8));
    }

    @Override
    public void input(Transform transform) {

    }

    @Override
    public void update(Transform transform) {

    }

    @Override
    public void render(Transform transform) {
        super.render(transform);
    }

}
