package gui;

import java.util.ArrayList;

public class Application{ // Класс приложения, содержит данные и методы для обработки изменения фреймов
    private MainFrame mainFrame;
    private StringBuilder hexContent;
    private StringBuilder textContent;
    private ArrayList<Integer> data;
    private int width;
    public Application(int width){
        this.width = width;
        mainFrame = new MainFrame(this);
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getWidth() {
        return width;
    }
    public void setData(ArrayList<Integer> data){
        this.data = data;
        this.updateContent();
    }
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
        for(int i=0;i<length;i++) data.remove(offset);
    }
    public void insert(int offset, String text){ // вставка промежутка текста
        for(int i=0;i<text.length();i++) data.add(offset+i,(int)text.charAt(i));
    }
    public void onTextChange(int offset,int length ,Type type, String text){

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
}
