package gui;

import logic.Application;
import logic.ByteFileReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class NorthPanel extends JMenuBar {
    private String fileName;
    private String path;
    private Application app;

    public NorthPanel(Application app){
        this.app = app;

        JComboBox width = new JComboBox();
        width.setMaximumSize(new Dimension(50,50));
        width.addItem(8);
        width.addItem(16);
        width.addItem(32);

        JButton open = new JButton("Open");

        JButton save = new JButton("Save");

        JMenu file = new JMenu("File");
        file.add(open);
        file.add(save);

// метод отслеживание выбора пользователем файла
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(file);

                if(result == JFileChooser.APPROVE_OPTION){
                    File selected = fileChooser.getSelectedFile();
                    fileName = selected.getName();
                    path = selected.getPath();
                    try {
                        app.readFile(path);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

// метод сохранение по нажатию нового файла
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean res;
                ByteFileReader reader = app.getReader();
                reader.setData(app.getData());
                res = reader.save("moded_"+reader.getFilename());
                if(res){
                    JOptionPane.showMessageDialog(null, "File successfully saved");
                }
                else JOptionPane.showMessageDialog(null, "Something go wrong, try again");
            }
        });
// метод отслеживание изменения пользователем количества колонок текста
        width.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = (int)width.getSelectedItem();
                app.setWidth(selected);
            }
        });

        this.add(file);
        this.add(width);

        this.setVisible(true);
    }

}
