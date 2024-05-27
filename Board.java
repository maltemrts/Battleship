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
    public static ArrayList<Integer> boatSizes;

    public Board(int size, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer>boatSizes) {
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

                                placeShipVertical(row, col);
                                
                            }
                        } else if (SwingUtilities.isMiddleMouseButton(e)) {
                            // Klicken um Schiff zu drehen
                            if (GameField.get(row).get(col) == 1) {
                                deleteShipAberBesser(row, col);

                                // Schiff drehen
                                if (boatSizes.get(boatSizes.size())-1 > 1) {
                                    int shipLength = boatSizes.get(boatSizes.size()-1);
                                    int spaceLeft = 0;
                                    int spaceRight = 0;

                                    // Felder Links
                                    for (int left = 1; left <= shipLength - 1; left++) {
                                        if (col - left > -1) {
                                            if (GameField.get(row).get(col - left) == 0) {
                                                spaceLeft++;
                                            }
                                        } else
                                            break;
                                    }

                                    // Felder Rechts
                                    for (int right = 1; right <= shipLength - 1; right++) {
                                        if (col + right < COLS) {
                                            if (GameField.get(row).get(col + right) == 0) {
                                                spaceRight++;
                                            }
                                        } else
                                            break;
                                    }

                                    if (spaceRight + spaceLeft + 1 >= shipLength) {

                                        int halfShipLength = shipLength / 2;

                                        // Berechne den tats채chlichen Platzbedarf nach oben und unten
                                        int leftSpaces = Math.min(spaceLeft, halfShipLength);
                                        int rightSpaces = shipLength - (leftSpaces + 1);

                                        // wenn unten kein Platz mehr ist platziert er 3 felder oberhalb
                                        if (spaceRight == 0) {
                                            for (int i = 0; i <= spaceLeft; i++) {
                                                setWaterSorroundingHorizontal(row, col - i, true);
                                                GameField.get(row).set(col - i, 1);
                                                panels[row][col - i].setBackground(Color.RED);
                                            }
                                        }
                                        // Platzieren des Schiffs nach oben
                                        for (int i = 0; i <= leftSpaces; i++) {
                                            setWaterSorroundingHorizontal(row, col - i, true);
                                            GameField.get(row).set(col - i, 1);
                                            panels[row][col - i].setBackground(Color.RED);
                                        }

                                        // Platzieren des Schiffs nach unten
                                        for (int i = 1; i <= rightSpaces; i++) {
                                            try {
                                                setWaterSorroundingHorizontal(row, col + i, false);
                                                GameField.get(row).set(col + i, 1);
                                                panels[row][col + i].setBackground(Color.RED);
                                            } catch (IndexOutOfBoundsException Ignore) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // Schiff entfernen
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            int shipLength = boatSizes.get(boatSizes.size()-1);
                            deleteShipAberBesser(row, col);
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

    public void setWaterSorroundingVertical(int row, int col, boolean isUpwards) {
        int num = -1;

        if (isUpwards)
            num = 1;

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
        try {// unten links
            GameField.get(row + num).set(col - 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {// unten mitte
            GameField.get(row + num).set(col, 1);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {// unten rechts
            GameField.get(row + num).set(col + 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
    }

    public void setWaterSorroundingHorizontal(int row, int col, boolean isLeft) {
        int num = -1;

        if (isLeft)
            num = 1;

        /*
         * Angaben basieren auf Links, bei Rechts spiegelverkehrt
         * Links Mitte, Links Oben Links unten
         * Oben, Unten
         */
        try { // Mitte
            GameField.get(row).set(col - num, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try { // Oben
            GameField.get(row - num).set(col - num, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try { // Unten
            GameField.get(row + num).set(col - num, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try { // Oben
            GameField.get(row + num).set(col, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try { // Unten
            GameField.get(row - num).set(col, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
    }

    private void deleteShip(int row, int col, int shipLength) {
        // Entfernt setzt das geklickte Feld zurück
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

    private void deleteShipAberBesser(int row, int col) {
        int totalShipLength = 0;

        if (GameField.get(row).get(col) == 1) {
            GameField.get(row).set(col, 0);
            panels[row][col].setBackground(null);
            deleteWaterSorrounding(row, col);
            totalShipLength++;

            try {// All the way up
                int height = 1;
                while (true) {
                    if (GameField.get(row - height).get(col) == 1) {
                        GameField.get(row - height).set(col, 0);
                        panels[row - height][col].setBackground(null);
                        totalShipLength++;
                        deleteWaterSorrounding(row - height, col);
                        height++;
                    } else
                        break;
                }
            } catch (IndexOutOfBoundsException Ignore) {
            }

            try {// All the way down (Iu Management)
                int height = 1;
                while (true) {
                    if (GameField.get(row + height).get(col) == 1) {
                        GameField.get(row + height).set(col, 0);
                        panels[row + height][col].setBackground(null);
                        totalShipLength++;
                        deleteWaterSorrounding(row + height, col);
                        height++;
                    } else
                        break;
                }
            } catch (IndexOutOfBoundsException Ignore) {
            }

            try {// Left
                int height = 1;
                while (true) {
                    if (GameField.get(row).get(col - height) == 1) {
                        GameField.get(row).set(col - height, 0);
                        panels[row][col - height].setBackground(null);
                        totalShipLength++;
                        deleteWaterSorrounding(row, col - height);
                        height++;
                    } else
                        break;
                }
            } catch (IndexOutOfBoundsException Ignore) {
            }

            try { // Right ()
                int height = 1;
                while (true) {
                    if (GameField.get(row).get(col + height) == 1) {
                        GameField.get(row).set(col + height, 0);
                        panels[row][col + height].setBackground(null);
                        totalShipLength++;
                        deleteWaterSorrounding(row, col + height);
                        height++;
                    } else
                        break;
                }
            } catch (IndexOutOfBoundsException Ignore) {
            }
        }

    }

    private void deleteWaterSorrounding(int row, int col) {
        try {
            if (GameField.get(row - 1).get(col - 1) == 2) {
                GameField.get(row - 1).set(col - 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row - 1).get(col) == 2) {
                GameField.get(row - 1).set(col, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row - 1).get(col + 1) == 2) {
                GameField.get(row - 1).set(col + 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row).get(col - 1) == 2) {
                GameField.get(row).set(col - 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row).get(col + 1) == 2) {
                GameField.get(row).set(col + 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row + 1).get(col - 1) == 2) {
                GameField.get(row + 1).set(col - 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row + 1).get(col) == 2) {
                GameField.get(row + 1).set(col, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row + 1).get(col + 1) == 2) {
                GameField.get(row + 1).set(col + 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
    }

    private void checkPanelStatus(int row, int col) {
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

    private void placeShipVertical(int row, int col) {
        int spaceUp = 0;
        int spaceDown = 0;

        int shipLength = boatSizes.get(boatSizes.size()-1);

        // Felder Oberhalb
        for (int up = 1; up <= shipLength; up++) {
            if (row - up > -1) {
                if (GameField.get(row - up).get(col) == 0) {
                    spaceUp++;
                }
            } else
                break;
        }

        // Felder unterhalb
        for (int down = 1; down <= shipLength - 1; down++) {
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
            int downSpaces = (spaceDown == 0) ? 0 : shipLength - (upSpaces + 1);

            // wenn unten kein Platz mehr ist platziert er 3 felder oberhalb
            if (spaceDown == 0) {
                for (int i = 0; i < spaceUp; i++) {
                    setWaterSorroundingVertical(row - i, col, true);
                    GameField.get(row - i).set(col, 1);
                    panels[row - i][col].setBackground(Color.RED);
                }
            }
            // Platzieren des Schiffs nach oben
            for (int i = 0; i <= upSpaces; i++) {
                setWaterSorroundingVertical(row - i, col, true);
                GameField.get(row - i).set(col, 1);
                panels[row - i][col].setBackground(Color.RED);
            }

            // Platzieren des Schiffs nach unten
            for (int i = 1; i <= downSpaces; i++) {
                try {
                    setWaterSorroundingVertical(row + i, col, false);
                    GameField.get(row + i).set(col, 1);
                    panels[row + i][col].setBackground(Color.RED);
                } catch (IndexOutOfBoundsException Ignore) {
                }
            }

            
        }
    }
}
