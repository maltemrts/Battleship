import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;
/*
 * Author: Mark Zuckerberg himself
 */
import java.util.Random;

/**
 * Die Klasse {@code Board} erbt von {@code BoardRules} und repräsentiert das Spielfeld des Spiels.
 * Sie enthält Methoden zur Erstellung der Benutzeroberfläche und zur Durchführung von Schüssen.
 */
public class Board extends BoardRules {
    /**
     * Die Anzahl der Reihen im Spielfeld.
     */
    private int ROWS = -1;

    /**
     * Die Anzahl der Spalten im Spielfeld.
     */
    private int COLS = -1;

    /**
     * Ein zweidimensionales Array von {@code JPanel}, das das Spielfeld darstellt.
     */
    private JPanel[][] panels;

    /**
     * Eine zweidimensionale {@code ArrayList} von {@code Integer}, die das Spielfeld darstellt.
     */
    public ArrayList<ArrayList<Integer>> GameField;

    /**
     * Eine {@code ArrayList} von {@code Integer}, die die Größen der Boote enthält.
     */
    public static ArrayList<Integer> boatSizes;

    /**
     * Ein Flag, das anzeigt, ob das Spiel bereit ist zu starten.
     */
    public static boolean isReadyToStart = false;

    /**
     * Die Gesamtanzahl der Zellen, die der Computer treffen muss.
     */
    public static int totalCellsToHitComputer = 0;

    /**
     * Die Größe des Spielfeldes.
     */
    public static int size = 0;

    /**
     * Ein Zufallsgenerator für die Schusspositionen des Computers.
     */
    public static Random random;

    /**
     * Erstellt und zeigt die grafische Benutzeroberfläche (GUI) für das Spielfeld.
     *
     * @param size Die Größe des Spielfeldes (Anzahl der Reihen und Spalten).
     * @param GameField Die Datenstruktur, die das Spielfeld darstellt.
     * @param boatSizes Die Größen der Boote.
     * @param totalCellsToHitComputer Die Gesamtanzahl der Zellen, die der Computer treffen muss.
     * @return Ein {@code JPanel}-Objekt, das die erstellte GUI darstellt.
     */

    public JPanel createAndShowGUI(int size, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer> boatSizes,
            int totalCellsToHitComputer) {
        this.ROWS = size;
        this.COLS = size;
        this.panels = new JPanel[ROWS][COLS];
        this.GameField = GameField;
        this.boatSizes = boatSizes;
        this.totalCellsToHitComputer = totalCellsToHitComputer;
        this.size = size;
        this.random = new Random(size);

        JPanel frame = new JPanel();
        // frame.setDefaultCloseOperation(JPanel.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        JButton horizontal = new JButton("Horizontal");
        JButton vertical = new JButton("Vertikal");

        JLabel header = new JLabel("Platziere deine Schiffe");
        JButton startGame = new JButton("Spiel starten");
        startGame.setVisible(false);

        // Create the panel that will hold the board
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
                        System.out.println(row + " " + col);
                    }
                });

                boardPanel.add(panels[i][j]);
            }

            startGame.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isReadyToStart = true;
                    header.setVisible(false);
                    startGame.setVisible(false);
                }
            });

        }

        // Add the board panel to the center of the frame
        frame.add(boardPanel, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(20, 0));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        frame.add(eastPanel, BorderLayout.EAST);

        // frame.setResizable(false);
        // Center the frame on the screen
        // frame.setLocationRelativeTo(null);
        // frame.setVisible(true);

        // placeShipsOnBoard(panels, GameField, boatSizes);

        return frame;
    }

    /**
     * Führt den Zug des Computers aus.
     * Diese Methode wählt zufällig eine Position auf dem Spielfeld und überprüft,
     * ob an dieser Stelle ein Boot getroffen wurde und verändert die Farbe und den "Wert" des Feldes.
     */
    public void shoot() {
        
        int row = random.nextInt(size) + 0;
        int col = random.nextInt(size) + 0;

        if (GameField.get(row).get(col) == 1) {
            panels[row][col].setBackground(Color.GREEN);
            System.out.println("Getroffen, yeah!");
            GameField.get(row).set(col, 3);
            totalCellsToHitComputer--;
        } else if (GameField.get(row).get(col) == 0 || GameField.get(row).get(col) == 2) {
            panels[row][col].setBackground(Color.BLUE);
            System.out.println("Verfehlt!");
        }
        
    }
}
