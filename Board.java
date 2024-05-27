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
                                        setWaterSorrounding( row,  col, true);
                                        setWaterSorrounding( row,  col, false);
                                    }
                                    shipLength /= 2;
                                    boolean isPlaceable = true;

                                    // Felder 체berhalb
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

                                            setWaterSorrounding( row - up,  col, true);
                                            setWaterSorrounding( row + up,  col, false);

                                            GameField.get(row - up).set(col, 1);
                                            GameField.get(row + up).set(col, 1);

                                            panels[row + up][col].setBackground(Color.RED);
                                            panels[row - up][col].setBackground(Color.RED);
                                        }

                                        // Angeklicktes Feld f채rben
                                        panels[row][col].setBackground(Color.RED);
                                        try {
                                            GameField.get(row).set(col + 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}

                                        try {
                                            GameField.get(row).set(col - 1, 2);
                                        } catch(IndexOutOfBoundsException Ignore) {}
                                    }
                                    // Wenn Schiffgroeße gerade:
                                } else {
                                    boolean isPlaceable = true;
                                    int spaceUp = 0;
                                    int spaceDown = 0;

                                    // Felder 체berhalb
                                    for (int up = 1; up <= shipLength-1; up++) {
                                        if (row - up > -1) {
                                            if (GameField.get(row - up).get(col) == 0) {
                                                spaceUp++;
                                            }
                                        } else
                                            break;
                                    }

                                    // Felder unterhalb
                                    for (int down = 1; down <= shipLength-1; down++) {
                                        if (row + down < ROWS) {
                                            if (GameField.get(row + down).get(col) == 0) {
                                                spaceDown++;
                                            }
                                        } else
                                            break;
                                    }

                                    if (spaceUp + spaceDown + 1 >= shipLength) {

                                        int halfShipLength = shipLength / 2;

                                        // Berechne den tats채chlichen Platzbedarf nach oben und unten
                                        int upSpaces = Math.min(spaceUp, halfShipLength);
                                        int downSpaces = shipLength - (upSpaces + 1);

                                        // wenn unten kein Platz mehr ist platziert er 3 felder oberhalb
                                        if (spaceDown == 0)
                                        {
                                            for (int i = 0; i <= spaceUp; i++) {
                                                setWaterSorrounding(row - i, col, true);
                                                GameField.get(row - i).set(col, 1);
                                                panels[row - i][col].setBackground(Color.RED);
                                            }
                                        }
                                        // Platzieren des Schiffs nach oben
                                        for (int i = 0; i <= upSpaces; i++)
                                        {
                                            setWaterSorrounding(row - i, col, true);
                                            GameField.get(row - i).set(col, 1);
                                            panels[row - i][col].setBackground(Color.RED);
                                        }

                                        // Platzieren des Schiffs nach unten
                                        for (int i = 1; i <= downSpaces; i++) {
                                            try
                                            {
                                                setWaterSorrounding(row + i, col, false);
                                                GameField.get(row + i).set(col, 1);
                                                panels[row + i][col].setBackground(Color.RED);
                                            } catch (IndexOutOfBoundsException Ignore) {}
                                        }
                                    }

                                }

                            }
                        }
                        else if (SwingUtilities.isMiddleMouseButton(e))
                        {
                            // Klicken um Schiff zu drehen
                            if (GameField.get(row).get(col) == 1)
                            {
                                //deleteShip(row, col);
                            }
                        }
                        // Schiff entfernen
                        else if (SwingUtilities.isRightMouseButton(e))
                        {
                            int shipLength = boatSizes[boatSizes.length - 1];
                            deleteShip(row,col,shipLength);
                            System.out.println(GameField.get(row).get(col));
                        }
                    }
                });

                frame.add(panels[i][j]);
            }
        }

        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void setWaterSorrounding(int row, int col, boolean isUpwards)
    {
        int num = -1;

        if (isUpwards) num = 1;

        /*
         * Oben Links, Oben Mitte, Oben Rechts
         * Links, Rechts
         */
        try { // Oben Links
            GameField.get(row - num).set(col - 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }

        try { // Oben Mitte
            GameField.get(row - num).set(col, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }

        try { // Oben Rechts
            GameField.get(row - num).set(col + 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }

        try { // Links
            GameField.get(row).set(col - 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }

        try { // Rechts
            GameField.get(row).set(col + 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {//unten links
            GameField.get(row + num).set(col - 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {//unten mitte
            GameField.get(row + num).set(col, 1);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {//unten rechts
            GameField.get(row + num).set(col + 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
    }
    private void deleteShip(int row, int col, int shipLength)
    {
        //Entfernt setzt das geklickte Feld zurück
        if (GameField.get(row).get(col) == 1 || GameField.get(row).get(col) == 2) {
            GameField.get(row).set(col, 0);
            panels[row][col].setBackground(null);

            // Überprüfen und Zurücksetzen der benachbarten Felder (oben)
            for (int i = 1; i < shipLength; i++) {
                if (row - i >= 0 && GameField.get(row - i).get(col) == 1) {
                    GameField.get(row - i).set(col, 0);
                    panels[row - i][col].setBackground(null);
                }
            }

            // Überprüfen und Zurücksetzen der benachbarten Felder (unten)
            for (int i = 1; i < shipLength; i++) {
                if (row + i < ROWS && GameField.get(row + i).get(col) == 1) {
                    GameField.get(row + i).set(col, 0);
                    panels[row + i][col].setBackground(null);
                }
            }

            // Zurücksetzen der umliegenden Wasserfelder
            for (int i = Math.max(0, row - shipLength + 1); i <= Math.min(ROWS - 1, row + shipLength - 1); i++) {
                for (int j = Math.max(0, col - 1); j <= Math.min(COLS - 1, col + 1); j++) {
                    if (GameField.get(i).get(j) == 2) {
                        GameField.get(i).set(j, 0);
                        panels[i][j].setBackground(null);
                    }
                }
            }
        }
    }

    private void checkPanelStatus(int row, int col)
    {
        try {
            int status = GameField.get(row).get(col);
            switch (status) {
                case 0:
                    System.out.println("Das Feld (" + row + ", " + col + ") ist frei.");
                    break;
                case 1:
                    System.out.println("Das Feld (" + row + ", " + col + ") enthält ein Schiff.");
                    break;
                case 2:
                    System.out.println("Das Feld (" + row + ", " + col + ") ist Wasser neben einem Schiff.");
                    break;
                default:
                    System.out.println("Unbekannter Status für das Feld (" + row + ", " + col + ").");
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Das Feld (" + row + ", " + col + ") ist außerhalb des Spielfelds.");
        }
    }
}
