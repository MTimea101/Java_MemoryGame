import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class HighscorePanel extends JPanel {
    private JTable scoreTable;
    private JButton backButton;
    private JComboBox<Difficulty> filterComboBox;

    public HighscorePanel(GameFrame gameFrame, MenuPanel menuPanel, Difficulty currentDifficulty) {
        FileHandler fileHandler = new FileHandler("results_");
        List<String[]> scores = fileHandler.loadAllResults(); // Összes eredmény betöltése

        initializeComponents(scores);
        setupLayout();
        setupButtonListeners(gameFrame, menuPanel);
        setBackground(new Color(240, 234, 214));
    }


    private void initializeComponents(List<String[]> scores) {
        String[] columnNames = {"Player", "Time (s)", "Attempts", "Difficulty"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        scoreTable = new JTable(tableModel);

        // Adatok betöltése a táblázatba
        for (String[] score : scores) {
            tableModel.addRow(score);
        }

        scoreTable.setRowHeight(40);
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        scoreTable.getTableHeader().setBackground(new Color(170, 165, 130));
        scoreTable.getTableHeader().setForeground(Color.BLACK);
        scoreTable.setEnabled(false);

        // Sorok szűrése
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        scoreTable.setRowSorter(sorter);

        // Szűrő legördülő menü
        filterComboBox = new JComboBox<>(Difficulty.values());
        filterComboBox.addActionListener(e -> {
            Difficulty selectedDifficulty = (Difficulty) filterComboBox.getSelectedItem();
            if (selectedDifficulty != null) {
                sorter.setRowFilter(RowFilter.regexFilter(selectedDifficulty.name(), 3)); // 3. oszlop: Difficulty
            }
        });

        backButton = createStyledButton("Back");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(20, 20));

        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(50, 50, 30));

        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterPanel.setBackground(new Color(240, 234, 214));
        filterPanel.add(new JLabel("Filter by Difficulty:"));
        filterPanel.add(filterComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 234, 214));
        buttonPanel.add(backButton);

        add(titleLabel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER); // Szűrő legördülő menü
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupButtonListeners(GameFrame gameFrame, MenuPanel menuPanel) {
        backButton.addActionListener(e -> gameFrame.setPanel(menuPanel));
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(170, 165, 130));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));

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
}
