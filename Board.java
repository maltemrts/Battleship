import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.awt.Color;

public class Board {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private CustomPanel[][] panels = new CustomPanel[ROWS][COLS];

    public Board() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Erstelle ein JFrame
        JFrame frame = new JFrame("Battlefield");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Setze Layout des JFrame
        frame.setLayout(new GridLayout(ROWS, COLS));

        // Initialisiere und füge die Panels hinzu
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                panels[i][j] = new CustomPanel();

                panels[i][j].setRow(i);
                panels[i][j].setCol(j);

                panels[i][j].setBorder(new LineBorder(Color.BLACK));
                frame.add(panels[i][j]);
            }
        }

        // Mache das JFrame sichtbar
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // Methode zum Ändern der Farbe eines bestimmten Panels von außerhalb
    public void changePanelColor(int row, int col, Color color) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            panels[row][col].changeColor(color);
        }
    }
}
