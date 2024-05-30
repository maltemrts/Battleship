import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ComputerPlayer extends BoardRules {
    private int fieldSize;
    private int ROWS = -1;
    private int COLS = -1;
    private JPanel[][] panels;
    public ArrayList<ArrayList<Integer>> GameField;
    public static ArrayList<Integer> boatSizes;

    public ComputerPlayer(int size, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer> boatSizes) {
        this.ROWS = size;
        this.COLS = size;
        this.panels = new JPanel[ROWS][COLS];
        this.GameField = GameField;
        this.boatSizes = boatSizes;
    }
    public ComputerPlayer(int fieldSize){
        this.fieldSize = fieldSize;
    }


//    public JPanel[][] createAndShowGUI(int size, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer>boatSizes) {
//        JFrame frame = new JFrame("Battlefield");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 600);
//
//        frame.setLayout(new GridLayout(ROWS, COLS));
//
//        for (int i = 0; i < ROWS; i++) {
//            for (int j = 0; j < COLS; j++) {
//                final int row = i;
//                final int col = j;
//                panels[i][j] = new JPanel();
//
//                panels[i][j].setBorder(new LineBorder(Color.BLACK));
//                if (GameField.get(i).get(j) == 1) {
//                    panels[i][j].setBackground(Color.red);
//                }
//            }
//        }
//        return JPanel[][];
//    }

    public JPanel[][] createAndShowGUI(int size, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer> boatSizes) {
        JFrame frame = new JFrame("Battlefield");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        frame.setLayout(new GridLayout(ROWS, COLS));

        panels = new JPanel[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                panels[i][j] = new JPanel();
                panels[i][j].setBorder(new LineBorder(Color.BLACK));
                if (GameField.get(i).get(j) == 1) {
                    panels[i][j].setBackground(Color.RED);
                }
                frame.add(panels[i][j]); // Panel dem Frame hinzufügen
            }
        }

        frame.setVisible(false);
        return panels;
    }

    public void placeShipsOnBoard(JPanel[][] panels, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer> boatSizes) {
        int maxTries = 0;
        Random random = new Random();
        int size = GameField.size() - 1;

        while (!boatSizes.isEmpty() || maxTries < 100) {
            int placeRow = random.nextInt(size);
            int placeCol = random.nextInt(size);

            // 1 = Vertikal        2 = Horizontal \\
            int horizontalOrVertical = random.nextInt(2);

            if (horizontalOrVertical == 1) {
                if (placeShipVertical(panels, GameField, boatSizes, placeRow, placeCol)) {
                    boatSizes.removeLast();
                }
            } else {
                if (placeShipHorizontal(panels, GameField, boatSizes, placeRow, placeCol)) {
                    boatSizes.removeLast();
                }
            }

            /*
             * maxTries verhindert, dass die while Schleife unnötig lange läuft,
             * sollte der Algorithmus es nicht hinbekommen, die Schiffe richtig zu platzieren
             */
            maxTries++;
        }
    }

    public JPanel getPanel() {
        JPanel panel = new JPanel(new GridLayout(ROWS, COLS));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                panel.add(panels[i][j]);
            }
        }

        return panel;
    }
}
