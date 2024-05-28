import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

public class BoardRules {
    public void placeShipsOnBoard(JPanel[][] panels, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer> boatSizes)
    {
        ArrayList<ArrayList<Integer>> generatedField = GameField;
        int maxTries = 0;
        Random random = new Random();
        int size = GameField.size()-1;

        while(!boatSizes.isEmpty() || maxTries < 100)
        {
            int placeRow = random.nextInt(size)+0;
            int placeCol = random.nextInt(size)+0;

            // 1 = Vertikal        2 = Horizontal \\
            int horizontalOrVertical = random.nextInt(2) + 1;

            if(horizontalOrVertical == 1)
            {
                if(placeShipVertical(panels, generatedField, boatSizes, placeRow, placeCol)){
                    boatSizes.removeLast();
                }
                else if(placeShipHorizontal(panels, generatedField, boatSizes, placeRow, placeCol)){
                    boatSizes.removeLast();
                }
            } else {
                if(placeShipHorizontal(panels, generatedField, boatSizes, placeRow, placeCol)){
                    boatSizes.removeLast();
                }
                else if(placeShipVertical(panels, generatedField, boatSizes, placeRow, placeCol)){
                    boatSizes.removeLast();
                }
            }

            /*
            * maxTries verhindert, dass die while Schleife unnötig lange läuft,
            * sollte der Algorithmus es nicht hinbekommen, die Schiffe richtig zu platzieren 
            */
            maxTries++;
        }

        if(boatSizes.isEmpty())
        {
            //printField(generatedField);
            GameField = generatedField;
        } else {
            placeShipsOnBoard(panels, GameField, boatSizes);
        }
    }

    public boolean placeShipVertical(JPanel[][] panels, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer>boatSizes, int row, int col)
    {
        if(!boatSizes.isEmpty())
        {
            int length = boatSizes.getLast();

            if(GameField.get(row).get(col) == 0)
            {
            int spaceUp = 0;
            // Space Up
            for(int l = 1; l < length; l++)            
            {
                try
                {
                    if(GameField.get(row - l).get(col) == 0)
                    {
                        spaceUp++;
                    }else break;
                } catch(IndexOutOfBoundsException Ignore) {}
            }

            int spaceDown = 0;
            // Space Down
            for(int r = 1; r < length; r++)
            {
                try
                {
                    if(GameField.get(row + r).get(col) == 0)
                    {
                        spaceDown++;
                    } else break;
                } catch(IndexOutOfBoundsException Ignore) {}
            }

            if(spaceUp + spaceDown + 1 >= length)
            {
                GameField.get(row).set(col, 1);
                panels[row][col].setBackground(Color.RED);
                setWaterSorroundingVertical(GameField, row, col, true);
                setWaterSorroundingVertical(GameField, row, col, false);
                length--;

                for(int l = 1; l <= spaceUp; l++)
                {
                    if(length > 0)
                    {
                        GameField.get(row - l).set(col, 1);
                        panels[row - l][col].setBackground(Color.RED);
                        setWaterSorroundingVertical(GameField, row - l, col, true);

                        length--;
                    } else break;
                }

                for(int r = 1; r <= spaceDown; r++)
                {
                    if(length > 0)
                    {
                        GameField.get(row + r).set(col, 1);
                        panels[row + r][col].setBackground(Color.RED);
                        setWaterSorroundingVertical(GameField, row + r, col, false);

                        length--;

                    }else break; 
                }

                return true;
            }
        }
        }

        return false;
    }

    public boolean placeShipHorizontal(JPanel[][] panels, ArrayList<ArrayList<Integer>> GameField, ArrayList<Integer>boatSizes, int row, int col)
    {
        if(!boatSizes.isEmpty())
        {
            int length = boatSizes.getLast();

        if(GameField.get(row).get(col) == 0)
        {
            int spaceLeft = 0;
            // Space Left
            for(int l = 1; l < length; l++)            
            {
                try
                {
                    if(GameField.get(row).get(col - l) == 0)
                    {
                        spaceLeft++;
                    }else break;
                } catch(IndexOutOfBoundsException Ignore) {}
            }

            int spaceRight = 0;
            // Space Right
            for(int r = 1; r < length; r++)
            {
                try
                {
                    if(GameField.get(row).get(col + r) == 0)
                    {
                        spaceRight++;
                    } else break;
                }catch(IndexOutOfBoundsException Ignore) {}
            }

            if(spaceLeft + spaceRight + 1 >= length)
            {
                GameField.get(row).set(col, 1);
                panels[row][col].setBackground(Color.RED);
                setWaterSorroundingHorizontal(GameField, row, col, true);
                setWaterSorroundingHorizontal(GameField, row, col, false);
                length--;

                for(int l = 1; l <= spaceLeft; l++)
                {
                    if(length > 0)
                    {
                        GameField.get(row).set(col - l, 1);
                        panels[row][col - l].setBackground(Color.RED);
                        setWaterSorroundingHorizontal(GameField, row, col - l, true);

                        length--;
                    } else break;
                }

                for(int r = 1; r <= spaceRight; r++)
                {
                    if(length > 0)
                    {
                        GameField.get(row).set(col + r, 1);
                        panels[row][col + r].setBackground(Color.RED);
                        setWaterSorroundingHorizontal(GameField, row, col + r, false);

                        length--;

                    }else break;
                }

                return true;
            }
        }
        }
        
        return false;
    }
    
    public void setWaterSorroundingVertical(ArrayList<ArrayList<Integer>> GameField,int row, int col, boolean isUpwards) {
        int num = -1;

        if (isUpwards)
            num = 1;

        /*
         * Oben Links, Oben Mitte, Oben Rechts
         * Links, Rechts
         */
        try { // Oben Links
            GameField.get(row - num).set(col - 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }

        try { // Oben Mitte
            GameField.get(row - num).set(col, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }

        try { // Oben Rechts
            GameField.get(row - num).set(col + 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }

        try { // Links
            GameField.get(row).set(col - 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }

        try { // Rechts
            GameField.get(row).set(col + 1, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
    }

    public void setWaterSorroundingHorizontal(ArrayList<ArrayList<Integer>> GameField, int row, int col, boolean isLeft) {
        int num = -1;

        if (isLeft)
            num = 1;

        /*
         * Angaben basieren auf Links, bei Rechts spiegelverkehrt
         * Links Mitte, Links Oben Links unten
         * Oben, Unten
         */
        try { // Mitte
            if(GameField.get(row).get(col - num) != 1)GameField.get(row).set(col - num, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try { // Oben
            if(GameField.get(row - num).get(col - num) != 1)GameField.get(row - num).set(col - num, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try { // Unten
            if(GameField.get(row + num).get(col - num) != 1)GameField.get(row + num).set(col - num, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try { // Oben
            if(GameField.get(row + num).get(col) != 1)GameField.get(row + num).set(col, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try { // Unten
            if(GameField.get(row - num).get(col) != 1)GameField.get(row - num).set(col, 2);
        } catch (IndexOutOfBoundsException Ignore) {
        }
    }

    public void deleteShipAberBesser(JPanel[][] panels, ArrayList<ArrayList<Integer>> GameField, int row, int col) {
        int totalShipLength = 0;

        if (GameField.get(row).get(col) == 1) {
            GameField.get(row).set(col, 0);
            panels[row][col].setBackground(null);
            deleteWaterSorrounding(GameField, row, col);
            totalShipLength++;

            try {// All the way up
                int height = 1;
                while (true) {
                    if (GameField.get(row - height).get(col) == 1) {
                        GameField.get(row - height).set(col, 0);
                        panels[row - height][col].setBackground(null);
                        totalShipLength++;
                        deleteWaterSorrounding(GameField, row - height, col);
                        height++;
                        printField(GameField);
                    } else
                        break;
                }
            } catch (IndexOutOfBoundsException Ignore) {
            }

            try {// All the way down (Iu Management)
                int height = 1;
                while (true) {
                    if (GameField.get(row + height).get(col) == 1) {     
                        GameField.get(row + height).set(col, 0);
                        panels[row + height][col].setBackground(null);
                        totalShipLength++;
                        deleteWaterSorrounding(GameField, row + height, col);
                        height++;
                        printField(GameField);
                    } else
                        break;
                }
            } catch (IndexOutOfBoundsException Ignore) {
            }

            try {// Left
                int height = 1;
                while (true) {
                    if (GameField.get(row).get(col - height) == 1) {
                        GameField.get(row).set(col - height, 0);
                        panels[row][col - height].setBackground(null);
                        totalShipLength++;
                        deleteWaterSorrounding(GameField, row, col - height);
                        height++;
                    } else
                        break;
                }
            } catch (IndexOutOfBoundsException Ignore) {
            }

            try { // Right ()
                int height = 1;
                while (true) {
                    if (GameField.get(row).get(col + height) == 1) {
                        GameField.get(row).set(col + height, 0);
                        panels[row][col + height].setBackground(null);
                        totalShipLength++;
                        deleteWaterSorrounding(GameField, row, col + height);
                        height++;
                    } else
                        break;
                }
            } catch (IndexOutOfBoundsException Ignore) {
            }

        }

    }

    public void deleteWaterSorrounding(ArrayList<ArrayList<Integer>> GameField, int row, int col) {
        try {
            if (GameField.get(row - 1).get(col - 1) == 2 && noShipAround(GameField, row - 1, col - 1)) {
                
                GameField.get(row - 1).set(col - 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row - 1).get(col) == 2 && noShipAround(GameField, row - 1, col)) {
                GameField.get(row - 1).set(col, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row - 1).get(col + 1) == 2 && noShipAround(GameField, row - 1, col + 1)) {
                GameField.get(row - 1).set(col + 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row).get(col - 1) == 2 && noShipAround(GameField, row, col - 1)) {
                GameField.get(row).set(col - 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row).get(col + 1) == 2 && noShipAround(GameField, row, col + 1)) {
                GameField.get(row).set(col + 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row + 1).get(col - 1) == 2 && noShipAround(GameField, row + 1, col - 1)) {
                GameField.get(row + 1).set(col - 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row + 1).get(col) == 2 && noShipAround(GameField, row + 1, col)) {
                GameField.get(row + 1).set(col, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row + 1).get(col + 1) == 2 && noShipAround(GameField, row + 1, col + 1)) {
                GameField.get(row + 1).set(col + 1, 0);
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }

    }

    private boolean noShipAround(ArrayList<ArrayList<Integer>> GameField, int row, int col)
    {
        try {
            if (GameField.get(row - 1).get(col - 1) == 1) {
                
                return false;
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row - 1).get(col) == 1) {
                return false;
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row - 1).get(col + 1) == 1) {
                return false;
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row).get(col - 1) == 1) {
                return false;
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row).get(col + 1) == 1) {
                return false;
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row + 1).get(col - 1) == 1) {
                return false;
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row + 1).get(col) == 1) {
                return false;
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }
        try {
            if (GameField.get(row + 1).get(col + 1) == 1) {
                return false;
            }
        } catch (IndexOutOfBoundsException Ignore) {
        }

        return true;
    }

    public void checkPanelStatus(ArrayList<ArrayList<Integer>> GameField, int row, int col) {
        try {
            int status = GameField.get(row).get(col);
            switch (status) {
                case 0:
                    System.out.println("Das Feld (" + row + ", " + col + ") ist frei.");
                    break;
                case 1:
                    System.out.println("Das Feld (" + row + ", " + col + ") enthält ein Schiff.");
                    break;
                case 2:
                    System.out.println("Das Feld (" + row + ", " + col + ") ist Wasser neben einem Schiff.");
                    break;
                default:
                    System.out.println("Unbekannter Status für das Feld (" + row + ", " + col + ").");
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Das Feld (" + row + ", " + col + ") ist außerhalb des Spielfelds.");
        }
    }

    public void printField(ArrayList<ArrayList<Integer>> GameField)
    {
        for (ArrayList<Integer> row : GameField) {
            System.out.println(row);
        }

        System.out.println("<-------- -------->");
    }
}
