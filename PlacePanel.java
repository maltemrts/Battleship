import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;
/**
 * @author Leonie Hahn
 * @author Malte Martens
 * @author Oliver Hartmann
 * @version 1.0
 * Die Klasse {@code PlacePanel} ermöglicht es dem Benutzer, Schiffe auf dem Spielfeld zu platzieren.
 * Es erweitert die {@code BoardRules}-Klasse.
 */
public class PlacePanel extends BoardRules {
    /** Die Anzahl der Zeilen im Spielfeld. */
    private int ROWS = -1;
    /** Die Anzahl der Spalten im Spielfeld. */
    private int COLS = -1;
    /** Ein zweidimensionales Array von Panels, das das Spielfeld darstellt. */
    private JPanel[][] panels;
    /** Eine zweidimensionale {@code ArrayList} von {@code Integer}, die das Spielfeld darstellt. */
    public ArrayList<ArrayList<Integer>> GameField;
    /** Eine {@code ArrayList} von {@code Integer}, die die Größen der Boote enthält. */
    public static ArrayList<Integer> boatSizes;
    /** Ein boolean-Wert, der angibt, ob der Benutzer bereit ist, das Spiel zu starten. */
    public static boolean isReadyToStart = false;

    /**
     * Erstellt und zeigt die grafische Benutzeroberfläche (GUI) zum Platzieren der Schiffe auf dem Spielfeld an.
     *
     * @param size       Die Größe des Spielfeldes.
     * @param GameField  Die Datenstruktur, die das Spielfeld darstellt.
     * @param boatSizes  Die Größen der Boote.
     * @return Ein {@code JFrame}-Objekt, das die erstellte GUI darstellt.
     */
    public void createAndShowGUI(int size, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer>boatSizes) {
        this.ROWS = size;
        this.COLS = size;
        this.panels = new JPanel[ROWS][COLS];
        this.GameField = GameField;
        this.boatSizes = boatSizes;


        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        JButton horizontal = new JButton("Horizontal");
        JButton vertical = new JButton("Vertikal");
        setButtonStyle(horizontal, true);
        setButtonStyle(vertical, false);

        JLabel header = new JLabel("Platziere deine Schiffe, Schiffsgröße: " + boatSizes.getLast());
        JButton startGame = new JButton("Spiel starten");
        startGame.setVisible(false);

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

                                boolean succesfull = false;
                                if(vertical.getBackground() == Color.GRAY)
                                {
                                    succesfull = placeShipVertical(panels, GameField, boatSizes, row, col);
                                }
                                else {
                                    succesfull = placeShipHorizontal(panels, GameField, boatSizes, row, col);
                                }

                                // Zieht die Platzieren Boote ab und gibt eine Meldung aus sollten keine Schiffe mehr übrig sein
                                if(succesfull)
                                {
                                    try
                                {
                                    boatSizes.removeLast();
                                    
                                    if(!boatSizes.isEmpty())
                                    {
                                        header.setText("Nächste Schiffslänge: " + boatSizes.getLast());
                                    }
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

                                    header.setText("Alle Schiffe gestartet, starte das Spiel");
                                }
                                }
                            }

                        } else if (SwingUtilities.isMiddleMouseButton(e)) {
                            // Klicken um Schiff zu drehen
                            if (GameField.get(row).get(col) == 1) {
                                deleteShip(panels, GameField, row, col,boatSizes);
                                header.setText("Nächste Schiffslänge: " + boatSizes.getLast());
                                placeShipHorizontal(panels, GameField, boatSizes, row, col);
                                
                                if(!boatSizes.isEmpty()) {
                                    header.setText("Nächste Schiffslänge: " + boatSizes.getLast());
                                }else {
                                    header.setText("Alle Schiffe gestartet, starte das Spiel");
                                }
                            }
                        }
                        // Schiff entfernen
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            deleteShip(panels, GameField, row, col,boatSizes);

                            if(!boatSizes.isEmpty())
                            {
                                horizontal.setVisible(true);
                                vertical.setVisible(true);
                                startGame.setVisible(false);

                                header.setText("Nächste Schiffslänge: " + boatSizes.getLast());
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


        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    printField(GameField);
                }
            }
        });

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * Setzt den Stil der Schaltfläche basierend auf dem ausgewählten Zustand.
     *
     * @param button   Die Schaltfläche, deren Stil gesetzt werden soll.
     * @param selected Der Zustand, ob die Schaltfläche ausgewählt ist oder nicht.
     */
    private void setButtonStyle(JButton button, boolean selected) {
        if (selected) {  // ! Wichtig, Algorithmus checked welcher Button ausgewählt ist am Background
            button.setBackground(Color.GRAY);
            button.setForeground(Color.BLACK);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
        }
    }

    /**
     * Gibt an, ob der Benutzer bereit ist, das Spiel zu starten.
     *
     * @return {@code true}, wenn der Benutzer bereit ist, das Spiel zu starten, sonst {@code false}.
     */
    public boolean getIsReadyToStart() {
        return isReadyToStart;
    }
}