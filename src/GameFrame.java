import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private JPanel currentPanel;
    SoundHandler soundHandler = new SoundHandler();

    public GameFrame() {
        setTitle("Memory Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menü hozzáadása
        setJMenuBar(createMenuBar());

        // Menü panel inicializálása és megjelenítése
        MenuPanel menuPanel = new MenuPanel(this);
        setPanel(menuPanel); // Alapértelmezett panel beállítása

        soundHandler.playBackgroundMusic("sounds/hz.wav");
    }

    @Override
    public void dispose() {
        soundHandler.stopSound();
        super.dispose();
    }

    public void setPanel(JPanel panel) {
        if (currentPanel != null) {
            remove(currentPanel); // Az aktuális panel eltávolítása
        }
        currentPanel = panel;
        add(currentPanel, BorderLayout.CENTER); // Új panel hozzáadása
        revalidate(); // Elrendezés frissítése
        repaint(); // Újrarajzolás
    }


    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(120, 120, 100)); // Sötétebb szín
        menuBar.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, new Color(80, 80, 60))); // Szebb keret

        // Menü létrehozása
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setFont(new Font("Serif", Font.BOLD, 16));
        gameMenu.setForeground(Color.BLACK); // Szöveg színe

        JMenuItem goBackMenuItem = new JMenuItem("Go Back to Menu");
        goBackMenuItem.addActionListener(e -> setPanel(new MenuPanel(this))); // vissza vált a főmenüre az aktuális panl helyett

        gameMenu.add(goBackMenuItem);
        menuBar.add(gameMenu);

        return menuBar;
    }
}
