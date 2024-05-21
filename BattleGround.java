import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleGround extends JFrame {
    private JPanel boardPanel;
    private JButton addButton;
    private boolean isTwoBoardsDisplayed = false;
    private static final int BOARD_WIDTH = 300;
    private static final int BOARD_HEIGHT = 300;

    public BattleGround() {
        // Set up the main frame
        setTitle("Battlefield with Button");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH + 100, BOARD_HEIGHT); // Adjust the initial size to accommodate the button
        setLayout(new BorderLayout());

        // Create and add the button
        addButton = new JButton("Add Second Board");
        addButton.addActionListener(new ButtonClickListener());

        // Create the board panel to hold the boards
        boardPanel = new JPanel();
        boardPanel.add(new Board());

        // Create a wrapper panel to ensure fixed size layout
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(boardPanel, BorderLayout.CENTER);

        // Add the button and the wrapper panel to the frame
        add(addButton, BorderLayout.WEST);
        add(wrapperPanel, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isTwoBoardsDisplayed) {
                // Update the layout to hold two boards
                boardPanel.setLayout(new GridLayout(1, 2));
                boardPanel.add(new Board());

                // Update the frame size to accommodate two boards
                setSize(BOARD_WIDTH * 2 + 100, BOARD_HEIGHT);

                isTwoBoardsDisplayed = true;

                // Hide the button after clicking
                JButton clickedButton = (JButton) e.getSource(); // Get the clicked button
                clickedButton.setVisible(false); // Hide the clicked button

                // Refresh the main frame
                revalidate();
                repaint();
            }
        }
    }

    public static void main(String[] args) {
        // Erstelle und zeige das Hauptfenster
        SwingUtilities.invokeLater(() -> new BattleGround());
    }
}
