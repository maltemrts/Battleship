import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Cell extends JPanel{
    public boolean placeShipVertical(JPanel[][] panels, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer>boatSizes, int row, int col)
    {
        int length = boatSizes.getLast();

        if(GameField.get(row).get(col) == 0)
        {
            int spaceUp = 0;
            // Space Up
            for(int l = 1; l < length; l++)            
            {
                if(GameField.get(row - l).get(col) == 0)
                {
                    spaceUp++;
                }else break;
            }

            int spaceDown = 0;
            // Space Down
            for(int r = 1; r < length; r++)
            {
                if(GameField.get(row + r).get(col) == 0)
                {
                    spaceDown++;
                } else break;
            }

            if(spaceUp + spaceDown + 1 >= length)
            {
                GameField.get(row).set(col, 1);
                panels[row][col].setBackground(Color.RED);
                setWaterSorroundingVertical(GameField, row, col, true);
                setWaterSorroundingVertical(GameField, row, col, false);
                length--;

                for(int l = 1; l < spaceUp; l++)
                {
                    if(length > 0)
                    {
                        GameField.get(row - l).set(col, 1);
                        panels[row][col - l].setBackground(Color.RED);
                        setWaterSorroundingVertical(GameField, row - l, col, true);

                        length--;
                    } else break;
                }

                for(int r = 1; r < spaceDown; r++)
                {
                    if(length > 0)
                    {
                        GameField.get(row + r).set(col, 1);
                        panels[row][col + r].setBackground(Color.RED);
                        setWaterSorroundingVertical(GameField, row + r, col, false);

                    }else break; 
                }

                return true;
            }
        }

        return false;
    }
}


