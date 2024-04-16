import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MonopolyGame extends JFrame {
    private Board board;
    private JTextArea textArea;
    private JButton playTurnButton;

    public MonopolyGame() {
        super("Monopoly Game");
        board = new Board();
        textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        playTurnButton = new JButton("Play Turn");

        // Set up the layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playTurnButton);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the play turn button
        playTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playTurn();
            }
        });

        // Set frame properties
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void playTurn() {
        textArea.append("\n-------------------------\n");
        for (Player player : board.getPlayers()) {
            textArea.append(player.getName() + "'s Turn:\n");
            player.move((int) (Math.random() * 6) + 1);
            Property property = board.getProperties().get(player.getCurrPosition());
            textArea.append("Moved to: " + property.getBlockname() + "\n");
            // Add more logic here as needed
        }
    }

    public static void main(String[] args) {
        new MonopolyGame();
    }
}
