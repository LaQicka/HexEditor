package logic;

import java.io.*;
import java.util.ArrayList;

public class ByteFileReader {
    private String filename;
    private ArrayList<Integer> data;
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
    public void setData(ArrayList<Integer> data){this.data = data;}
    public boolean save(String filename){
        try{
            OutputStream oStream = new FileOutputStream(filename);
            for (Integer cur:data) {
                oStream.write(cur);
            }
            oStream.close();
            return true;
        }catch (IOException ex){
            System.out.println(ex);
            return false;
        }
    }
}
