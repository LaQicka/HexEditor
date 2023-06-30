package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import logic.*;

// Класс верхней панели приложения. Содержит интерфейс для работы с текстовыми областями
public class NorthPanel extends JPanel {
    private JButton save;
    private Application app;
    private JComboBox<Integer> widthBox;
    private JComboBox<Integer> pageBox;
    public NorthPanel(Application app){
        this.app = app;
        this.save = new JButton("SAVE");
        this.add(save);
        Integer[] items = {4,8,16,32};

        this.widthBox = new JComboBox<Integer>(items);
        this.pageBox = new JComboBox<Integer>();
        pageBox.addItem(0);
        widthBox.setSelectedIndex(0);
        this.add(widthBox);
        this.add(pageBox);


//  Listeners
        // Save button Listener
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.onSaveFile();
            }
        });

        // widthBox Listener. Change current width
        widthBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app.setWidth((Integer) widthBox.getSelectedItem());
            }
        });

        pageBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int currentPage = pageBox.getSelectedIndex();
                if(currentPage>=0) app.changePage(currentPage);
            }
        });
    }

    public void setPageAmount(int pageAmount){
        this.pageBox.removeAllItems();
        for(int i=0;i<pageAmount;i++)this.pageBox.addItem(i);
    }

}
