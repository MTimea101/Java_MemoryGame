import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuPanel extends JPanel {
    private JButton newGameButton;
    private JButton settingsButton;
    private JButton quitGameButton;
    private JButton statsButton;
    private Difficulty selectedDifficulty; // Tárolja a kiválasztott nehézségi szintet

    public MenuPanel(GameFrame gameFrame) {
        this.selectedDifficulty = Difficulty.EASY; // Alapértelmezett nehézségi szint
        initializeComponents(gameFrame);
        setupLayout();
        setBackground(new Color(195, 193, 151)); // Háttér szín
    }

    public void setSelectedDifficulty(Difficulty difficulty) {
        this.selectedDifficulty = difficulty; // Setter metódus
    }

    public Difficulty getSelectedDifficulty() {
        return selectedDifficulty; // Getter metódus
    }

    private void initializeComponents(GameFrame gameFrame) {
        // Gombok inicializálása
        newGameButton = createStyledButton("New Game", null);
        settingsButton = createStyledButton("Settings", null);
        quitGameButton = createStyledButton("Quit Game", null);
        statsButton = createStyledButton("See Stats", null);

        // Gomb események
        newGameButton.addActionListener(e -> gameFrame.setPanel(new GamePanel(selectedDifficulty, gameFrame, this)));
        settingsButton.addActionListener(e -> gameFrame.setPanel(new SettingsPanel(gameFrame, this)));
        statsButton.addActionListener(e -> {
            Difficulty currentDifficulty = getSelectedDifficulty();
            if (currentDifficulty == null) {
                currentDifficulty = Difficulty.EASY; // Alapértelmezett, ha nincs beállítva
            }
            gameFrame.setPanel(new HighscorePanel(gameFrame, this, currentDifficulty));
        });


        quitGameButton.addActionListener(e -> System.exit(0));
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Címek hozzáadása
        JLabel titleLabel = new JLabel("Welcome to");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 50, 30)); // Sötét szín
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Re:Memory");
        subtitleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        subtitleLabel.setForeground(new Color(50, 50, 30)); // Sötét szín
        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(50)); // Térköz a tetején
        add(titleLabel);
        add(Box.createVerticalStrut(10)); // Kis térköz a két cím között
        add(subtitleLabel);
        add(Box.createVerticalStrut(50)); // Térköz a cím után

        // Gombok hozzáadása középre igazítva
        add(createCenteredPanel(newGameButton, 200, 50)); // Szélesebb és magasabb gomb
        add(Box.createVerticalStrut(20)); // Térköz a gombok között
        add(createCenteredPanel(settingsButton, 200, 50));
        add(Box.createVerticalStrut(20));
        add(createCenteredPanel(statsButton, 200, 50));
        add(Box.createVerticalStrut(20));
        add(createCenteredPanel(quitGameButton, 200, 50));
    }

    private JPanel createCenteredPanel(JButton button, int width, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER)); //Középen legyen
        panel.setOpaque(false); // Átlátszó háttér
        button.setPreferredSize(new Dimension(width, height)); // Gomb méretének beállítása
        panel.add(button);
        return panel;
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);

        // Gomb ikon hozzáadása
        button.setIcon(new ImageIcon(iconPath));
        button.setHorizontalTextPosition(SwingConstants.RIGHT); // Szöveg jobbra
        button.setIconTextGap(10); // Ikon és szöveg közötti távolság

        // Gomb stílusok
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(170, 165, 130));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true); // Egyszerű forma
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true)); // Ovális szegély

        // Hover-effektus
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 190, 140));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(170, 165, 130));
            }
        });

        return button;
    }

    private List<String[]> loadHighScores() {
        List<String[]> scores = new ArrayList<>();
        scores.add(new String[]{"Player1", "45", "30"});
        scores.add(new String[]{"Player2", "50", "25"});
        scores.add(new String[]{"Player3", "60", "20"});
        return scores;
    }
}
