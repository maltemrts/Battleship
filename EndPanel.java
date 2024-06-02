import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Das {@code EndPanel} zeigt das Ergebnis des Spiels und bietet Optionen zum erneuten Spielen oder Beenden an.
 * Es erweitert die {@code JFrame}-Klasse.
 */
public class EndPanel extends JFrame {
    /** Der Gewinner des Spiels. */
    private String winner = "select_User";

    /**
     * Zeigt das Ergebnis des Spiels und die Schaltflächen zum erneuten Spielen oder Beenden an.
     *
     * @param winner Der Gewinner des Spiels ("Du", "Computer" oder "select_User").
     */
    public void endPanelMethod(String winner){
        this.winner = winner;
        setTitle("Spielfeldgröße wählen");
        setLayout(new BorderLayout());

        JLabel winnerLabel;
        if(winner == "Du") {
            winnerLabel = new JLabel("Du hast gewonnen!", JLabel.CENTER);
        }
        else if(winner == "Computer") {

            winnerLabel = new JLabel("Du hast verloren, versuchs nochmal!", JLabel.CENTER);
        } else {
            winnerLabel = new JLabel("Der gewinner konnte nicht identifiziert werden", JLabel.CENTER);
        }


        JButton playAgainButton = new JButton("Nochmal Spielen");
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BattleShip battleShip = new BattleShip();
                battleShip.restartApplication();
                System.exit(0);
            }
        });

        JButton endButton = new JButton("Beenden");
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, playAgainButton.getMinimumSize().height));

        endButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        endButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, endButton.getMinimumSize().height));

        buttonPanel.add(playAgainButton);
        buttonPanel.add(endButton);


        add(winnerLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setSize(325, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }

    /**
     * Setzt den Gewinner des Spiels.
     *
     * @param winner Der Gewinner des Spiels ("Du", "Computer" oder "select_User").
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    /**
     * Gibt den Gewinner des Spiels zurück.
     *
     * @return Der Gewinner des Spiels ("Du", "Computer" oder "select_User").
     */
    public String getWinner() {
        return winner;
    }
}