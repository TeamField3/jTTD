package tk.teamfield3.test;

import tk.teamfield3.jTTD.core.Game;
import tk.teamfield3.jTTD.core.GameObject;
import tk.teamfield3.jTTD.display.Window;
import tk.teamfield3.jTTD.util.math.Vector3f;

public class TestGame extends Game {

    @Override
    public void init() {
        camera = new TestCamera(70f, Window.getWidth() / Window.getHeight(), 0.1f, 1000);

        GameObject cube = new GameObject();
        cube.addComponent(new TestCube(new Vector3f(0, 10, 0)));
        cube.addComponent(new TestCube(new Vector3f(5, 10, 5)));
        getRootObject().addChild(cube);
    }

}
