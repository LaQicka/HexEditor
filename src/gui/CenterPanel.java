package gui;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class CenterPanel extends JPanel {

    Application app;
    private JTextArea hexArea;
    private JTextArea textArea;
    private StringBuilder textContent;
    private StringBuilder hexContent;
    private JPanel hexPanel;
    private JPanel textPanel;
    private JPanel center;
    private JPanel north;
    private int height;
    public CenterPanel(Application app) {
        this.app = app;
        this.height = 0;
        this.setLayout(new BorderLayout());
        center = new JPanel(new GridLayout(1, 2));

//  HEX Panel config
//  <----------------------------------------------------------------->
        {
            hexPanel = new JPanel(new BorderLayout());

            JPanel hexNorthPanel = new JPanel(); // Верхняя панель с адресами колонок
            hexPanel.add(hexNorthPanel, BorderLayout.NORTH);

            JPanel hexCenterPanel = new JPanel(); // Центральная панель с данными
            hexCenterPanel.setLayout(new BoxLayout(hexCenterPanel,BoxLayout.Y_AXIS));
            hexPanel.add(hexCenterPanel, BorderLayout.CENTER);

            JPanel hexWestPanel = new JPanel();
            hexPanel.add(hexWestPanel, BorderLayout.WEST);

            JScrollPane hexScrollPane = new JScrollPane(hexPanel);
            hexScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            hexScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            StringBuilder colAdrText = new StringBuilder();
            for (int i = 0; i < app.getWidth(); i++) colAdrText.append(String.format("%02X ", i));
            JTextArea colAdr = new JTextArea(colAdrText.toString()); // Текстовая область адресов колонок
            colAdr.setEnabled(false);
            colAdr.setFont(new Font("Courier New", 1, 15));
            hexCenterPanel.add(colAdr);

            hexArea = new JTextArea(23, 24); // Текстовая область данных
            hexArea.setFont(new Font("Courier New", 1, 15));
            hexCenterPanel.add(hexArea);

            JTextArea hexWestArea = new JTextArea();
            hexWestArea.setMargin(new Insets(15,0,0,0));
            hexWestArea.setEnabled(false);
            hexWestArea.setFont(new Font("Courier New", 1, 15));
            hexWestPanel.add(hexWestArea);

            center.add(hexScrollPane);
        }
//  <----------------------------------------------------------------->

//  TEXT Panel config
//  <----------------------------------------------------------------->
        {
            textPanel = new JPanel(new BorderLayout());

            JPanel textCenterPanel = new JPanel(); // Центральная панель с данными
            textPanel.add(textCenterPanel, BorderLayout.CENTER);

            JPanel textNorthPanel = new JPanel(); // Верхняя панель с адресами колонок
            textPanel.add(textNorthPanel, BorderLayout.NORTH);

            JScrollPane textScrollPane = new JScrollPane(textPanel);
            textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            textArea = new JTextArea(23, 24); // Текстовая область данных
            textArea.setFont(new Font("Courier New", 1, 15));
            textCenterPanel.add(textArea);

            JTextArea northTextArea = new JTextArea("Decode text");
            northTextArea.setFont(new Font("Courier New", 1, 15));
            northTextArea.setEnabled(false);
            textNorthPanel.add(northTextArea);

            center.add(textScrollPane);
        }
//  <----------------------------------------------------------------->

        this.add(center,BorderLayout.CENTER);
        this.setVisible(true);

//  Listeners
    //  Text Area Listener
    //  <----------------------------------------------------------------->
        AbstractDocument textDoc = (AbstractDocument) textArea.getDocument();
        textDoc.setDocumentFilter(new DocumentFilter() {
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                super.insertString(fb, offset, text, attr);
            }
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                String selected = textArea.getSelectedText();
                super.remove(fb, offset, length);
                for(int i=0;selected!=null && i<selected.length();i++) if(selected.charAt(i) == '\n') length--; // пересчет длины строки для удаления без /n
                StringBuilder now = new StringBuilder(textArea.getText());
                if(!now.toString().equals(textContent.toString())) onTextChange(offset-offset/app.getWidth(),length,Type.REMOVE, "");

            }
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                super.replace(fb, offset, length, text, attrs);
                StringBuilder now = new StringBuilder(textArea.getText());
                if(!now.toString().equals(textContent.toString())) {
                    onTextChange(offset-(offset/app.getWidth()-1),length,Type.REPLACE, text);

//                    if(length>0){
//                        onTextChange(offset-offset/app.getWidth(),length,Type.REPLACE, text);
//                    }else{
//                        if(offset%app.getWidth()==0)onTextChange((offset+1)-offset/app.getWidth(),length,Type.INSERT, text); // из-за того что в цонце каждой строки есть \n
//                        else onTextChange(offset-offset/app.getWidth(),length,Type.INSERT, text);                            // требуется менять offset
//                    }
                }
            }
            //offset - индекс измененного символа
        });
    //  <----------------------------------------------------------------->

    //  HEX Area Listener
    //  <----------------------------------------------------------------->
        AbstractDocument hexDoc = (AbstractDocument) hexArea.getDocument();
        hexDoc.setDocumentFilter(new DocumentFilter() {
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                super.insertString(fb, offset, text, attr);
                StringBuilder now = new StringBuilder(hexArea.getText());
                if(!now.toString().equals(hexContent.toString())) {
                    StringBuilder hex = new StringBuilder(hexArea.getText());
                    onHexChange(hex);
                }
            }
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length);
                StringBuilder now = new StringBuilder(hexArea.getText());
                if(!now.toString().equals(hexContent.toString())) {
                    StringBuilder hex = new StringBuilder(hexArea.getText());
                    onHexChange(hex);
                }
            }
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                super.replace(fb, offset, length, text, attrs);
                StringBuilder now = new StringBuilder(hexArea.getText());
                if(!now.toString().equals(hexContent.toString())) {
                    StringBuilder hex = new StringBuilder(hexArea.getText());
                    onHexChange(hex);
                }
            }
            //offset - индекс измененного символа
        });
    //  <----------------------------------------------------------------->

    }

//  Метод установки содержимого текстовых переменных
    public void setContent(StringBuilder textContent, StringBuilder hexContent){
        this.textContent = new StringBuilder();
        this.hexContent = new StringBuilder();
        int width = app.getWidth();
        if(textContent.length() % width==0) height = textContent.length()/width;
        else height = (textContent.length()/width) + 1;
        for(int i = 0;i<textContent.length();i++){
            this.textContent.append(textContent.charAt(i));
            this.hexContent.append(hexContent.substring(i*2,i*2+2));
            this.hexContent.append(" ");
            if((i+1)%app.getWidth() == 0){
                this.textContent.append('\n');
                this.hexContent.append('\n');
            }
        }
        this.update();

    }

//  Метод обработки изменений текстовой области
    public void onTextChange(int offset,int length, Type type, String text){
        app.onTextChange(offset,length,type,text);
    }

//  Метод обработки изменений области шестнадцетиричного текста
    public void onHexChange(StringBuilder hex){
        app.onHexChange(hex);
    }

//  Метод обновления содержимого текстовых областей
    public void update(){
        this.textArea.setText(textContent.toString());
        this.hexArea.setText(hexContent.toString());
        int width = app.getWidth();
        JPanel heightPanel = (JPanel) hexPanel.getComponent(2);
        JTextArea heightArea = (JTextArea) heightPanel.getComponent(0);
        StringBuilder strAdr = new StringBuilder();
        for(int i=0;i<=this.height;i++) {
            strAdr.append(String.format("%08X",i*width).toLowerCase());
            strAdr.append('\n');
        }
        heightArea.setText(strAdr.toString());

        JPanel widthPanel = (JPanel) hexPanel.getComponent(1);
        JTextArea widthArea = (JTextArea) widthPanel.getComponent(0);

        StringBuilder colAdrText = new StringBuilder();
        for (int i = 0; i < width; i++) colAdrText.append(String.format("%02X ", i));
        widthArea.setText(colAdrText.toString());

        this.revalidate();
    }
}

