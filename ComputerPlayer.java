import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Die {@code ComputerPlayer}-Klasse verwaltet das Spielbrett und die Aktionen des Computers
 * im Spiel "Schiffe versenken".
 * @author Leonie Hahn
 * @author Malte Martens
 * @author Oliver Hartmann
 * @version 1.0
 */
public class ComputerPlayer extends BoardRules {

    /** Die Größe des Spielfelds. */
    private int fieldSize;

    /** Die Anzahl der Zeilen auf dem Spielfeld. */
    private int ROWS = -1;

    /** Die Anzahl der Spalten auf dem Spielfeld. */
    private int COLS = -1;

    /** Die Panels, die das Spielfeld repräsentieren. */
    private JPanel[][] panels;

    /** Das Spielfeld des Computers. */
    public ArrayList<ArrayList<Integer>> GameField;

    /** Die Größen der Boote des Computers. */
    public static ArrayList<Integer> boatSizes;

    /** Die Gesamtanzahl der Zellen, die der Spieler treffen muss. */
    public static int totalCellsToHitPlayer = 0;

    /** Ein Flag, das angibt, ob der Spieler geschossen hat. */
    public static boolean playerHasShot = false;

    /**
     * Erstellt das GUI für den Computer-Spieler und gibt das Panel zurück.
     *
     * @param size Die Größe des Spielfelds.
     * @param GameField Das Spielfeld.
     * @param boatSizes Die Größen der Boote des Computers.
     * @param totalCellsToHitPlayer Die Gesamtanzahl der Zellen, die der Spieler treffen muss.
     * @return Das Panel, das das Spielfeld des Computers darstellt.
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

        JLabel score = new JLabel("Du musst noch " + totalCellsToHitPlayer + " Zellen treffen", SwingConstants.CENTER);

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLS));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                final int row = i;
                final int col = j;
                panels[i][j] = new JPanel();
                panels[i][j].setBorder(new LineBorder(Color.BLACK));

                panels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (GameField.get(row).get(col) != 3) {
                            if (GameField.get(row).get(col) == 1) {
                                panels[row][col].setBackground(Color.GREEN);
                                System.out.println("Getroffen, yeah!");
                                GameField.get(row).set(col, 3);
                                ComputerPlayer.totalCellsToHitPlayer--;
                                score.setText("Du musst noch " + ComputerPlayer.totalCellsToHitPlayer + " Zellen treffen");
                            } else if (GameField.get(row).get(col) == 0 || GameField.get(row).get(col) == 2) {
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

        JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.add(score, BorderLayout.CENTER);
        frame.add(scorePanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(20, 0));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        frame.add(eastPanel, BorderLayout.EAST);

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(frame);
        jFrame.pack();

        placeShipsOnBoard(panels, GameField, boatSizes);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                panels[i][j].setBackground(null);
            }}


        return frame;
    }

    /**
     * Platziert die Schiffe auf dem Spielfeld des Computers.
     *
     * @param GameField Das Spielfeld.
     * @param boatSizes Die Größen der Boote des Computers.
     * @return Das Spielfeld mit platzierten Schiffen.
     */
    public ArrayList<ArrayList<Integer>> placeShipsOnComputerBoard(ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer> boatSizes) {
        ArrayList<ArrayList<Integer>> generatedField = GameField;
        int maxTries = 0;
        Random random = new Random();
        int size = GameField.size()-1;

        while(!boatSizes.isEmpty() || maxTries < 100) {
            int placeRow = random.nextInt(size)+0;
            int placeCol = random.nextInt(size)+0;

            int horizontalOrVertical = random.nextInt(2) + 1;

            if(horizontalOrVertical == 1) {
                if(placeShipVertical(panels, generatedField, boatSizes, placeRow, placeCol)){
                    boatSizes.removeLast();
                }
                else if(placeShipHorizontal(panels, generatedField, boatSizes, placeRow, placeCol)){
                    boatSizes.removeLast();
                }
            } else {
                if(placeShipHorizontal(panels, generatedField, boatSizes, placeRow, placeCol)){
                    boatSizes.removeLast();
                }
                else if(placeShipVertical(panels, generatedField, boatSizes, placeRow, placeCol)){
                    boatSizes.removeLast();
                }
            }

            maxTries++;
        }

        if(boatSizes.isEmpty()) {
            GameField = generatedField;
        } else {
            placeShipsOnBoard(panels, GameField, boatSizes);
        }
        return GameField;
    }

}