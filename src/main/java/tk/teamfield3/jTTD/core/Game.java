package tk.teamfield3.jTTD.core;

public abstract class Game {

    public GameObject rootObject = new GameObject();

    public abstract void init(GameEngine engine);

    public abstract void input();

    public abstract void update();

    public abstract void render();

}
