package FileStaff;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AppFileReader {
    private String filename;
    private ArrayList<Integer> data;

    public AppFileReader(){

    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) throws IOException {
        data = new ArrayList<>();
        this.filename = filename;
        InputStream iStream = new FileInputStream(filename);
        int value;
        while ((value = iStream.read()) !=-1 )data.add(value);
    }
    public ArrayList<Integer> getData() {
        return data;
    }
    public void setData(ArrayList<Integer> data){ this.data = data; }
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