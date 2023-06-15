package logic;

import java.io.IOException;
import java.util.ArrayList;
import FileStaff.*;
import gui.*;

// Класс приложения, содержит данные и методы для обработки изменения фреймов
public class Application{
    private MainFrame mainFrame;
    private StringBuilder hexContent;
    private StringBuilder textContent;
    private ArrayList<Integer> data;
    private AppFileReader fileReader;
    private int width;

//  Конструктор - проводит инициализацию основного фрейма и класса считывания/записи файлов
    public Application(int width){
        this.width = width;
        mainFrame = new MainFrame(this);
        this.fileReader = new AppFileReader();
        this.data = new ArrayList<>();
        this.hexContent = new StringBuilder();
        this.textContent = new StringBuilder();
    }

//  Установка длины строки в байтах
    public void setWidth(int width){
        this.width = width;
        this.update();
    }

//
    public int getWidth() {
        return width;
    }

//  Метод установки массива байт
    public void setData(ArrayList<Integer> data){
        this.data = data;
        this.updateContent();
    }

//  Метод обновления контентного содержимого переменных внутри класса
    public void updateContent(){
        int i = 0;
        hexContent = new StringBuilder();
        textContent = new StringBuilder();
        for (Integer cur:this.data) {
            hexContent.append(String.format("%02X",cur).toLowerCase());
            if (!Character.isISOControl(cur)) {
                textContent.append((char) cur.intValue());
            } else {
                textContent.append(".");
            }
        }
        mainFrame.setContent(textContent,hexContent);
    }

//  Метод обновления содержимого основного фрейма
    public void update(){
        mainFrame.setContent(this.textContent,this.hexContent);
    }

//  Метод удаления определенного промежутка байт
    public void remove(int offset, int length){ // удаление промежутка текста
        for(int i=0;i<length;i++) data.remove(offset);
    }

//  Метод вставки промежутка байт
    public void insert(int offset, String text){ // вставка промежутка текста
        for(int i=0;i<text.length();i++) {
            data.add(offset - 1 + i, (int) text.charAt(i));
        }
    }

//  Метод, обрабатывающий изменения содержимого текстовой панели
    public void onTextChange(int offset,int length ,Type type, String text){

        if (type.equals(Type.REMOVE)) this.remove(offset, length);

        else if (type.equals(Type.REPLACE)) {
            this.remove(offset, length);
            this.insert(offset, text);
        }
        else if (type.equals(Type.INSERT)) this.insert(offset, text);

        this.updateContent();
        this.update();
    }

//  Метод, обрабатывающий изменения содержимого hex-панели
    public void onHexChange(StringBuilder hex){
        hexContent = new StringBuilder();
        for(int i=0;i<hex.length();i++){
            int temp = hex.charAt(i);
            if(hex.charAt(i)!=' '  && hex.charAt(i)!='\n' && temp < 103)hexContent.append(hex.charAt(i));
        }
        data.clear();
        for(int i=0;i<hexContent.length();i+=2){
            if(i+2<=hexContent.length()) {
                data.add(Integer.decode("0x" + hexContent.substring(i, i + 2)));
            }
            else {
                data.add(Integer.decode("0x" + hexContent.substring(i)));
            }
        }

        this.updateContent();
        this.update();
    }

//  Метод, обрабатывающий смену входного файла. По сути просто открывает новый файл без сохранения старого
    public void onFileChange(String filename) throws IOException {
        this.fileReader.setFilename(filename);
        this.data = fileReader.getData();
        this.updateContent();
        this.update();
    }

//  Метод сохранения содержимого в файл
    public void onSaveFile(String filename){
        this.fileReader.setData(this.data);
        this.fileReader.writeToFile(filename);
    }
}
