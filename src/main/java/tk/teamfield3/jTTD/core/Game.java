package tk.teamfield3.jTTD.core;

import tk.teamfield3.jTTD.display.Camera;

public abstract class Game {

    protected Camera camera;
    private GameObject rootObject = new GameObject();

    public abstract void init();

    private void input(float delta) {
        rootObject.input(delta);
    }

    public void update(float delta) {
        input(delta);
        rootObject.update(delta);
    }

    public GameObject getRootObject() {
        return rootObject;
    }

    public Camera getCamera() {
        return camera;
    }
}
