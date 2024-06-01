import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;

public class Board extends BoardRules {
    private int ROWS = -1;
    private int COLS = -1;
    private JPanel[][] panels;
    public ArrayList<ArrayList<Integer>> GameField;
    public static ArrayList<Integer> boatSizes;
    public static boolean isReadyToStart = false;

    public JPanel createAndShowGUI(int size, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer>boatSizes) {
        this.ROWS = size;
        this.COLS = size;
        this.panels = new JPanel[ROWS][COLS];
        this.GameField = GameField;
        this.boatSizes = boatSizes;


        JPanel frame = new JPanel();
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
        eastPanel.setPreferredSize(new Dimension(20,0));
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        frame.add(eastPanel, BorderLayout.EAST);

        //frame.setResizable(false);
        // Center the frame on the screen
       // frame.setLocationRelativeTo(null);
       // frame.setVisible(true);

       // placeShipsOnBoard(panels, GameField, boatSizes);

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
