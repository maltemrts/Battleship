import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.PageAttributes.ColorType;
import java.awt.event.*;
import java.util.NoSuchElementException;

public class ComputerPlayer extends BoardRules {
    private int fieldSize;
    private int ROWS = -1;
    private int COLS = -1;
    private JPanel[][] panels;
    public ArrayList<ArrayList<Integer>> GameField;
    public static ArrayList<Integer> boatSizes;
    public static int totalCellsToHitPlayer = 0;
    public static boolean playerHasShot = false;




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

        

        // Create the panel that will hold the board
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
        // Add the board panel to the center of the frame
        frame.add(boardPanel, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(20, 0));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        frame.add(eastPanel, BorderLayout.EAST);

        // Erstelle ein JFrame für die Darstellung des JPanel
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(frame);
        jFrame.pack();

        //frame.setResizable(false);
        // Center the frame on the screen
       // frame.setLocationRelativeTo(null);
       // frame.setVisible(true);

       placeShipsOnBoard(panels, GameField, boatSizes);

       for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            panels[i][j].setBackground(null);
        }}
       

        return frame; 
    }

    public ArrayList<ArrayList<Integer>> placeShipsOnComputerBoard(ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer> boatSizes) {
        ArrayList<ArrayList<Integer>> generatedField = GameField;
        int maxTries = 0;
        Random random = new Random();
        int size = GameField.size()-1;

        while(!boatSizes.isEmpty() || maxTries < 100)
        {
            int placeRow = random.nextInt(size)+0;
            int placeCol = random.nextInt(size)+0;

            // 1 = Vertikal        2 = Horizontal \\
            int horizontalOrVertical = random.nextInt(2) + 1;

            if(horizontalOrVertical == 1)
            {
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

            /*
            * maxTries verhindert, dass die while Schleife unnötig lange läuft,
            * sollte der Algorithmus es nicht hinbekommen, die Schiffe richtig zu platzieren 
            */
            maxTries++;
        }

        if(boatSizes.isEmpty())
        {
            //printField(generatedField);
            GameField = generatedField;
        } else {
            placeShipsOnBoard(panels, GameField, boatSizes);
        }
        return GameField;
    }


    public void shoot()
    {
        
    }
}
