import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.PageAttributes.ColorType;
import java.awt.event.*;
import java.util.NoSuchElementException;

/**
 * Diese Klasse repräsentiert einen Computergegner im Spiel.
 * Der ComputerPlayer erstellt und zeigt die grafische Benutzeroberfläche (GUI) für das Spielfeld.
 * Der Spieler kann auf dem Feld des Computers klicken um seine Schiffe zu treffen
 */

public class ComputerPlayer extends BoardRules {
    /**
     * Die Anzahl der Zeilen im Spielfeld.
     */
    private int ROWS = -1;

    /**
     * Die Anzahl der Spalten im Spielfeld.
     */
    private int COLS = -1;

    /**
     * Eine Matrix von JPanel-Objekten, die die Spielfeldgrafik darstellen.
     */
    private JPanel[][] panels;

    /**
     * Eine Datenstruktur, die das Spielfeld repräsentiert.
     */
    public ArrayList<ArrayList<Integer>> GameField;

    /**
     * Die Größen der Boote, die auf dem Spielfeld platziert werden sollen.
     */
    public static ArrayList<Integer> boatSizes;

    /**
     * Die Gesamtanzahl der Zellen, die der Spieler treffen muss, um das Spiel zu gewinnen.
     */
    public static int totalCellsToHitPlayer = 0;

    /**
     * Eine Flagge, die angibt, ob der Spieler bereits geschossen hat oder nicht.
     */
    public static boolean playerHasShot = false;

    /**
     * Erstellt und zeigt die grafische Benutzeroberfläche (GUI) für das Spielfeld.
     *
     * @param size Die Größe des Spielfeldes.
     * @param GameField Die Datenstruktur, die das Spielfeld darstellt.
     * @param boatSizes Die Größen der Boote.
     * @param totalCellsToHitPlayer Die Gesamtanzahl der Zellen, die der Spieler treffen muss.
     * @return Ein {@code JPanel}-Objekt, das die erstellte GUI darstellt.
     */

    public JPanel createAndShowGUI(int size, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer>boatSizes, int totalCellsToHitPlayer) {
        this.ROWS = size;
        this.COLS = size;
        this.panels = new JPanel[ROWS][COLS];
        this.GameField = GameField;
        this.boatSizes = boatSizes;
        this.totalCellsToHitPlayer = totalCellsToHitPlayer;
        

        JPanel frame = new JPanel();
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        // Panel auf dem das Board generiert wird
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLS));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                final int row = i;
                final int col = j;
                panels[i][j] = new JPanel();
                panels[i][j].setBorder(new LineBorder(Color.BLACK));
                if (GameField.get(i).get(j) == 1) {
                    panels[i][j].setBackground(Color.GRAY);
                }

                panels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                       if(GameField.get(row).get(col) != 3)
                       {
                        if(GameField.get(row).get(col) == 1)
                       {
                            panels[row][col].setBackground(Color.GREEN);
                            System.out.println("Getroffen, yeah!");
                            GameField.get(row).set(col, 3);
                            ComputerPlayer.totalCellsToHitPlayer--;       
                       }else if (GameField.get(row).get(col) == 0 || GameField.get(row).get(col) == 2)
                       {
                           panels[row][col].setBackground(Color.BLUE);
                           System.out.println("Verfehlt!");
                       }
                       ComputerPlayer.playerHasShot = true;
                       }
                    }
                });

                boardPanel.add(panels[i][j]);
            }
           
        }

        // Spielfeld wird ins Zentrum platziert
        frame.add(boardPanel, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(20,0));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        frame.add(eastPanel, BorderLayout.EAST);
        placeShipsOnBoard(panels, GameField, boatSizes);
        return frame; 
    }

}
