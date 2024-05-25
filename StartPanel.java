import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JFrame {
    public int size = 10;

    public StartPanel() {
        setTitle("Spielfeldgröße wählen");
        setLayout(new FlowLayout());
        setVisible(true);
        
        JLabel label = new JLabel("Wähle die Spielfeldgröße:");
        add(label);
        
        String[] groessen = {"10x10", "8x8", "5x5"};
        JComboBox<String> comboBox = new JComboBox<>(groessen);
        add(comboBox);
        

        
        JButton startButton = new JButton("Start Game");
        add(startButton);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String auswahl = (String) comboBox.getSelectedItem();
                
                switch(auswahl)
                {
                    case "5x5":
                        size = 5;
                        break;
                    case "8x8":
                        size = 8;
                        break;
                    case "10x10":
                        size = 10;
                        break;
                    
                    default: 
                    break;
                }
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
