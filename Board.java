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

        JPanel[][] field = new JPanel[ROWS_COUNT][COL_COUNT];

        for (int i = 0; i < ROWS_COUNT; i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                field[i][j] = new JPanel();
                field[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                final int row = i;
                final int col = j;
                field[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        field[row][col].setBackground(Color.red);
                        
                    }  
                });
                panel.add(field[i][j]);
            }
        }


    }

    public JPanel getPanel() {
        return panel;
    }
}