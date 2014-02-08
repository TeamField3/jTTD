package tk.teamfield3.test;

import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Texture;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestFloor extends TestComponent {

    public TestFloor(Mesh mesh) {
        super(mesh, new Material(new Texture("test.png", false), new Vector3f(1, 1, 1), 1, 8), new Vector3f(0, 0, 0));
    }

    @Override
    public void input(float delta) {

    }

    @Override
    public void update(float delta) {

    }

}
