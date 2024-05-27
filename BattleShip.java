import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class BattleShip {
    public int size;

    /*
     * Regeln für GameField:
     * 0: Feld ist nicht besetzt und nicht in der Nähe eines Schiffes
     * 1: Schiff befindet sich auf dem Feld
     * 2: Feld befindet sich neben einem Schiff
     */
    public static ArrayList<ArrayList<Integer>> GameField = new ArrayList<>();

    public static ArrayList<Integer> boatSizes = new ArrayList<>(Arrays.asList(2, 2, 2, 2, 3, 3, 3, 4, 4, 4));
    /*
     * Standard Spiel:
     * Größe 10x10
     * 4x2 Schiffe
     * 3x3 Schiffe
     * 2x4 Schiffe
     * 1x5 Schiff
     */

    public static void main(String[] args) {

        
        StartPanel panel = new StartPanel();

        while(panel.isActive())
        {
            // Panel ist aktiv, bis eine Größenauswahl getroffen wurde
        }
        int size = panel.size;
       
        GameField = setField(size);
        GameField.get(2).set(0, 1);
        Board board = new Board(size, GameField, boatSizes);

       /**
        * Ziel heute:
            - Spielfeldgröße einstellbar CHECK
            - Zu jeder Spielfeldgröße Schiffe festlegen
            - Schiffe platzierbar machen
            - Drehbar, entfernbar
        */
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
}


