import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.w3c.dom.events.MouseEvent;

/**
 * @author Malte Martens
 * @author Leonie Hahn
 * @author Oliver Hartmann
 * @version 1.0 (release)
 *
 * */

/**
 * Die Klasse {@code BattleShip} enthält die Mainmethode.
 */

public class BattleShip {
    /**
     * Die Gesamtanzahl der Zellen, die der Spieler treffen muss.
     */
    public static int totalCellsToHitPlayer = 0;

    /**
     * Die Gesamtanzahl der Zellen, die der Computer treffen muss.
     */
    public static int totalCellsToHitComputer = 0;

    /*
     * Regeln für GameField:
     * 0: Feld ist nicht besetzt und nicht in der Nähe eines Schiffes
     * 1: Schiff befindet sich auf dem Feld
     * 2: Feld befindet sich neben einem Schiff
     * 3: Feld beschossen

     * Standard Spiel:
     * Größe 10x10
     * 4x2 Schiffe
     * 3x3 Schiffe
     * 2x4 Schiffe
     * 1x5 Schiff
     */
    /**
     * Eine {@code ArrayList} von {@code Integer}, die die Größen der Boote enthält.
     */
    public static ArrayList<Integer> boatSizes = new ArrayList<>();

    /**
     * Eine {@code ArrayList} von {@code Integer}, die die Größen der Boote des Benutzers enthält.
     */
    public static ArrayList<Integer> userBoatSizes = new ArrayList<>();

    /**
     * Eine {@code ArrayList} von {@code Integer}, die die Größen der Boote des Computers enthält.
     */
    public static ArrayList<Integer> computerBoatSizes = new ArrayList<>();

    /**
     * Eine zweidimensionale {@code ArrayList} von {@code Integer}, die das Spielfeld des Benutzers darstellt.
     */
    public static ArrayList<ArrayList<Integer>> UserBoard = new ArrayList<>();

    /**
     * Eine zweidimensionale {@code ArrayList} von {@code Integer}, die das Spielfeld des Computers darstellt.
     */
    public static ArrayList<ArrayList<Integer>> ComputerBoard = new ArrayList<>();

    /**
     * Einstiegspunkt des Programms.
     * Diese Methode startet die Startoberfläche des Spiels und wartet,
     * bis eine Spielfeldgröße ausgewählt wurde hat.
     *
     * @param args Kommandozeilenargumente, die beim Start des Programms übergeben werden.
     */

    public static void main(String[] args) {


        StartPanel panel = new StartPanel();

        while(panel.isActive())
        {
            // Panel ist aktiv, bis eine Größenauswahl getroffen wurde
            try {
                Thread.sleep(100); // Damit die schleife nicht unbegrenzt oft durchläuft ohne Pause --> führte zu fehlern
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int size = panel.size;
        boolean allParamsSet = panel.allParamsSet;
        

        if(allParamsSet)
        {
            userBoatSizes = (ArrayList<Integer>) panel.selectedFleet.clone();
            computerBoatSizes = (ArrayList<Integer>) panel.selectedFleet.clone();
            boatSizes = (ArrayList<Integer>) panel.selectedFleet.clone();

            totalCellsToHitPlayer = countallBoatCells();
            totalCellsToHitComputer = countallBoatCells();

           System.out.println(computerBoatSizes);
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
     * Es wird ein Spielfeld erstellt und initialisiert mit der angegebenen Größe.
     * Das Spielfeld wird als zweidimensionale {@code ArrayList} von {@code Integer} dargestellt,
     * jede Zelle mit dem Wert 0 initialisiert wird.
     *
     * @param size Die Größe des Spielfeldes (Anzahl der Reihen und Spalten).
     * @return Eine zweidimensionale {@code ArrayList} von {@code Integer}, die das initialisierte Spielfeld darstellt.
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
     * Öffnet das Spielfeld.
     * Diese Methode erstellt das Spielfeld für den Spieler und den Computer, zeigt das Hauptfenster an
     * und startet den Spielablauf.
     *
     * @param size Die Größe des Spielfeldes (Anzahl der Reihen und Spalten).
     */
    public void openGameBoard(int size){

        Board board = new Board();

        JPanel playerBoard = board.createAndShowGUI(size, UserBoard, userBoatSizes, totalCellsToHitComputer);
        /* */
        // JPanel computerBoard = board.createAndShowGUI(size, ComputerBoard, boatSizes);
        ComputerPlayer computerPlayer = new ComputerPlayer();
        
        JPanel computerBoardPanel = computerPlayer.createAndShowGUI(size, ComputerBoard, computerBoatSizes , totalCellsToHitPlayer);

        JFrame mainFrame = new JFrame("Battleship");
        mainFrame.setSize(1200, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(1, 2));
        mainFrame.add(playerBoard);
        mainFrame.add(computerBoardPanel);

        // Schiffe für den Computer platzieren
        //computerBoardPanels.placeShipsOnBoard(computerBoardPanels, ComputerBoard, computerBoatSizes);
    
        //mainFrame.pack();
        mainFrame.setVisible(true);

      /*  JFrame mainFrame = new JFrame("Main Frame");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(playerBoard);
        mainFrame.setVisible(true);*/

        while(ComputerPlayer.totalCellsToHitPlayer != 0 && Board.totalCellsToHitComputer != 0)
        {
            try {// Das, sonst zu schnell für Computer (lowperformer)
                Thread.sleep(100); // 100 Millisekunden warten
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(ComputerPlayer.playerHasShot) {
                board.shoot();
                ComputerPlayer.playerHasShot = false;
                System.out.println("Computer shot");
                System.out.println(ComputerPlayer.totalCellsToHitPlayer);
            }
        }

        mainFrame.dispose();
        System.out.println("Computer tot");
        EndPanel endPanel = new EndPanel();
        String winner;
        winner = (ComputerPlayer.totalCellsToHitPlayer !=0)? "Computer" : "Du";
        endPanel.endPanelMethod(winner);

    }

    /**
     * Zählt die Gesamtanzahl der Zellen, die von allen Booten belegt werden.
     * Also die länge aller Boote werden zusammen gerechnet um sie mit allen Treffern abzugleichen.
     *
     * @return Die Gesamtanzahl der Zellen, die von allen Booten belegt werden.
     */
    private static Integer countallBoatCells()
    {
        int totalCount = 0;
        for(int boat : boatSizes)
        {   
            totalCount += boat;
        }

        return totalCount;
    }
    /**
     * Diese Methode startet die Anwendung neu.
     * Sie wird verwendet, um die aktuelle JVM zu beenden und eine neue JVM-Instanz der Anwendung zu starten.
     * Dies geschieht, indem der Java-Binärpfad, die Prozess-ID der aktuellen JVM, der Klassenpfad der Anwendung
     * sowie der Hauptklassenname und deren Argumente abgerufen werden, um dann einen neuen Prozess mit diesen
     * Informationen zu starten. Nach dem Start des neuen Prozesses wird die aktuelle JVM beendet.
     *  */
    public void restartApplication() {

        String javaBin = System.getProperty("java.home") + "/bin/java";

        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

        // Führt die Mainklasse wieder aus
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
