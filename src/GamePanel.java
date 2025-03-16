import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private GameLogic gameLogic;
    private List<JButton> cardButtons;
    private int firstSelectedIndex = -1;
    private int secondSelectedIndex = -1;
    private int attempts = 0; // Helytelen párok kereséseinek száma
    private long startTime; // Időmérés kezdete
    private String playerName; // Játékos neve
    private GameFrame gameFrame;
    private MenuPanel menuPanel;
    private SoundHandler soundHandler;
    private boolean initialized = false; // Az inicializálás állapota

    public GamePanel(Difficulty difficulty, GameFrame gameFrame, MenuPanel menuPanel) {
        this.gameLogic = new GameLogic(difficulty.getGridSize(), difficulty);
        this.gameFrame = gameFrame;
        this.menuPanel = menuPanel;
        this.cardButtons = new ArrayList<>();
        this.soundHandler = new SoundHandler();

        // Játékos neve bekérése
        this.playerName = JOptionPane.showInputDialog(
                null,
                "Please enter your name:",
                "Player Name",
                JOptionPane.QUESTION_MESSAGE
        );

        // Ha a játékos nem ad meg nevet vagy a Cancel gombra kattint
        if (playerName == null || playerName.trim().isEmpty()) {
            this.playerName = "Player1";
        }

        initializeGame(difficulty);
    }



    private void initializeGame(Difficulty difficulty) {

        // Háttérszín
        this.setBackground(new Color(240, 240, 240));

        // Időmérés indítása
        this.startTime = System.currentTimeMillis();

        // Grid elrendezés
        this.setLayout(new GridLayout(difficulty.getGridSize(), difficulty.getGridSize()));
        generateCardButtons();

        // Üdvözlő üzenet
        JOptionPane.showMessageDialog(
                null,
                "Welcome, " + playerName + "! Find all the pairs to win!",
                "Welcome",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void generateCardButtons() {
        int gridSize = gameLogic.getGridSize() * gameLogic.getGridSize();

        cardButtons = java.util.stream.IntStream.range(0, gridSize)// Iterál 0-tól a rácsméretig
                .mapToObj(i -> { // Minden indexhez létrehoz egy új gombot
                    JButton cardButton = new JButton();
                    cardButton.setText("?");
                    cardButton.setBackground(new Color(132, 176, 103));
                    cardButton.setFont(new Font("Arial", Font.BOLD, 18));
                    cardButton.setForeground(Color.DARK_GRAY);
                    cardButton.setHorizontalTextPosition(SwingConstants.CENTER);
                    cardButton.setVerticalTextPosition(SwingConstants.CENTER);
                    cardButton.setOpaque(true); // Nem átlátszó

                    cardButton.addActionListener(e -> handleCardClick(i, cardButton));
                    this.add(cardButton); // Gomb hozzáadása a panelhez
                    return cardButton;
                })
                .toList(); // A létrehozott gombokat listába gyűjti
    }


    private void handleCardClick(int index, JButton cardButton) {
        if (firstSelectedIndex == -1) { //Ha ez az  első kártya
            firstSelectedIndex = index;
            gameLogic.revealCard(index);
            showImage(cardButton, gameLogic.getCardValue(index));
            cardButton.setEnabled(false); // Nem kattintható tovább
            cardButton.setDisabledIcon(cardButton.getIcon());
        } else if (secondSelectedIndex == -1 && index != firstSelectedIndex) { // Ha ey a második kártya
            secondSelectedIndex = index;
            gameLogic.revealCard(index);
            showImage(cardButton, gameLogic.getCardValue(index));
            cardButton.setEnabled(false);
            cardButton.setDisabledIcon(cardButton.getIcon());

            attempts++; // Csak itt növeljük a próbálkozások számát

            if (gameLogic.checkMatch(firstSelectedIndex, secondSelectedIndex)) { // Ha megeggyeznek
                firstSelectedIndex = -1;
                secondSelectedIndex = -1;

                if (gameLogic.isGameOver()) {
                    endGame();
                }
            } else {
                hideCardsAfterDelay();
            }
        }
    }

    private void hideCardsAfterDelay() {
        Timer timer = new Timer(1000, e -> {
            gameLogic.hideCard(firstSelectedIndex);
            gameLogic.hideCard(secondSelectedIndex);
            hideImage(cardButtons.get(firstSelectedIndex));
            hideImage(cardButtons.get(secondSelectedIndex));
            cardButtons.get(firstSelectedIndex).setEnabled(true);
            cardButtons.get(secondSelectedIndex).setEnabled(true);
            firstSelectedIndex = -1;
            secondSelectedIndex = -1;
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void endGame() {
        SwingUtilities.invokeLater(() -> {
            soundHandler.playSound("sounds/bell_ring.wav");
            long endTime = System.currentTimeMillis();
            int timeElapsed = (int) ((endTime - startTime) / 1000); // Idő másodpercekben

            JOptionPane.showMessageDialog(
                    this,
                    "Congratulations, " + playerName + "! You found all pairs!\n"
                            + "Time: " + timeElapsed + " seconds\n"
                            + "Attempts: " + attempts,
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE
            );

            ResultPanel resultPanel = new ResultPanel(gameFrame, menuPanel, playerName, menuPanel.getSelectedDifficulty());
            resultPanel.updateResults(timeElapsed, attempts);
            gameFrame.setPanel(resultPanel);
        });
    }



    private void showImage(JButton cardButton, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(
                cardButton.getWidth() - 10,
                cardButton.getHeight() - 10,
                Image.SCALE_SMOOTH
        );
        cardButton.setIcon(new ImageIcon(scaledImage));
        cardButton.setText(null); // Töröljük a szöveget
    }

    private void hideImage(JButton cardButton) {
        cardButton.setIcon(null); // Töröljük az ikont
        cardButton.setText("?"); // Kérdőjelet teszünk vissza
    }

    public boolean isInitialized() {
        return initialized;
    }
}
