import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Leonie Hahn
 * @author Malte Martens
 * @author Oliver Hartmann
 * @version 1.0
 * Die Klasse {@code StartPanel} stellt die Benutzeroberfläche zum Starten des Spiels bereit.
 * Der Benutzer kann die Spielfeldgröße und die Flotte auswählen.
 */
public class StartPanel extends JFrame {
    /** Die Größe des Spielfeldes. */
    public int size = 10;
    /** Die ausgewählte Flotte des Benutzers. */
    public ArrayList<Integer> selectedFleet = new ArrayList<>();
    /** Ein boolean-Wert, der angibt, ob alle erforderlichen Parameter festgelegt wurden. */
    public boolean allParamsSet = false;

    /**
     * Konstruktor für die {@code StartPanel}-Klasse.
     * Erstellt die Benutzeroberfläche und initialisiert die erforderlichen Komponenten.
     */
    public StartPanel() {
        setTitle("Spielfeldgröße wählen");
        setLayout(new FlowLayout());
        setVisible(true);
        
        JLabel label = new JLabel("Wähle die Spielfeldgröße:");
        add(label);
        
        String[] groessen = {"10x10", "8x8", "5x5"};
        JComboBox<String> comboBox = new JComboBox<>(groessen);
        add(comboBox);

        String[] kleineFlotte = {"2,2,3"};
        String[] mittlereFlotte = {"2,2,2,2,3,3,4"};
        String[] groesseFlotte = {"2,2,2,2,3,3,3,4,4,5"};

        JLabel flotteLabel = new JLabel("Wähle deine Flotte:");
        add(flotteLabel);
        flotteLabel.setVisible(false);

        JComboBox<String> flotte = new JComboBox<>();
        add(flotte);
        flotte.setVisible(false);
        
        JButton startButton = new JButton("Start Game");
        add(startButton);
        startButton.setEnabled(false);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String auswahl = (String) comboBox.getSelectedItem();
                
                switch(auswahl)
                {
                    case "5x5":
                        size = 5;
                        flotte.setModel(new DefaultComboBoxModel<>(new String[]{"kleine Flotte"}));
                        flotte.setSelectedItem("kleine Flotte");
                        flotte.setEnabled(false);
                        selectedFleet = new ArrayList<>(Arrays.asList(2, 2, 3));
                        break;
                    case "8x8":
                        size = 8;
                        flotte.setModel(new DefaultComboBoxModel<>(new String[]{"kleine Flotte", "mittlere Flotte"}));
                        flotte.setEnabled(true);
                        break;
                    case "10x10":
                        size = 10;
                        flotte.setModel(new DefaultComboBoxModel<>(new String[]{"kleine Flotte", "mittlere Flotte", "große Flotte"}));
                        flotte.setEnabled(true);
                        break;
                    
                    default: 
                    break;
                }
                flotteLabel.setVisible(true);
                flotte.setVisible(true);
            }
        });

        flotte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String auswahl = (String) flotte.getSelectedItem();

                switch(auswahl) {
                    case "kleine Flotte":
                        selectedFleet = new ArrayList<>(Arrays.asList(2, 2, 3));
                        break;
                    case "mittlere Flotte":
                        selectedFleet = new ArrayList<>(Arrays.asList(2, 2, 2, 2, 3, 3, 4));
                        break;
                    case "große Flotte":
                        selectedFleet = new ArrayList<>(Arrays.asList(2, 2, 2, 2, 3, 3, 3, 4, 4, 5));
                        break;
                    default:
                        break;
                }
                startButton.setEnabled(true);
            }
        });
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allParamsSet = true;
                dispose();
            }
        });
        
        setSize(325, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
