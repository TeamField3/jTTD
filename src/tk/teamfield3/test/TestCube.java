package tk.teamfield3.test;

import tk.teamfield3.jTTD.display.Material;
import tk.teamfield3.jTTD.display.Mesh;
import tk.teamfield3.jTTD.display.Texture;
import tk.teamfield3.jTTD.display.Transform;

public class TestCube extends TestComponent {

    public TestCube() {
        super(new Mesh("cube.obj"), new Material(new Texture("test2.png")));
    }

    int i = 0;
    float j = 0;
    boolean minus;
    @Override
    public void update(Transform transform) {
        i++;
        if (j > 5)
            minus = true;
        if (j < 0)
            minus = false;
        if (minus)
            j -= 0.1;
        else
            j += 0.1;
        transform.setTranslation(j, 10, j);
        transform.setRotation(i, i, i);
    }

}
