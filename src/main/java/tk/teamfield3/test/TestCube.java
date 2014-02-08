package tk.teamfield3.test;

import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Texture;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestCube extends TestComponent {

    public TestCube(Vector3f position) {
        super(new Mesh("cube.obj"), new Material(new Texture("test2.png", false)), position);
    }

    int i = 0;

    @Override
    public void update(float delta) {
        super.update(delta);

        i++;
        transform.setRotation(i, i, i);
    }

}
