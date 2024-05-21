import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Board extends JPanel {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int PANEL_SIZE = 30; // Größe eines einzelnen Panels (30x30 Pixel)
    private CustomPanel[][] panels = new CustomPanel[ROWS][COLS];

    public Board() {
        createAndShowGUI();
        setPreferredSize(new Dimension(COLS * PANEL_SIZE, ROWS * PANEL_SIZE));
    }

    private void createAndShowGUI() {
        // Set the layout for the board
        setLayout(new GridLayout(ROWS, COLS));

        // Initialize and add the panels
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                panels[i][j] = new CustomPanel();

                panels[i][j].setRow(i);
                panels[i][j].setCol(j);

                panels[i][j].setBorder(new LineBorder(Color.BLACK));
                panels[i][j].setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
                add(panels[i][j]);
            }
        }
    }

    // Method to change the color of a specific panel from outside
    public void changePanelColor(int row, int col, Color color) {
        if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
            panels[row][col].changeColor(color);
        }
    }
}
