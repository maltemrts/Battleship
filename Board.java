import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Board {
    private int ROWS = -1;
    private int COLS = -1;
    private JPanel[][] panels;
    public ArrayList<ArrayList<Integer>> GameField;
    public static int[] boatSizes;

    public Board(int size, ArrayList<ArrayList<Integer>> GameField, int boatSizes[]) {
        this.ROWS = size;
        this.COLS = size;
        this.panels = new JPanel[ROWS][COLS];
        this.GameField = GameField;
        this.boatSizes = boatSizes;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Battlefield");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        frame.setLayout(new GridLayout(ROWS, COLS));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                final int row = i;
                final int col = j;
                panels[i][j] = new JPanel();

                panels[i][j].setBorder(new LineBorder(Color.BLACK));
                if (GameField.get(i).get(j) == 1) {
                    panels[i][j].setBackground(Color.red);
                }

                panels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Schiff platzieren oder drehen
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            // Klicken um Schiff zu platzieren - vertikal
                            if (GameField.get(row).get(col) == 0) {

                                int shipLength = boatSizes[boatSizes.length - 1];

                                if (shipLength % 2 != 0) {
                                    if(shipLength == 1) {
                                        try {
                                            GameField.get(row - 1).set(col, 2);
                                            GameField.get(row - 1).set(col - 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}

                                        try {
                                            GameField.get(row + 1).set(col, 2);
                                            GameField.get(row + 1).set(col - 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}
                                        try {
                                            GameField.get(row - 1).set(col, 2);
                                            GameField.get(row - 1).set(col + 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}

                                        try {
                                            GameField.get(row + 1).set(col, 2);
                                            GameField.get(row + 1).set(col + 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}
                                    }
                                    shipLength /= 2;
                                    boolean isPlaceable = true;

                                    // Felder überhalb
                                    for (int up = 1; up <= shipLength; up++) {
                                        if (row - up > -1) {
                                            if (GameField.get(row - up).get(col) != 0) {
                                                isPlaceable = false;
                                            }
                                        } else
                                            isPlaceable = false;
                                    }

                                    // Felder unterhalb
                                    for (int down = 1; down <= shipLength; down++) {
                                        if (row + down < ROWS) {
                                            if (GameField.get(row + down).get(col) != 0) {
                                                isPlaceable = false;
                                            }
                                        } else
                                            isPlaceable = false;
                                    }

                                    // Wenn alles frei, Felder platzieren
                                    if (isPlaceable) {
                                        for (int up = 1; up <= shipLength; up++) {

                                            try { // Oberhalb
                                                GameField.get(row - up - 1).set(col, 2);
                                                GameField.get(row - up - 1).set(col + 1, 2);
                                                GameField.get(row - up - 1).set(col - 1, 2);
                                            } catch (IndexOutOfBoundsException Ignore) {
                                            }

                                            try { // Links
                                                GameField.get(row - up).set(col - 1, 2);
                                                GameField.get(row + up).set(col - 1, 2);
                                            } catch (IndexOutOfBoundsException Ignore) {
                                            }

                                            try { // Rechts                                               
                                                GameField.get(row + up).set(col + 1, 2);
                                                GameField.get(row - up).set(col + 1, 2);
                                            } catch (IndexOutOfBoundsException Ignore) {
                                            }

                                            try { // Unterhalb
                                                GameField.get(row + up + 1).set(col, 2);
                                                GameField.get(row + up + 1).set(col + 1, 2);
                                                GameField.get(row + up + 1).set(col - 1, 2);
                                            } catch (IndexOutOfBoundsException Ignore) {
                                            }

                                            GameField.get(row - up).set(col, 1);                                        
                                            GameField.get(row + up).set(col, 1);

                                            panels[row + up][col].setBackground(Color.RED);
                                            panels[row - up][col].setBackground(Color.RED);
                                        }

                                        // Angeklicktes Feld färben
                                        panels[row][col].setBackground(Color.RED);
                                        try {
                                            GameField.get(row).set(col + 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}

                                        try {
                                            GameField.get(row).set(col - 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}
                                    }

                                } else {

                                    if(shipLength == 1) {
                                        try {
                                            GameField.get(row - 1).set(col, 2);
                                            GameField.get(row - 1).set(col - 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}

                                        try {
                                            GameField.get(row + 1).set(col, 2);
                                            GameField.get(row + 1).set(col - 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}
                                        try {
                                            GameField.get(row - 1).set(col, 2);
                                            GameField.get(row - 1).set(col + 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}

                                        try {
                                            GameField.get(row + 1).set(col, 2);
                                            GameField.get(row + 1).set(col + 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}
                                    }

                                }

                            }

                            // Klicken um Schiff zu drehen
                            if (GameField.get(row).get(col) == 1) {

                            }

                        }
                        // Schiff entfernen
                        else if (SwingUtilities.isRightMouseButton(e)) {

                        }
                    }
                });

                frame.add(panels[i][j]);
            }
        }

        frame.setResizable(false);
        frame.setVisible(true);
    }

}
