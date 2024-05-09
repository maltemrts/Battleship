import java.util.ArrayList;

public class Ship {
    private String shipName;
    private int length;
    private ArrayList<Cell> occupiedCells;

    public Ship(String shipName, int length) {
        this.shipName = shipName;
        this.length = length;
        this.occupiedCells = new ArrayList<>();
    }

    public String getShipName() {
        return shipName;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<Cell> getOccupiedCells() {
        return occupiedCells;
    }
}

