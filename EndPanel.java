import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndPanel extends JFrame {
        private String winner = "select_User";

        public void endPanelMethod(){
        setTitle("Spielfeldgröße wählen");
        setLayout(new BorderLayout());

        JLabel winnerLabel = new JLabel("Der Gewinner ist: " + winner, SwingConstants.CENTER);


        JButton beendenButton = new JButton("Beenden");
            beendenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BattleShip battleShip = new BattleShip();
                battleShip.main(null);
                System.exit(0);
            }
        });
        add(beendenButton, BorderLayout.SOUTH);

        add(winnerLabel);
        setSize(325, 150);
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