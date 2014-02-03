package tk.teamfield3.jTTD.core;

public interface Game {

    public void init(GameEngine engine);
    public void input();
    public void update();
    public void render();

}
