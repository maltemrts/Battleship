import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.w3c.dom.events.MouseEvent;

public class BattleShip {
    public int size;


    public ArrayList<Integer> selectedFleet = new ArrayList<>();

    /*
     * Regeln für GameField:
     * 0: Feld ist nicht besetzt und nicht in der Nähe eines Schiffes
     * 1: Schiff befindet sich auf dem Feld
     * 2: Feld befindet sich neben einem Schiff
     */
    public static ArrayList<Integer> boatSizes = new ArrayList<>(Arrays.asList(1,1,2));
    public static ArrayList<ArrayList<Integer>> UserBoard = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> ComputerBoard = new ArrayList<>();
    /*
     * Standard Spiel:
     * Größe 10x10
     * 4x2 Schiffe
     * 3x3 Schiffe
     * 2x4 Schiffe
     * 1x5 Schiff
     */

    public static void main(String[] args) {

        // UserBoard und ComputerBoard initialisieren
        //ArrayList<ArrayList<Integer>> userBoard = new ArrayList<>();
        //ArrayList<ArrayList<Integer>> computerBoard = new ArrayList<>();
        
        StartPanel panel = new StartPanel();

        while(panel.isActive())
        {
            // Panel ist aktiv, bis eine Größenauswahl getroffen wurde
            try {
                Thread.sleep(100); // 100 Millisekunden warten
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int size = panel.size;
        boolean allParamsSet = panel.allParamsSet;

        if(allParamsSet)
        {
            UserBoard = setField(size);
            ComputerBoard = setField(size);

            PlacePanel placeShips = new PlacePanel();
            placeShips.createAndShowGUI(size, UserBoard, boatSizes);
            
            ///ArrayList<Integer> selectedFleet = panel.selectedFleet;

        System.out.println("Spielfeldgröße: " + size);

        PlacePanel placePanel = new PlacePanel();
            while (!placePanel.getIsReadyToStart()) {
                // Hier können Sie eine kleine Pause einlegen, um eine endlose Schleife zu vermeiden
                try {
                    Thread.sleep(100); // 100 Millisekunden warten
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            BattleShip battleShip = new BattleShip();
            battleShip.openGameBoard(size);

        }
    }

    public static ArrayList<ArrayList<Integer>> setField(int size) {
        ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            arrayList.add(row);
        }
        return arrayList;
    }
    public void openGameBoard(int size){

        Board board = new Board();

        // Array erstellt für den User kopiert boatsizes
        ArrayList<Integer> userBoatSizes = new ArrayList<>(boatSizes);

        // Array für computer kopiert boatsizes
        ArrayList<Integer> computerBoatSizes = new ArrayList<>(boatSizes);

        //Board board = new Board(size, UserBoard, boatSizes);
        JPanel playerBoard = board.createAndShowGUI(size, UserBoard, userBoatSizes);
        /* */
        // JPanel computerBoard = board.createAndShowGUI(size, ComputerBoard, boatSizes);
        ComputerPlayer computerPlayer = new ComputerPlayer();
        computerPlayer.placeShipsOnBoard(ComputerBoard, computerBoatSizes);
        JPanel computerBoard = computerPlayer.createAndShowGUI(size, ComputerBoard, computerBoatSizes);

        JFrame mainFrame = new JFrame("Battleship");
        mainFrame.setSize(1200, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1, 2));
        mainFrame.add(playerBoard);
        mainFrame.add(computerBoard);

        // Schiffe für den Computer platzieren
        //computerBoardPanels.placeShipsOnBoard(computerBoardPanels, ComputerBoard, computerBoatSizes);
    
        //mainFrame.pack();
        mainFrame.setVisible(true);

      /*  JFrame mainFrame = new JFrame("Main Frame");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(playerBoard);
        mainFrame.setVisible(true);*/
    }
}
