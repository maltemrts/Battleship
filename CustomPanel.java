import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomPanel extends JPanel {
    private int row = -1;
    private int col = -1;
    private Boolean isOccupied = false;

    public CustomPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!isOccupied) {
                    changeColor(Color.RED);
                    isOccupied = true;
                }
            }

            public void mousePressed(MouseEvent e) {
                if(isOccupied) {
                    changeColor(null);
                    isOccupied = false;
                }

            }

            public void mouseReleased(MouseEvent e) {
                if(!isOccupied) {
                    changeColor(Color.RED);
                    isOccupied = true;
                }
            }

        });
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
}
