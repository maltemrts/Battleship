import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndPanel extends JFrame {
        private String winner = "select_User";

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


        JButton beendenButton = new JButton("Beenden");
            beendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BattleShip battleShip = new BattleShip();
                battleShip.restartApplication();
                System.exit(0);
            }
        });
        add(beendenButton, BorderLayout.SOUTH);

        
        add(winnerLabel);
        setSize(325, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }
}