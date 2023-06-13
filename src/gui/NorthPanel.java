package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class NorthPanel extends JPanel {

    private JButton open;
    private JButton newFile;
    private JButton save;
    private Application app;

    public NorthPanel(Application app){
        this.app = app;
        this.open = new JButton("OPEN");
        this.add(open);
        this.newFile = new JButton("NEW");
        this.add(newFile);
        this.save = new JButton("SAVE");
        this.add(save);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Выберите файл"); // Название диалогового окна
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Режим выбора только файлов

                int result = fileChooser.showOpenDialog(new JPanel()); // Открытие диалогового окна выбора файла

                if (result == JFileChooser.APPROVE_OPTION) { // Обработка выбора файла
                    File selectedFile = fileChooser.getSelectedFile(); // Получение выбранного файла

                    String filename = selectedFile.getAbsolutePath();
                    try {
                        app.onFileChange(filename);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                }
        });

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
    }

}
