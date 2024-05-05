import javax.swing.*;
import java.awt.*;

public class Board {

    private JPanel panel;

    public Board() {
        panel = new JPanel(new GridLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel.add(square);
            }
        }


    }

    public JPanel getPanel() {
        return panel;
    }
}
