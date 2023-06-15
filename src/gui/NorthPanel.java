package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// Класс верхней панели приложения. Содержит интерфейс для работы с текстовыми областями
public class NorthPanel extends JPanel {
    private JButton save;
    private Application app;
    private JComboBox<Integer> widthBox;
    public NorthPanel(Application app){
        this.app = app;
        this.save = new JButton("SAVE");
        this.add(save);
        Integer[] items = {4,8,16,32};
        this.widthBox = new JComboBox<Integer>(items);
        widthBox.setSelectedIndex(0);
        this.add(widthBox);

//  Listeners
        // Save button Listener
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Сохранить файл"); // Название диалогового окна

                int userSelection = fileChooser.showSaveDialog(new JPanel());

                if (userSelection == JFileChooser.APPROVE_OPTION) { // Если файл выбран
                    File fileToSave = fileChooser.getSelectedFile(); // Получение выбранного файла
                    app.onSaveFile(fileToSave.getAbsolutePath());
                    System.out.println(fileToSave.getAbsolutePath());
                }

            }
        });

        // widthBox Listener. Change current width
        widthBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int width = widthBox.getSelectedIndex();
                app.setWidth(items[width]);
            }
        });
    }

}
