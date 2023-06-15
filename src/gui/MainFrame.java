package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame { // Основной фрейм приложения, все остальные области находятся внутри него

    private Application app;
    private CenterPanel centerPanel;
    private NorthPanel north;
    private JPanel south;
    private JPanel west;
    private JPanel east;
    public MainFrame(Application app){
        this.app = app;
        this.setTitle("MainFrame");
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(1000,500));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        this.centerPanel = new CenterPanel(app);

        this.north = new NorthPanel(app);
//        this.north.setBackground(Color.CYAN);
        this.south = new JPanel();
        this.south.setBackground(Color.MAGENTA);
        this.west = new JPanel();
        this.west.setBackground(Color.GREEN);
        this.east = new JPanel();
        this.east.setBackground(Color.ORANGE);

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(north,BorderLayout.NORTH);
        this.add(south,BorderLayout.SOUTH);
        this.add(west,BorderLayout.WEST);
        this.add(east,BorderLayout.EAST);
        this.setVisible(true);
    }

    public void setContent(StringBuilder textContent, StringBuilder hexContent){
        this.centerPanel.setContent(textContent,hexContent);
    }

}