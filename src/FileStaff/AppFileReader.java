package FileStaff;

import java.io.*;
import java.util.ArrayList;

// Класс записи/чтения ффайлов в байтовом виде
public class AppFileReader {
    private String filename;
    private ArrayList<Integer> data;

    public AppFileReader(){

    }

//
    public String getFilename() {
        return filename;
    }

//  Чтение из файла по имени
    public void setFilename(String filename){
        data = new ArrayList<>();
        this.filename = filename;
        System.out.println(filename);
        try{
            InputStream iStream = new FileInputStream(filename);
            int value;
            while ((value = iStream.read()) !=-1 )data.add(value);
        }catch (IOException e){
            data = null;
            throw new RuntimeException(e);
        }
//        System.out.println(this.filename + " : " + data);
    }

//
    public ArrayList<Integer> getData() {
        return data;
    }

//
    public void setData(ArrayList<Integer> data){ this.data = data; }

//  Метод записи в файл. Если файл не существует будет создан новый файл с указанным именем
    public void writeToFile(String filename){

        StringBuilder content = new StringBuilder();
        for(int i=0;i<data.size();i++){
            content.append(Character.highSurrogate(data.get(i)));
        }
        try{
            FileOutputStream output = new FileOutputStream(filename);

            for(int i=0;i<data.size();i++){
                output.write(data.get(i));
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }



    }
}
