import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ResultPanel extends JPanel {
    private JLabel resultLabel;
    private JButton retryButton;
    private JButton menuButton;
    private GameFrame gameFrame;
    private MenuPanel menuPanel;
    private FileHandler fileHandler;
    private JTable resultsTable;
    private String playerName;
    private Difficulty difficulty;

    public ResultPanel(GameFrame gameFrame, MenuPanel menuPanel, String playerName, Difficulty difficulty) {
        this.gameFrame = gameFrame;
        this.menuPanel = menuPanel;
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.fileHandler = new FileHandler("results_");
        initializeComponents();
        setupLayout();
        setupButtonListeners();
        loadAndDisplayPreviousResults();
    }

    private void initializeComponents() {
        resultLabel = new JLabel("Congratulations!");
        resultLabel.setFont(new Font("Verdana", Font.BOLD, 36));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        retryButton = createStyledButton("Play Again");
        menuButton = createStyledButton("Back to Menu");

        resultsTable = new JTable();
        resultsTable.setFont(new Font("Arial", Font.PLAIN, 16));
        resultsTable.setRowHeight(30);
        resultsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        resultsTable.getTableHeader().setBackground(new Color(168, 169, 114));
        resultsTable.getTableHeader().setForeground(Color.WHITE);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(new Color(154, 185, 153));

        add(resultLabel, BorderLayout.NORTH);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(retryButton);
        buttonPanel.add(menuButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupButtonListeners() {
        retryButton.addActionListener(e -> gameFrame.setPanel(new GamePanel(menuPanel.getSelectedDifficulty(), gameFrame, menuPanel)));
        menuButton.addActionListener(e -> gameFrame.setPanel(menuPanel));
    }

    public void updateResults(int timeElapsed, int attempts) {
        resultLabel.setText("<html>Congratulations!<br>Time: " + timeElapsed + " seconds<br>Attempts: " + attempts + "</html>");
        fileHandler.saveResults(playerName, timeElapsed, attempts, difficulty); // Mentés nehézségi szint szerint
        loadAndDisplayPreviousResults();
    }


    private void loadAndDisplayPreviousResults() {
        String[] columnNames = {"Player", "Time (s)", "Attempts"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        List<String[]> bestResults = fileHandler.loadBestResults(difficulty); // Nehézségi szint alapján töltsük be
        for (String[] result : bestResults) {
            tableModel.addRow(result);
        }

        resultsTable.setModel(tableModel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        return button;
    }
}
