import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Die {@code EndPanel}-Klasse stellt das Endspiel-Panel dar und ermöglicht es dem Benutzer,
 * zwischen dem Spielen erneut zu wählen oder das Spiel zu beenden.
 * @author Leonie Hahn
 * @author Malte Martens
 * @author Oliver Hartmann
 * @version 1.0
 */
public class EndPanel extends JFrame {

    /** Der Gewinner des Spiels. */
    private String winner = "select_User";

    /**
     * Zeigt das Endspiel-Panel an.
     *
     * @param winner Der Gewinner des Spiels ("Du", "Computer" oder "select_User", bei einem Fehler).
     */
    public void endPanelMethod(String winner){
        this.winner = winner;
        setTitle("Spielfeldgröße wählen");
        setLayout(new BorderLayout());

        JLabel winnerLabel;
        if(winner.equals("Du")) {
            winnerLabel = new JLabel("Du hast gewonnen!", JLabel.CENTER);
        } else if(winner.equals("Computer")) {
            winnerLabel = new JLabel("Du hast verloren, versuchs nochmal!", JLabel.CENTER);
        } else {
            winnerLabel = new JLabel("Der Gewinner konnte nicht identifiziert werden", JLabel.CENTER);
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
     * @param winner Der Gewinner des Spiels ("Du", "Computer" oder "select_User, bei einem Fehler").
     */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    /**
     * Gibt den Gewinner des Spiels zurück.
     *
     * @return Der Gewinner des Spiels ("Du", "Computer" oder "select_User, bei einem Fehler").
     */
    public String getWinner() {
        return winner;
    }
}
