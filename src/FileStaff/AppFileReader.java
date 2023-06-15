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
            iStream.close();
        }catch (IOException e){
            data = null;
            throw new RuntimeException(e);
        }
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
        try{
            FileOutputStream output = new FileOutputStream(filename);

            for(int i=0;i<data.size();i++){
                output.write(data.get(i));
            }

            output.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }



    }
}
