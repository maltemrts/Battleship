import javax.swing.*;
import java.awt.*;

public class BattleGround {
    
    public BattleGround()
    {
        Board board1 = new Board();
        Board board2 = new Board();


        JFrame frame = new JFrame("Battle Ground");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2)); 

        
        frame.add(board1.getPanel());
        frame.add(board2.getPanel());

        frame.setSize(1000, 500);
        frame.setVisible(true);
    }
}
