public class Cell {
    private int x;
    private int y;
    private boolean hit;
    private boolean occupied;
    private boolean status; // Der Status der Zelle (z.B. "getroffen" oder "nicht getroffen")

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.hit = false;
        this.occupied = false;
        this.status = false; // Zu Beginn ist die Zelle nicht getroffen
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
        this.status = true; // Setze den Status auf "getroffen", wenn die Zelle getroffen wird
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean getStatus() {
        return status;
    }
}


