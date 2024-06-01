import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;

public class PlacePanel extends BoardRules {
    private int ROWS = -1;
    private int COLS = -1;
    private JPanel[][] panels;
    public ArrayList<ArrayList<Integer>> GameField;
    public static ArrayList<Integer> boatSizes;
    public static boolean isReadyToStart = false;

    public JFrame createAndShowGUI(int size, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer>boatSizes) {
        this.ROWS = size;
        this.COLS = size;
        this.panels = new JPanel[ROWS][COLS];
        this.GameField = GameField;
        this.boatSizes = boatSizes;


        JFrame frame = new JFrame();
        //frame.setDefaultCloseOperation(JPanel.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        JButton horizontal = new JButton("Horizontal");
        JButton vertical = new JButton("Vertikal");
        setButtonStyle(horizontal, true);
        setButtonStyle(vertical, false);

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
                    panels[i][j].setBackground(Color.RED);
                }

                panels[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Schiff platzieren oder drehen
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            // Klicken um Schiff zu platzieren - vertikal
                            if (GameField.get(row).get(col) == 0) {
                                // Actionlistener, ob das Boot horizontal oder vertikal platziert werden soll

                                //Schiff wird horizontal platziert
                                horizontal.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        setButtonStyle(horizontal, true);
                                        setButtonStyle(vertical, false);
                                        
                                    }
                                });
                                
                                //Schiff wird vertikal platziert
                                vertical.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        setButtonStyle(horizontal, false);
                                        setButtonStyle(vertical, true);
                                        
                                    }
                                });

                                if(vertical.getBackground() == Color.GRAY)
                                {
                                    placeShipVertical(panels, GameField, boatSizes, row, col);
                                }
                                else {
                                    placeShipHorizontal(panels, GameField, boatSizes, row, col);
                                }

                                // Zieht die Platzieren Boote ab und gibt eine Meldung aus sollten keine Schiffe mehr übrig sein
                                try
                                {
                                    boatSizes.removeLast();
                                }catch (NoSuchElementException ignore)
                                {
                                    JOptionPane.showMessageDialog(null, "Keine Schiffe mehr Übrig!");
                                }

                                if(boatSizes.isEmpty())
                                {
                                    //Entfernt horizontal - vertikal Buttons um Spiel zu starten
                                    horizontal.setVisible(false);
                                    vertical.setVisible(false);

                                    //Button, um das Spiel zu starten
                                    startGame.setVisible(true);
                                }
                            }

                        } else if (SwingUtilities.isMiddleMouseButton(e)) {
                            // Klicken um Schiff zu drehen
                            if (GameField.get(row).get(col) == 1) {
                                deleteShipAberBesser(panels, GameField, row, col,boatSizes);
                                placeShipHorizontal(panels, GameField, boatSizes, row, col);
                            }
                        }
                        // Schiff entfernen
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            deleteShipAberBesser(panels, GameField, row, col,boatSizes);

                            if(!boatSizes.isEmpty())
                            {
                                horizontal.setVisible(true);
                                vertical.setVisible(true);
                                startGame.setVisible(false);
                            }
                        }
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
                    frame.dispose();
                }
            });
           
        }

        

        // Add the board panel to the center of the frame
        frame.add(boardPanel, BorderLayout.CENTER);

        JPanel northPanel = new JPanel();
        northPanel.add(header);
        frame.add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.add(horizontal);
        southPanel.add(startGame);
        southPanel.add(vertical);
        frame.add(southPanel, BorderLayout.SOUTH);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(20,0));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        frame.add(eastPanel, BorderLayout.EAST);

        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(20,0));
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        frame.add(westPanel, BorderLayout.WEST);

        // Key listener to print the field
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    printField(GameField);
                }
            }
        });

        frame.setResizable(false);
        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //placeShipsOnBoard(panels, GameField, boatSizes);

        return frame; 
    }

    private void setButtonStyle(JButton button, boolean selected) {
        if (selected) {
            // ! Wichtig, Algorithmus check welcher Button ausgewählt ist am Background
            button.setBackground(Color.GRAY);
            button.setForeground(Color.BLACK);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }
    }

    public boolean getIsReadyToStart()
    {
        return isReadyToStart;
    }
}
