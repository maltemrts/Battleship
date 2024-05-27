package Other;
import java.util.ArrayList;

public class Ship {
    private int length;
    private ArrayList<ArrayList<Integer>> occupiedCells;

    public Ship(int length, ArrayList<ArrayList<Integer>> occupiedCells) {
        this.length = length;
        this.occupiedCells = new ArrayList<>();
    }
    
    public int getLength() {
        return length;
    }

    public ArrayList<ArrayList<Integer>> getOccupiedCells() {
        return occupiedCells;
    }
}

