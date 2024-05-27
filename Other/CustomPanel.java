package Other;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class CustomPanel extends JPanel {
    private int row = -1;
    private int col = -1;
    private Boolean isOccupied = false;

    public CustomPanel() {
        
    }

    public void changeColor(Color newColor) {
        this.setBackground(newColor);
    }



    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return this.row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol(int col) {
        return this.col;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
    
    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
    
}
