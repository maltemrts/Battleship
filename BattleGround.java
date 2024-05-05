import javax.swing.*;
import java.awt.*;

public class BattleGround {
    
    public BattleGround()
    {
        Board board1 = new Board();
        Board board2 = new Board();

        // Erstellen eines übergeordneten Containers
        JFrame frame = new JFrame("Zwei Kästchenfelder nebeneinander");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2)); // GridLayout mit einer Zeile und zwei Spalten

        // Hinzufügen der Kästchenfelder zum übergeordneten Container
        frame.add(board1.getPanel());
        frame.add(board2.getPanel());

        // Anpassen der Größe des Frames und Anzeigen
        frame.setSize(1000, 500); // Breite verdoppelt, um Platz für beide Kästchenfelder zu schaffen
        frame.setVisible(true);
    }
}
