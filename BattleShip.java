import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;

/**
 * @author Leonie Hahn
 * @author Malte Martens
 * @author Oliver Hartmann
 * @version 1.0
 * die {@code BattleShip}-Klasse implementiert das Hauptprogramm für das Spiel Battleship.
 * Es ermöglicht das Starten des Spiels, die Verwaltung der Spielbretter und die Steuerung
 * des Spielflusses zwischen Benutzer- und Computeraktionen.
 */
public class BattleShip{

    /** Die Größe des Spielbretts. */
    public int size;

    /** Die Gesamtanzahl der Zellen, die der Spieler treffen muss. */
    public static int totalCellsToHitPlayer = 0;

    /** Die Gesamtanzahl der Zellen, die der Computer treffen muss. */
    public static int totalCellsToHitComputer = 0;

    /*
     * Regeln für GameField:
     * 0: Feld ist nicht besetzt und nicht in der Nähe eines Schiffes
     * 1: Schiff befindet sich auf dem Feld
     * 2: Feld befindet sich neben einem Schiff
     */

    /** Die Größen der Boote im Spiel. */
    public static ArrayList<Integer> boatSizes = new ArrayList<>();

    /** Die Größen der Boote des Benutzers. */
    public static ArrayList<Integer> userBoatSizes = new ArrayList<>();

    /** Die Größen der Boote des Computers. */
    public static ArrayList<Integer> computerBoatSizes = new ArrayList<>();

    /** Das Spielbrett des Benutzers. */
    public static ArrayList<ArrayList<Integer>> UserBoard = new ArrayList<>();

    /** Das Spielbrett des Computers. */
    public static ArrayList<ArrayList<Integer>> ComputerBoard = new ArrayList<>();

    /**
     * Die Hauptmethode des Spiels, die das Spiel startet und den Spielablauf verwaltet.
     *
     * @param args Eingabeparameter.
     */
    public static void main(String[] args) {

        StartPanel panel = new StartPanel();

        // Warten bis die Größenauswahl getroffen wurde
        while(panel.isActive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int size = panel.size;
        boolean allParamsSet = panel.allParamsSet;

        if(allParamsSet) {
            userBoatSizes = (ArrayList<Integer>) panel.selectedFleet.clone();
            computerBoatSizes = (ArrayList<Integer>) panel.selectedFleet.clone();
            boatSizes = (ArrayList<Integer>) panel.selectedFleet.clone();

            totalCellsToHitPlayer = countallBoatCells();
            totalCellsToHitComputer = countallBoatCells();

            UserBoard = setField(size);
            ComputerBoard = setField(size);

            PlacePanel placeShips = new PlacePanel();
            placeShips.createAndShowGUI(size, UserBoard, userBoatSizes);

            PlacePanel placePanel = new PlacePanel();
            while (!placePanel.getIsReadyToStart()) {
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

    /**
     * Initialisiert ein Spielbrett der ausgewählten Größe.
     *
     * @param size Legt die Größe des Spielbretts.
     * @return Ein zweidimensionales Array, das das leere Spielbrett darstellt.
     */
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

    /**
     * Öffnet das Spielbrett mit den gegebenen Parametern.
     *
     * @param size Die Größe des Spielbretts.
     */
    public void openGameBoard(int size){
        Board board = new Board();

        JPanel playerBoard = board.createAndShowGUI(size, UserBoard, userBoatSizes, totalCellsToHitComputer);

        ComputerPlayer computerPlayer = new ComputerPlayer();

        JPanel computerBoardPanel = computerPlayer.createAndShowGUI(size, ComputerBoard, computerBoatSizes , totalCellsToHitPlayer);

        JFrame mainFrame = new JFrame("Battleship");
        mainFrame.setSize(1200, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1, 2));
        mainFrame.add(playerBoard);
        mainFrame.add(computerBoardPanel);

        mainFrame.setVisible(true);

        while(ComputerPlayer.totalCellsToHitPlayer != 0 && Board.totalCellsToHitComputer != 0)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(ComputerPlayer.playerHasShot) {
                board.shoot();
                ComputerPlayer.playerHasShot = false;
            }
        }

        mainFrame.dispose();
        EndPanel endPanel = new EndPanel();
        String winner = (ComputerPlayer.totalCellsToHitPlayer != 0)? "Computer" : "Du";
        endPanel.endPanelMethod(winner);
    }

    /**
     * Rechnet die länge der Schiffe zusammen.
     *
     * @return Die Gesamtanzahl der Zellen aller Boote.
     */
    private static Integer countallBoatCells() {
        int totalCount = 0;
        for(int boat : boatSizes) {
            totalCount += boat;
        }
        return totalCount;
    }

    /**
     * Startet die Anwendung neu, indem sie eine neue JVM-Instanz startet und die aktuelle beendet.
     */
    public void restartApplication() {
        String javaBin = System.getProperty("java.home") + "/bin/java";
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        String jarPath = System.getProperty("java.class.path");
        String className = System.getProperty("sun.java.command").split(" ")[0];
        String[] command = {
                javaBin,
                "-cp",
                jarPath,
                className
        };

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.start();
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
