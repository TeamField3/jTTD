package tk.teamfield3.jTTD.core;

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

    public void input() {
        for (GameComponent component : components)
            component.input();

        for (GameObject child : children)
            child.input();
    }

    public void update() {
        for (GameComponent component : components)
            component.update();

        for (GameObject child : children)
            child.update();
    }

    public void render() {
        for (GameComponent component : components)
            component.render();

        for (GameObject child : children)
            child.render();
    }

    public ArrayList<GameObject> getChildren() {
        return children;
    }

    public ArrayList<GameComponent> getComponents() {
        return components;
    }

}
