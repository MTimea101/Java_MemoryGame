import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private JRadioButton easyButton;
    private JRadioButton mediumButton;
    private JRadioButton hardButton;
    private JButton saveButton;
    private JButton backButton;
    private ButtonGroup difficultyGroup;

    public SettingsPanel(GameFrame gameFrame, MenuPanel menuPanel) {
        initializeComponents();
        setupLayout();
        setupButtonListeners(gameFrame, menuPanel);
        setBackground(new Color(195, 193, 151)); // Világos háttérszín
    }

    private void initializeComponents() {
        easyButton = new JRadioButton("Easy (4x4)");
        mediumButton = new JRadioButton("Medium (6x6)");
        hardButton = new JRadioButton("Hard (8x8)");

        saveButton = createStyledButton("Save");
        backButton = createStyledButton("Back");

        // Rádiógombok csoportosítása
        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(hardButton);

        // Alapértelmezett nehézségi szint
        easyButton.setSelected(true);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(20, 20)); // Térköz a panelek között

        // Cím hozzáadása
        JLabel titleLabel = new JLabel("Game Settings");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Rádiógombok keretben
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
        difficultyPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 80), 2),
                "Select Difficulty",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                new Color(50, 50, 30)
        ));
        difficultyPanel.setBackground(new Color(170, 165, 130)); // Háttérszín
        difficultyPanel.add(easyButton);
        difficultyPanel.add(Box.createVerticalStrut(10));
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(Box.createVerticalStrut(10));
        difficultyPanel.add(hardButton);

        // Alsó gombok
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(195, 193, 151)); // Ugyanaz, mint a panel háttere
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        add(titleLabel, BorderLayout.NORTH);
        add(difficultyPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupButtonListeners(GameFrame gameFrame, MenuPanel menuPanel) {
        saveButton.addActionListener(e -> {
            if (easyButton.isSelected()) {
                menuPanel.setSelectedDifficulty(Difficulty.EASY);
            } else if (mediumButton.isSelected()) {
                menuPanel.setSelectedDifficulty(Difficulty.MEDIUM);
            } else if (hardButton.isSelected()) {
                menuPanel.setSelectedDifficulty(Difficulty.HARD);
            }
            gameFrame.setPanel(menuPanel); // Vissza a menübe
        });

        backButton.addActionListener(e -> gameFrame.setPanel(menuPanel)); // Mentés nélkül vissza
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(100, 149, 237)); // Kék gomb
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

        // Hover-effektus
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(135, 206, 250)); // Világosabb kék
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Eredeti kék
            }
        });

        return button;
    }
}
