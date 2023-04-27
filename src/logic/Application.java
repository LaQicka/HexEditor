package logic;

import java.io.IOException;
import java.util.ArrayList;
import gui.MainFrame;
import gui.Type;

public class Application{ // Класс приложения, содержит данные и методы для обработки изменения фреймов
    private MainFrame mainFrame;
    private StringBuilder hexContent;
    private StringBuilder textContent;
    private ArrayList<Integer> data;
    private int width;
    private ByteFileReader reader;
    public Application(int width){
        this.width = width;
        mainFrame = new MainFrame(this);
        reader = new ByteFileReader();
    }
    public ByteFileReader getReader(){return this.reader;}
    public void setWidth(int width){
        this.width = width;
        this.updateContent();
        this.update();
    }
    public int getWidth() {
        return width;
    }
    public void setData(ArrayList<Integer> data){
        this.data = data;
        this.updateContent();
    }
    public ArrayList<Integer> getData(){return this.data;}
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
    public void update(){
        mainFrame.setContent(this.textContent,this.hexContent);
    }
    public void remove(int offset, int length){ // удаление промежутка текста
        for(int i=0;i<length;i++) if(data.size()>0)data.remove(offset);
    }
    public void insert(int offset, String text){ // вставка промежутка текста
        if(data.size()>0)for(int i=0;i<text.length();i++) data.add(offset+i,(int)text.charAt(i));
        else for(int i=0;i<text.length();i++) data.add((int) text.charAt(i));
    }
    public void onTextChange(int offset, int length , Type type, String text){

        if (type.equals(Type.REMOVE)) this.remove(offset, length);

        else if (type.equals(Type.REPLACE)) {
            this.remove(offset, length);
            this.insert(offset, text);
        } else if (type.equals(Type.INSERT)) this.insert(offset, text);

        this.updateContent();
        this.update();
    }
    public void onHexChange(StringBuilder hex){
        hexContent = new StringBuilder();
        for(int i=0;i<hex.length();i++){
            if(hex.charAt(i)!=' ' && hex.charAt(i)!='\n' &&
                    ( ((int)hex.charAt(i)>=97 && (int)hex.charAt(i)<=102) || (((int)hex.charAt(i)>=48 && (int)hex.charAt(i)<=57)) ))hexContent.append(hex.charAt(i));
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
    public void readFile(String filename) throws IOException {
        reader.setFilename(filename);
        this.setData(reader.getData());
    }
}
