import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board {

    private JPanel panel;

    private int ROWS_COUNT = 10;
    private int COL_COUNT = 10;

    public Board() {
        panel = new JPanel(new GridLayout(ROWS_COUNT, COL_COUNT));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        square.setBackground(Color.red);
                    }  
                });
                panel.add(square);
            }
        }


    }

    public JPanel getPanel() {
        return panel;
    }
}