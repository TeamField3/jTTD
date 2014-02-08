package tk.teamfield3.jTTD.core;

import tk.teamfield3.jTTD.display.shader.Shader;

import java.util.ArrayList;

public class GameObject {

    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;

    public GameObject() {
        children = new ArrayList<GameObject>();
        components = new ArrayList<GameComponent>();
    }

    public void addChild(GameObject child) {
        children.add(child);
    }

    public void addComponent(GameComponent component) {
        components.add(component);
    }

    public void input(float delta) {
        for (GameComponent component : components)
            component.input(delta);

        for (GameObject child : children)
            child.input(delta);
    }

    public void update(float delta) {
        for (GameComponent component : components)
            component.update(delta);

        for (GameObject child : children)
            child.update(delta);
    }

    public void render(Shader shader) {
        for (GameComponent component : components)
            component.render(shader);

        for (GameObject child : children)
            child.render(shader);
    }

    public ArrayList<GameObject> getChildren() {
        return children;
    }

    public ArrayList<GameComponent> getComponents() {
        return components;
    }

}
