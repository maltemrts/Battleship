import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class Board extends BoardRules {
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

                                placeShipVertical(panels, GameField, boatSizes, row, col);
                                
                            }
                        } else if (SwingUtilities.isMiddleMouseButton(e)) {
                            // Klicken um Schiff zu drehen
                            if (GameField.get(row).get(col) == 1) {
                                deleteShipAberBesser(panels, GameField, row, col);
                                
                                placeShipHorizontal(panels, GameField, boatSizes, row, col);
                            }
                        }
                        // Schiff entfernen
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            int shipLength = boatSizes.get(boatSizes.size()-1);
                            deleteShipAberBesser(panels,GameField, row, col);
                            
                        }
                    }
                });

                frame.add(panels[i][j]);
            }
        }

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    printField(GameField);
                }
            }
        });

        frame.setResizable(false);
        frame.setVisible(true);

        placeShipsOnBoard(panels, GameField, boatSizes);
        
    }
}
