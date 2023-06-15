package MenuApplication;

import gui.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// Класс - главное меню приложения. Открытие и создание файлов происходят через него
public class MenuApplication {

    private JFrame MainFrame;
    private JPanel MainPanel;

    public MenuApplication(){
//  Basic Frame Config
        this.MainFrame = new JFrame("MENU");
        this.MainFrame.setMinimumSize(new Dimension(300,300));

        this.MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.MainFrame.setLocationRelativeTo(null);
        this.MainFrame.setVisible(true);

//  Panel Config
        this.MainPanel = new JPanel();
        this.MainPanel.setLayout(new GridLayout(2,2));

        JButton openFile = new JButton("OPEN");
        JButton newFile = new JButton("NEW");

        this.MainPanel.add(openFile);
        this.MainPanel.add(new JLabel("Open existing file"));
        this.MainPanel.add(newFile);
        this.MainPanel.add(new JLabel("Open new file"));

        this.MainFrame.add(this.MainPanel);

//  Buttons Actions Listeners

        // Open button listener
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Выберите файл"); // Название диалогового окна
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Режим выбора только файлов

                        int result = fileChooser.showOpenDialog(new JPanel()); // Открытие диалогового окна выбора файла

                        if (result == JFileChooser.APPROVE_OPTION) { // Обработка выбора файла
                            File selectedFile = fileChooser.getSelectedFile(); // Получение выбранного файла

                            String filename = selectedFile.getAbsolutePath();
                            try {
                                Application app = new Application(4);
                                app.onFileChange(filename);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        return null;
                    }
                };
                worker.execute();
            }
        });


        // NewFile button Listener
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                    @Override
                    protected Void doInBackground() throws Exception {
                        Application application = new Application(4);
                        application.update();
                        return null;
                    }
                };
                worker.execute();
            }
        });

    }


}
