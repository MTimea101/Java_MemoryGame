import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        MenuPanel menuPanel = new MenuPanel(gameFrame);
        gameFrame.setPanel(menuPanel); // indulaskor a menupanelt jeleniti meg
        gameFrame.show();
    }
}
